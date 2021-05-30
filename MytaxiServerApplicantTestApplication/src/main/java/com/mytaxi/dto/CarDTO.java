package com.mytaxi.dto;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_EMPTY)
public class CarDTO {

	public static final String SEQUENCE_NAME = "users_sequence";

	private Long id;

	private String license_plate;

	private Integer seat_cnt;

	private Float rating;

	private String engine_type;

	private String model;

	private String manufacturer;

	private Boolean convertible;

	private Boolean isAvailable;

	private Date created_date;
	
	@JsonInclude(Include.NON_NULL)
	private Long driverId;
	
	@JsonInclude(Include.NON_NULL)
	private DriverDTO driver;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLicense_plate() {
		return license_plate;
	}

	public void setLicense_plate(String license_plate) {
		this.license_plate = license_plate;
	}

	public Integer getSeat_cnt() {
		return seat_cnt;
	}

	public void setSeat_cnt(Integer seat_cnt) {
		this.seat_cnt = seat_cnt;
	}

	public Float getRating() {
		return rating;
	}

	public void setRating(Float rating) {
		this.rating = rating;
	}

	public String getEngine_type() {
		return engine_type;
	}

	public void setEngine_type(String engine_type) {
		this.engine_type = engine_type;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public Boolean getConvertible() {
		return convertible;
	}

	public void setConvertible(Boolean convertible) {
		this.convertible = convertible;
	}

	public Boolean getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(Boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	public Date getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}

	public DriverDTO getDriver() {
		return driver;
	}

	public void setDriver(DriverDTO driver) {
		this.driver = driver;
	}

	public Long getDriverId() {
		return driverId;
	}

	public void setDriverId(Long driverId) {
		this.driverId = driverId;
	}


//	public Manufacturer getManufacturer() {
//		return manufacturer;
//	}
//
//	public void setManufacturer(Manufacturer manufacturer) {
//		this.manufacturer = manufacturer;
//	}

}
