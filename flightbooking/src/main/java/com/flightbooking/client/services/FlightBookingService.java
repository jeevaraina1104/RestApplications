package com.flightbooking.client.services;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.flightbooking.model.FlightModel;
import com.flightbooking.wrapper.FlightDataWrapper;



@Path("/flight")
public class FlightBookingService {
	private static FlightDataWrapper flightDataWrapper = FlightDataWrapper.getInstance();
	
	@GET
	@Path("/serviceInfo")
	public String serviceVersion() {
		return "dollar version 0.1";
	}
	
	//Inserting a new user into the db
	@POST
	@Path("/flightSearch")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String flightSearch(@Valid FlightModel request) {
		return flightDataWrapper.flightSearch(request);
	}	
	
	//Login the existing user into db
	@POST
	@Path("/flightBook")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String flightBook(@Valid FlightModel request) {
		return flightDataWrapper.flightBook(request);
	}	
	
	//Forgot Password tho check thw user is valid user
	@POST
	@Path("/flightCancel")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String flightCancel(@Valid FlightModel request) {
		return flightDataWrapper.flightCancel(request);
	}	
	
	//Resetting the old password with new passowrd
	@POST
	@Path("/checkSeatAvailability")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String checkSeatAvailability(@Valid FlightModel request) {
		return flightDataWrapper.checkSeatAvailability(request);
	}	
	
	
	
	
	
	

}
