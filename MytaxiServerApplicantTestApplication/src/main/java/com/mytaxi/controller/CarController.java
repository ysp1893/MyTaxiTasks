package com.mytaxi.controller;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mytaxi.dto.CarDTO;
import com.mytaxi.dto.DriverDTO;
import com.mytaxi.model.Car;
import com.mytaxi.model.Driver;
import com.mytaxi.repository.CarRepository;
import com.mytaxi.repository.DriverRepository;
import com.mytaxi.service.CarCustomService;
import com.mytaxi.service.DataBaseSequenceServiceCar;
import com.mytaxi.service.ZonedDateTimeReadConverter;
import com.mytaxi.service.ZonedDateTimeWriteConverter;
import com.mytaxi.util.MethodUtils;

@RestController
@RequestMapping(path = "/api/v1/car/")
public class CarController {

	@Autowired
	private CarRepository carRepo;
	
	@Autowired
	private DriverRepository driverRepo;
	
	@Autowired
	private CarCustomService carService;
	
	@Autowired
	private DataBaseSequenceServiceCar seqService;
	
	@Autowired
	private ZonedDateTimeReadConverter readConverter;
	
	@Autowired
	private ZonedDateTimeWriteConverter writeConverter;
	
	@GetMapping(path = "findAll")
	public ResponseEntity<?> findAll(@RequestParam(defaultValue = "1") Integer pageNo, 
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
		try {
			List<Car> cars = carService.getAllCars(pageNo, pageSize, sortBy);
			if (cars.isEmpty()) {
				return new ResponseEntity<String>("No Car(s) found for page no: " + pageNo, HttpStatus.NO_CONTENT);
			}
			
			return new ResponseEntity<List<Car>>(cars, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.EXPECTATION_FAILED);
		}

	}
	
	@GetMapping(path = "search")
	public ResponseEntity<?> searchCar(@RequestParam String searchString, @RequestParam(defaultValue = "id") String searchBy,
			@RequestParam(defaultValue = "0") Integer pageNo, 
            @RequestParam(defaultValue = "10") Integer pageSize){
		try {
			Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(searchBy));
			List<Car> cars = carService.findBySearch(searchString, searchBy, paging);
			if (MethodUtils.isListIsNullOrEmpty(cars)) {
				return new ResponseEntity<String>("Cars Not found for searching "+searchString, HttpStatus.BAD_REQUEST);
			}
			System.out.println(".........drivers: "+cars.size());
			return new ResponseEntity<List<Car>>(cars, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.EXPECTATION_FAILED);
		}
	}
	
	@PostMapping(path = "add")
	public ResponseEntity<?> addCar(@RequestBody Car car) {
		List<Car> cars = carService.findBy("license_plate", car.getLicense_plate());
		if(!MethodUtils.isListIsNullOrEmpty(cars)) {
			return new ResponseEntity<String>("The license plate allready register", HttpStatus.BAD_REQUEST);
		}
		car.setId(seqService.generateSequence(Car.SEQUENCE_NAME));
		car.setCreated_date(writeConverter.convert(ZonedDateTime.now(ZoneOffset.UTC)));
		car.setIsAvailable(true);
		return new ResponseEntity<Car>(carRepo.save(car),HttpStatus.ACCEPTED);
	}
	
	@GetMapping(path = "find/{id}")
	public ResponseEntity<?> findById(@PathVariable(name = "id") Long id) {
		Optional<Car> optCar = carRepo.findById(id);
		if(optCar.isEmpty()) {
			return new ResponseEntity<String>("Car not found",HttpStatus.NOT_FOUND);
		}
		Car car = optCar.get();
		CarDTO carDTO = getCarDtoObject(car);
		return new ResponseEntity<CarDTO>(carDTO,HttpStatus.FOUND);
	}
	
	@DeleteMapping(path = "delete/{id}")
	public ResponseEntity<?> deleteCar(@PathVariable(name = "id") Long id) {
		Optional<Car> optCar = carRepo.findById(id);
		if(optCar.isEmpty()) {
			return new ResponseEntity<String>("Car not found",HttpStatus.NOT_FOUND);
		}
		carRepo.delete(optCar.get());
		return new ResponseEntity<String>("Car removed succefully",HttpStatus.FOUND);
	}
	
	@PostMapping(path = "deSelectDriver/{id}")
	public ResponseEntity<?> removeDriver(@PathVariable(name = "id") Long id){
		Optional<Car> optCar = carRepo.findById(id);
		if(optCar.isEmpty()) {
			return new ResponseEntity<String>("Car not found",HttpStatus.NOT_FOUND);
		}
		
		Car car = optCar.get();
		if(MethodUtils.isObjectisNullOrEmpty(car.getDriverId())) {
			return new ResponseEntity<String>("No Driver available for this car",HttpStatus.NOT_FOUND);
		}
			
		Optional<Driver> opt = driverRepo.findById(car.getDriverId());
		Driver driver = opt.get();
		if(opt.isPresent()) {
			driver.setCar(null);
			driver.setCarID(null);
			driverRepo.save(driver);
		}
		car.setDriverId(null);
		car.setIsAvailable(true);
		carRepo.save(car);
		
		return new ResponseEntity<Car>(optCar.get(),HttpStatus.FOUND);
	}
	
	public CarDTO getCarDtoObject(Car car) {
		CarDTO carDTO = new CarDTO();
		carDTO.setConvertible(car.getConvertible());
		carDTO.setCreated_date(car.getCreated_date());
		carDTO.setDriverId(car.getDriverId());
		if(!MethodUtils.isObjectisNullOrEmpty(car.getDriverId())) {
			Optional<Driver> opt = driverRepo.findById(car.getDriverId());
			DriverDTO driverDTO = new DriverDTO();
			Driver driver = opt.get();
			driverDTO.setCreated_date(driver.getCreated_date());
			driverDTO.setContact_num(driver.getContact_num());
			driverDTO.setFull_name(driver.getFull_name());
			carDTO.setDriver(driverDTO);
		}
		carDTO.setEngine_type(car.getEngine_type());
		carDTO.setIsAvailable(car.getIsAvailable());
		carDTO.setLicense_plate(car.getLicense_plate());
		carDTO.setManufacturer(car.getManufacturer());
		carDTO.setModel(car.getModel());
		carDTO.setRating(car.getRating());
		carDTO.setSeat_cnt(car.getSeat_cnt());
		return carDTO;
	}
}
