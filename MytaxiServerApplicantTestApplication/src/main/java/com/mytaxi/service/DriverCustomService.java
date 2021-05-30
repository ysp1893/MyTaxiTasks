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
import com.mytaxi.model.Driver;
import com.mytaxi.repository.DriverCustomRepository;

@Service
public class DriverCustomService {

	@Autowired
	private DriverCustomRepository driverCystomRepo;

	@Autowired
	private MongoTemplate mongoTemplate;
	
	public List<Driver> getAllDrivers(Integer pageNo, Integer pageSize, String sortBy) {
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

		Page<Driver> pagedResult = driverCystomRepo.findAll(paging);

		if (pagedResult.hasContent()) {
			return pagedResult.getContent();
		} else {
			return new ArrayList<Driver>();
		}
	}

	
	public List<Driver> findBy(Long searchString, String searchBy){
		Query query = new Query();
		query.addCriteria(Criteria.where(searchBy).is(searchString));
		return mongoTemplate.find(query, Driver.class);
	}
	
	public List<Driver> findBy(String searchString, String searchBy, Pageable paging){
		Query query = new Query().with(paging);
		query.addCriteria(Criteria.where(searchString).is(searchBy));
		return mongoTemplate.find(query, Driver.class);
	}
	
	
	public List<Driver> findBy(Long searchString, String searchBy, Pageable paging){
		Query query = new Query().with(paging);
		query.addCriteria(Criteria.where(searchBy).is(searchString));
		return mongoTemplate.find(query, Driver.class);
	}
	
	public List<Driver> findBySearch(String searchString, String searchBy, Pageable paging){
		Query query = new Query().with(paging);
		query.addCriteria(Criteria.where(searchBy).regex(searchString+".*"));
		return mongoTemplate.find(query, Driver.class);
	}

}
