package com.flightbooking.model;

import org.hibernate.validator.constraints.NotEmpty;

import io.dropwizard.Configuration;

public class FlightModel extends Configuration{
	private String flightNumber;
	private String from;
	private String to;
	
	@NotEmpty
	private String date;
	private int empId;
	private String name;
	private String email;
	private String tripNumber;
	private String departureTime;
	private String status;
	private int seatsAvailable;
	private int flight;
	public String getFlightNumber() {
		return flightNumber;
	}
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTripNumber() {
		return tripNumber;
	}
	public void setTripNumber(String tripNumber) {
		this.tripNumber = tripNumber;
	}
	public String getDepartureTime() {
		return departureTime;
	}
	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getSeatsAvailable() {
		return seatsAvailable;
	}
	public void setSeatsAvailable(int seatsAvailable) {
		this.seatsAvailable = seatsAvailable;
	}
	public int getFlight() {
		return flight;
	}
	public void setFlight(int flight) {
		this.flight = flight;
	}
	
	

}
