package com.mytaxi.controller;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mytaxi.model.Car;
import com.mytaxi.model.Driver;
import com.mytaxi.repository.CarRepository;
import com.mytaxi.repository.DriverRepository;
import com.mytaxi.service.DataBaseSequenceServiceCar;
import com.mytaxi.service.DataBaseSequenceServiceDriver;
import com.mytaxi.service.ZonedDateTimeWriteConverter;

@RestController
@RequestMapping(path = "/api/v1/reset/")
public class ResetController {

	@Autowired
	private DataBaseSequenceServiceCar seqServicec;
	
	@Autowired
	private DataBaseSequenceServiceDriver seqServiced;
	
	@Autowired
	private ZonedDateTimeWriteConverter writeConverter;
	
	@Autowired
	private DriverRepository driverRepo;
	
	@Autowired
	private CarRepository carRepo;
	
	@CrossOrigin
	@PostMapping(path = "resetAll")
	public ResponseEntity<?> save() {
		try {
			List<Car> carsList = new ArrayList<Car>();
			
			Car car1 = new Car(seqServicec.generateSequence(Car.SEQUENCE_NAME),"GJ051234", 4, 3.1f
					, "Gas", "1993", "TATA", true, false, 
					writeConverter.convert(ZonedDateTime.now(ZoneOffset.UTC)), 1L);
			carsList.add(car1);
			Car car2 = new Car(seqServicec.generateSequence(Car.SEQUENCE_NAME),"GJ051235", 4, 3.1f
					, "Gas", "1993", "TATA", true, false, 
					writeConverter.convert(ZonedDateTime.now(ZoneOffset.UTC)), 2L);
			carsList.add(car2);
			Car car3 = new Car(seqServicec.generateSequence(Car.SEQUENCE_NAME),"GJ051231", 4, 3.1f
					, "Gas", "1993", "TATA", true, true, 
					writeConverter.convert(ZonedDateTime.now(ZoneOffset.UTC)), null);
			carsList.add(car3);
			Car car4 = new Car(seqServicec.generateSequence(Car.SEQUENCE_NAME),"GJ051236", 4, 3.1f
					, "Gas", "1993", "TATA", true, false, 
					writeConverter.convert(ZonedDateTime.now(ZoneOffset.UTC)), 4L);
			carsList.add(car4);
			Car car5 = new Car(seqServicec.generateSequence(Car.SEQUENCE_NAME),"GJ051232", 4, 3.1f
					, "Gas", "1993", "TATA", true, false, 
					writeConverter.convert(ZonedDateTime.now(ZoneOffset.UTC)), 5L);
			carsList.add(car5);
			Car car6 = new Car(seqServicec.generateSequence(Car.SEQUENCE_NAME),"GJ051233", 4, 3.1f
					, "Gas", "1993", "TATA", true, false, 
					writeConverter.convert(ZonedDateTime.now(ZoneOffset.UTC)), 6L);
			carsList.add(car6);
			Car car7 = new Car(seqServicec.generateSequence(Car.SEQUENCE_NAME),"GJ051237", 4, 3.1f
					, "Gas", "1993", "TATA", true, true, 
					writeConverter.convert(ZonedDateTime.now(ZoneOffset.UTC)), null);
			carsList.add(car7);
			Car car8 = new Car(seqServicec.generateSequence(Car.SEQUENCE_NAME),"GJ051238", 4, 3.1f
					, "Gas", "1993", "TATA", true, false, 
					writeConverter.convert(ZonedDateTime.now(ZoneOffset.UTC)), 7L);
			carsList.add(car8);
			Car car9 = new Car(seqServicec.generateSequence(Car.SEQUENCE_NAME),"GJ051239", 4, 3.1f
					, "Gas", "1993", "TATA", true, true, 
					writeConverter.convert(ZonedDateTime.now(ZoneOffset.UTC)), null);
			carsList.add(car9);
			Car car10 = new Car(seqServicec.generateSequence(Car.SEQUENCE_NAME),"GJ051230", 4, 3.1f
					, "Gas", "1993", "TATA", true, false, 
					writeConverter.convert(ZonedDateTime.now(ZoneOffset.UTC)), 8L);
			carsList.add(car10);
			
			carRepo.saveAll(carsList);
			
			List<Driver> driverList = new ArrayList<Driver>();
			Driver driver1 = new Driver(seqServiced.generateSequence(Driver.SEQUENCE_NAME), "Yogesh Patil"
					, 9875582541L, writeConverter.convert(ZonedDateTime.now(ZoneOffset.UTC)), car1, 1L);
			driverList.add(driver1);
			Driver driver2 = new Driver(seqServiced.generateSequence(Driver.SEQUENCE_NAME), "Mahesh Patel"
					, 9875582542L, writeConverter.convert(ZonedDateTime.now(ZoneOffset.UTC)), car2, 2L);
			driverList.add(driver2);
			Driver driver3 = new Driver(seqServiced.generateSequence(Driver.SEQUENCE_NAME), "JAgdish Patil"
					, 9875582543L, writeConverter.convert(ZonedDateTime.now(ZoneOffset.UTC)), null, null);
			driverList.add(driver3);
			Driver driver4 = new Driver(seqServiced.generateSequence(Driver.SEQUENCE_NAME), "Himanshu Patel"
					, 9875582544L, writeConverter.convert(ZonedDateTime.now(ZoneOffset.UTC)), car4, 4L);
			driverList.add(driver4);
			Driver driver5 = new Driver(seqServiced.generateSequence(Driver.SEQUENCE_NAME), "Aakash Patel"
					, 9875582545L, writeConverter.convert(ZonedDateTime.now(ZoneOffset.UTC)), car5, 5L);
			driverList.add(driver5);
			Driver driver6 = new Driver(seqServiced.generateSequence(Driver.SEQUENCE_NAME), "paresh Patel"
					, 9875582546L, writeConverter.convert(ZonedDateTime.now(ZoneOffset.UTC)), car6, 6L);
			driverList.add(driver6);
			Driver driver7 = new Driver(seqServiced.generateSequence(Driver.SEQUENCE_NAME), "Lalit Patel"
					, 9875582547L, writeConverter.convert(ZonedDateTime.now(ZoneOffset.UTC)), car8, 8L);
			driverList.add(driver7);
			Driver driver8 = new Driver(seqServiced.generateSequence(Driver.SEQUENCE_NAME), "Mitansh Patil"
					, 9875582548L, writeConverter.convert(ZonedDateTime.now(ZoneOffset.UTC)), car10, 10L);
			driverList.add(driver8);
			Driver driver9 = new Driver(seqServiced.generateSequence(Driver.SEQUENCE_NAME), "Kalpesh Wagh"
					, 9875582549L, writeConverter.convert(ZonedDateTime.now(ZoneOffset.UTC)), null, null);
			driverList.add(driver9);
			Driver driver10 = new Driver(seqServiced.generateSequence(Driver.SEQUENCE_NAME), "Yogesh Rajput"
					, 9875582540L, writeConverter.convert(ZonedDateTime.now(ZoneOffset.UTC)), null, null);
			driverList.add(driver10);
			Driver driver11 = new Driver(seqServiced.generateSequence(Driver.SEQUENCE_NAME), "Pradip Salunkhe"
					, 9875582502L, writeConverter.convert(ZonedDateTime.now(ZoneOffset.UTC)), null, null);
			driverList.add(driver11);
			
			driverRepo.saveAll(driverList);
			
			return new ResponseEntity<String>("All data save succefully", HttpStatus.ACCEPTED);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getLocalizedMessage(), HttpStatus.ACCEPTED);
		}
	}
}
