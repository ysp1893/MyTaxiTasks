package com.mytaxi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mytaxi.model.Car;

@Repository
public interface CarRepository extends MongoRepository<Car, Long>{

}
