package com.mytaxi.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.mytaxi.model.Car;
import com.mytaxi.repository.CarCustomRepository;

@Service
public class CarCustomService {

	@Autowired
	private CarCustomRepository carCystomRepo;
	
	@Autowired
	private MongoTemplate mongoTemplate;

	public List<Car> getAllCars(Integer pageNo, Integer pageSize, String sortBy) {
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

		Page<Car> pagedResult = carCystomRepo.findAll(paging);

		if (pagedResult.hasContent()) {
			return pagedResult.getContent();
		} else {
			return new ArrayList<Car>();
		}
	}

	public List<Car> findBy(String searchString, String searchBy){
		Query query = new Query();
		query.addCriteria(Criteria.where(searchString).is(searchBy));
		return mongoTemplate.find(query, Car.class);
	}
	
	public List<Car> findBy(String searchString, String searchBy, Pageable paging){
		Query query = new Query().with(paging);
		query.addCriteria(Criteria.where(searchString).is(searchBy));
		return mongoTemplate.find(query, Car.class);
	}
	
	public List<Car> findBySearch(String searchString, String searchBy, Pageable paging){
		Query query = new Query().with(paging);
		System.out.println("....searc searchBy: "+searchBy+" searchString: "+searchString);
		query.addCriteria(Criteria.where(searchBy).regex(searchString+".*"));
		return mongoTemplate.find(query, Car.class);
	}
}
