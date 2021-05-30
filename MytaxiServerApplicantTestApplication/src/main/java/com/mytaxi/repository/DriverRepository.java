package com.mytaxi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mytaxi.model.Driver;

public interface DriverRepository extends MongoRepository<Driver, Long>{

}
