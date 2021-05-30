package com.mytaxi.controller;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mytaxi.dto.CarDTO;
import com.mytaxi.dto.DriverDTO;
import com.mytaxi.exceptions.CarAlreadyInUseException;
import com.mytaxi.model.Car;
import com.mytaxi.model.Driver;
import com.mytaxi.repository.CarRepository;
import com.mytaxi.repository.DriverRepository;
import com.mytaxi.service.DataBaseSequenceServiceDriver;
import com.mytaxi.service.DriverCustomService;
import com.mytaxi.service.ZonedDateTimeReadConverter;
import com.mytaxi.service.ZonedDateTimeWriteConverter;
import com.mytaxi.util.MethodUtils;

@RestController
@RequestMapping(path = "/api/v1/driver/")
public class DriverController {

	@Autowired
	private DriverRepository driverRepo;

	@Autowired
	private CarRepository carRepo;

	@Autowired
	private DriverCustomService driverService;

	@Autowired
	private DataBaseSequenceServiceDriver seqService;

	@Autowired
	private ZonedDateTimeReadConverter readConverter;

	@Autowired
	private ZonedDateTimeWriteConverter writeConverter;

	@GetMapping(path = "findAll")
	public ResponseEntity<?> findAll(@RequestParam(defaultValue = "1") Integer pageNo,
			@RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "id") String sortBy) {
		try {
			List<Driver> drivers = driverService.getAllDrivers(pageNo, pageSize, sortBy);
			if (drivers.isEmpty()) {
				return new ResponseEntity<String>("No Driver(s) found for page no: " + pageNo, HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<List<Driver>>(drivers, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			System.out.println("............exception : " + e.getMessage());
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.EXPECTATION_FAILED);
		}

	}

	@PostMapping(path = "add")
	public ResponseEntity<?> addDriver(@RequestBody DriverDTO driverDTO) {
		Driver driver = new Driver();
		List<Driver> drivers = driverService.findBy(driverDTO.getContact_num(), "contact_num");
		if (!MethodUtils.isListIsNullOrEmpty(drivers)) {
			return new ResponseEntity<String>("The contact no all ready register", HttpStatus.BAD_REQUEST);
		}

		driver.setId(seqService.generateSequence(Driver.SEQUENCE_NAME));
		driver.setCreated_date(writeConverter.convert(ZonedDateTime.now(ZoneOffset.UTC)));
		if (MethodUtils.isObjectisNullOrEmpty(driverDTO.getFull_name())) {
			return new ResponseEntity<String>("Please Enter driver full name: ", HttpStatus.BAD_REQUEST);
		}
		if (MethodUtils.isObjectisNullOrEmpty(driverDTO.getContact_num())) {
			return new ResponseEntity<String>("Please Enter driver contact: ", HttpStatus.BAD_REQUEST);
		}
		driver.setFull_name(driverDTO.getFull_name());
		driver.setContact_num(driverDTO.getContact_num());
		if (!MethodUtils.isObjectisNullOrEmpty(driverDTO.getCarID())) {
			Optional<Car> optCar = carRepo.findById(driver.getCarID());
			if (!optCar.get().equals(null)) {
				driver.setCar(optCar.get());
			}
		}
		driverRepo.save(driver);

		return new ResponseEntity<Driver>(driver, HttpStatus.ACCEPTED);
	}

	@GetMapping(path = "search")
	public ResponseEntity<?> searchCar(@RequestParam String searchString, @RequestParam String searchBy,
			@RequestParam(defaultValue = "0") Integer pageNo, 
            @RequestParam(defaultValue = "10") Integer pageSize){
		try {
			List<Driver> drivers = null;
			Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(searchBy));
			if(searchBy == "contact_num" || searchBy.equals("contact_num")) {
				Long contactString = Long.parseLong(searchString);
				drivers = driverService.findBy(contactString, searchBy, paging);
			} else {
				drivers = driverService.findBySearch(searchString, searchBy, paging);
			}
			System.out.println(".........drivers: "+drivers.size());
			if (MethodUtils.isListIsNullOrEmpty(drivers)) {
				return new ResponseEntity<String>("Drivers Not found for searching "+searchString, HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<List<Driver>>(drivers, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.EXPECTATION_FAILED);
		}
	}

	@GetMapping(path = "find/{id}")
	public ResponseEntity<?> findById(@PathVariable(name = "id") Long id) {
		Optional<Driver> optDriver = driverRepo.findById(id);
		if (optDriver.isEmpty()) {
			return new ResponseEntity<String>("Driver not found", HttpStatus.NOT_FOUND);
		}
		Driver driver = optDriver.get();

		return new ResponseEntity<DriverDTO>(getDriverDTOObject(driver), HttpStatus.FOUND);
	}

	@DeleteMapping(path = "delete/{id}")
	public ResponseEntity<?> deleteCar(@PathVariable(name = "id") Long id) {
		Optional<Driver> optDriver = driverRepo.findById(id);
		if (optDriver.isEmpty()) {
			return new ResponseEntity<String>("Driver not found", HttpStatus.NOT_FOUND);
		}
		driverRepo.delete(optDriver.get());
		return new ResponseEntity<String>("Driver removed succefully", HttpStatus.FOUND);
	}

	@PutMapping(path = "assignCar")
	public ResponseEntity<?> addCar(@RequestBody DriverDTO driverDTO) {
		Optional<Driver> optDriver = driverRepo.findById(driverDTO.getId());
		if (optDriver.isEmpty()) {
			return new ResponseEntity<String>("Driver not found", HttpStatus.NOT_FOUND);
		}
		Optional<Car> optCar = carRepo.findById(driverDTO.getCarID());
		if (optCar.isEmpty()) {
			return new ResponseEntity<String>("Car not found", HttpStatus.NOT_FOUND);
		}
		Driver driverExist = optDriver.get();
		Car car = optCar.get();
		if (!car.getIsAvailable()) {
			throw new CarAlreadyInUseException("Given car already in use..,");
		}
		car.setIsAvailable(false);
		driverExist.setCar(car);
		driverExist.setCarID(driverDTO.getCarID());
		driverRepo.save(driverExist);

		car.setDriverId(driverExist.getId());
		car.setIsAvailable(false);
		carRepo.save(car);
		return new ResponseEntity<Driver>(driverExist, HttpStatus.FOUND);
	}

	@PostMapping(path = "deSelectCar/{id}")
	public ResponseEntity<?> removeCar(@PathVariable(name = "id") Long id) {
		Optional<Driver> optDriver = driverRepo.findById(id);
		if (optDriver.isEmpty()) {
			return new ResponseEntity<String>("Driver not found", HttpStatus.NOT_FOUND);
		}
		Driver driver = optDriver.get();
		if (MethodUtils.isObjectisNullOrEmpty(driver.getCarID())) {
			return new ResponseEntity<String>("No Car available for Driver: " + driver.getFull_name(),
					HttpStatus.NOT_FOUND);
		}
		driver.setCar(null);
		driverRepo.save(driver);
		Optional<Car> optCar = carRepo.findById(driver.getCarID());
		if (optCar.isPresent()) {
			Car car = optCar.get();
			car.setDriverId(null);
			car.setIsAvailable(true);
			carRepo.save(car);
		}
		return new ResponseEntity<Driver>(optDriver.get(), HttpStatus.FOUND);
	}

	public DriverDTO getDriverDTOObject(Driver driver) {
		DriverDTO driverDTO = new DriverDTO();
		driverDTO.setContact_num(driver.getContact_num());
		driverDTO.setFull_name(driver.getFull_name());
		if (!MethodUtils.isObjectisNullOrEmpty(driver.getCarID())) {
			Car car = driver.getCar();
			CarDTO carDTO = new CarDTO();
			carDTO.setConvertible(car.getConvertible());
			carDTO.setCreated_date(car.getCreated_date());
			carDTO.setDriverId(car.getDriverId());
			carDTO.setEngine_type(car.getEngine_type());
			carDTO.setIsAvailable(car.getIsAvailable());
			carDTO.setLicense_plate(car.getLicense_plate());
			carDTO.setManufacturer(car.getManufacturer());
			carDTO.setModel(car.getModel());
			carDTO.setRating(car.getRating());
			carDTO.setSeat_cnt(car.getSeat_cnt());
			driverDTO.setCar(carDTO);
		}
		return driverDTO;
	}
}
