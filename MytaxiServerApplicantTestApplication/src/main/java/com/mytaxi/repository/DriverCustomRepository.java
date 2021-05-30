package com.mytaxi.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.mytaxi.model.Driver;

@Repository
public interface DriverCustomRepository extends PagingAndSortingRepository<Driver, Long>{

}
