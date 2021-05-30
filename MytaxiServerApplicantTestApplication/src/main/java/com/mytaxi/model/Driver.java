package com.mytaxi.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Document(collection = "driver")
public class Driver {

	@Transient
	public static final String SEQUENCE_NAME = "drivers_sequence";
	
	@Id
	private Long id;

	private String full_name;

	private Long contact_num;

	private Date created_date;
	
	@JsonInclude(Include.NON_NULL)
	private Car car;

	@JsonInclude(Include.NON_NULL)
	private Long carID;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFull_name() {
		return full_name;
	}

	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}

	public Long getContact_num() {
		return contact_num;
	}

	public void setContact_num(Long contact_num) {
		this.contact_num = contact_num;
	}

	public Date getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public Long getCarID() {
		return carID;
	}

	public void setCarID(Long carID) {
		this.carID = carID;
	}

	public Driver() {
		// TODO Auto-generated constructor stub
	}
	public Driver(Long id, String full_name, Long contact_num, Date created_date, Car car, Long carID) {
		super();
		this.id = id;
		this.full_name = full_name;
		this.contact_num = contact_num;
		this.created_date = created_date;
		this.car = car;
		this.carID = carID;
	}

	
}
