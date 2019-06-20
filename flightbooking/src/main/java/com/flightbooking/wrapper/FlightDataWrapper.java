package com.flightbooking.wrapper;

import org.json.JSONObject;

import com.flightbooking.common.Constant;
import com.flightbooking.common.ResponseUtils;
import com.flightbooking.datastore.FlightDataStore;
import com.flightbooking.logger.MyLogger;
import com.flightbooking.model.FlightModel;
import com.wipro.c4.core.utils.common.constants.Constants;
import com.wipro.c4.core.utils.exception.ConfigException;
import com.wipro.c4.core.utils.exception.DatabaseException;
import com.wipro.c4.core.utils.outputResponse.OutputResponse;

public class FlightDataWrapper {
	
	private static FlightDataWrapper flightDataWrapper;
	private static FlightDataStore flightDataStore = FlightDataStore.getInstance();

	
	public static FlightDataWrapper getInstance() {
		if(flightDataWrapper != null)
			return flightDataWrapper;
		
		return flightDataWrapper = new FlightDataWrapper();
	}
	
	private FlightDataWrapper() {
	}

	//To validate the user request and call flightSearchfromDb method
	public String flightSearch(FlightModel request) {
		OutputResponse response = null;
		if(validateFlightSearch(request)) {
			response = flightSearchFromDb(request);
		} else {
			response = new OutputResponse();
			response = ResponseUtils.setFailure(Constant.INVALID_INPUT_MESSAGE);
			MyLogger.printInfo(Constant.INVALID_INPUT_MESSAGE);
		}
		return response.getResponse();
	}
	
	//Validating the user request for flight Search
	private boolean validateFlightSearch(FlightModel request) {
		boolean result = true;
		if(request.getFrom().equals("") || request.getTo().equals("") || request.getDate().equals("")) {
			result = false;
		}
		return result;
	}

	//Perform some operation and calling the datastore class method
	private OutputResponse flightSearchFromDb(FlightModel request) {
		JSONObject flightData = null;
		OutputResponse response = new OutputResponse();
		

		try {
			flightData = flightDataStore.getFlightDetails(request.getFrom(),request.getTo(),request.getDate());
			if (flightData != null && flightData.length() > 0) {
				MyLogger.printInfo( flightData.get("flightNumber").toString(), Constant.SUCCESS+ flightData);
				response =ResponseUtils.setSuccessWithData(Constant.SUCCESS, flightData.toString());
			}

		} catch (Exception e) {
			MyLogger.printError(Constant.INVALID_FLIGHT_DETAILS,e.toString());
			response = ResponseUtils.setFailure(Constant.INVALID_FLIGHT_DETAILS + e.toString());
			System.out.print(e.getMessage());
		}

		if (flightData == null) {
			MyLogger.printInfo(Constant.INVALID_FLIGHT_DETAILS);
			response = ResponseUtils.setFailure(Constant.INVALID_FLIGHT_DETAILS);
		}
		return response;
	}

	
	//To validate the user request and call flightBookfromDb method
	public String flightBook(FlightModel request) {
		OutputResponse response = null;
		if(validateFlightBook(request)) {
			response = flightBooktoDb(request);
		} else {
			response = new OutputResponse();
			response = ResponseUtils.setFailure(Constant.INVALID_INPUT_MESSAGE);
			MyLogger.printInfo(Constant.INVALID_INPUT_MESSAGE);
		}
		return response.getResponse();
	}
	
	//Validating the user request for flight Book
	private boolean validateFlightBook(FlightModel request) {
		boolean result = true;
		if(request.getFlightNumber().equals("") || request.getDate().equals("") || request.getDepartureTime().equals("")) {
			result = false;
		}
		return result;
	}

	//Perform some operation and calling the datastore class method
	private OutputResponse flightBooktoDb(FlightModel request) {
		JSONObject checkSeatData = null;
		String insertData = null;
		OutputResponse response = new OutputResponse();
		JSONObject tripData = new JSONObject();
		String flightNumber = request.getFlightNumber();
		String date = request.getDate();

		try {
			checkSeatData = flightDataStore.checkSeatAvailability(flightNumber, date);
			if (checkSeatData != null && checkSeatData.length() > 0
					&& !(checkSeatData.get("seatsAvailable").equals(0))) {
				JSONObject userId = flightDataStore.getTripNumberCount();
				if (userId != null) {
					String count = userId.get("userCount").toString();
					long id = Long.parseLong(count);
					id++;
					String newId = Long.toString(id);
					request.setTripNumber(newId);
					int updateSeatStatus = checkSeatData.getInt("seatsAvailable");
					updateSeatStatus--;
					String tripresponse = flightDataStore.updateTripNumberCount(newId);
					String updateresponse = flightDataStore.updateSeatAvailable(checkSeatData.get("from").toString(),
							checkSeatData.get("to").toString(), date, updateSeatStatus);
					if (tripresponse != null && updateresponse != null) {
						JSONObject flightdata = new JSONObject();
						flightdata.put(Constant.FLIGHTNUMBER, flightNumber);
						flightdata.put(Constant.FROM, checkSeatData.get("from"));
						flightdata.put(Constant.TO, checkSeatData.get("to"));
						flightdata.put(Constant.DATE, date);
						flightdata.put(Constant.DEPATURE_TIME, request.getDepartureTime());
						flightdata.put(Constant.STATUS, Constant.TRIP_BOOKED);
						
						tripData.put(Constant.EMP_ID, request.getEmpId());
						tripData.put(Constant.TRIPNUMBER, request.getTripNumber());
						tripData.put(Constant.FLIGHT, flightdata);
						insertData = flightDataStore.bookFlight(tripData);
					}
				}
			}
		} catch (DatabaseException e) {
			MyLogger.printError(Constant.INVALID_FLIGHT_DETAILS, e.toString());
			response = ResponseUtils.setFailure(Constant.INVALID_FLIGHT_DETAILS + e.toString());
			System.out.print(e.getMessage());
		} catch (ConfigException e) {
			MyLogger.printError(Constant.INVALID_FLIGHT_DETAILS, e.toString());
			response = ResponseUtils.setFailure(Constant.INVALID_FLIGHT_DETAILS + e.toString());
			System.out.print(e.getMessage());
		}
		if (insertData != null) {
			response = ResponseUtils.setSuccessWithData(Constant.FLIGHT_BOOKED, tripData.toString());
			MyLogger.printInfo(tripData.toString(), Constant.FLIGHT_BOOKED);
		}

		return response;
	}

	//To validate the user request and call flightCancelToDb method
	public String flightCancel(FlightModel request) {
		OutputResponse response = null;
		if(validateflightCancel(request)) {
			response = flightCanceltoDb(request);
		} else {
			response = new OutputResponse();
			response = ResponseUtils.setFailure(Constant.INVALID_INPUT_MESSAGE);
			MyLogger.printInfo(Constant.INVALID_INPUT_MESSAGE);
		}
		return response.getResponse();
	}

	//Validating the user request for flight Cancel
	private boolean validateflightCancel(FlightModel request) {
		boolean result = true;
		if(request.getTripNumber().equals("")) {
			result = false;
		}
		return result;
	}

	//Perform some operation and calling the datastore class method
	private OutputResponse flightCanceltoDb(FlightModel request) {
		JSONObject flightData = null;
		String cancelData = null;
		JSONObject availData = null;
		String updateData = null;
		OutputResponse response = new OutputResponse();
		String tripNumber = request.getTripNumber();
		try {
			flightData = flightDataStore.getFlightDetailsForCancel(tripNumber);
			if (flightData != null && flightData.length() > 0) {
				JSONObject flightdata = flightData.getJSONObject("flight");
				flightdata.put("status", Constant.TRIP_CANCEL);
				flightData.put("flight", flightdata);
				cancelData = flightDataStore.updateFlightCancel(tripNumber, flightData);
				if (cancelData != null && cancelData.length() > 0) {
					String date = flightData.getJSONObject("flight").get("date").toString();
					String from = flightData.getJSONObject("flight").get("from").toString();
					String to = flightData.getJSONObject("flight").get("to").toString();
					availData = flightDataStore.getFlightDetails(from, to, date);
					if (availData != null && availData.length() > 0) {
						int updateSeatStatus = availData.getInt("seatsAvailable");
						updateSeatStatus++;
						updateData = flightDataStore.updateSeatAvailable(from, to, date, updateSeatStatus);
						if (updateData != null && updateData.length() > 0) {
							MyLogger.printInfo(flightData.get("flight").toString(), Constant.SUCCESS + flightData);
							response = ResponseUtils.setSuccessWithData(Constant.SUCCESS, flightData.toString());
						}
					}
				}
			}

		} catch (Exception e) {
			MyLogger.printError(Constant.INVALID_FLIGHT_DETAILS, e.toString());
			response = ResponseUtils.setFailure(Constant.INVALID_FLIGHT_DETAILS + e.toString());
			System.out.print(e.getMessage());
		}

		if (flightData == null) {
			MyLogger.printInfo(null, Constant.INVALID_FLIGHT_DETAILS);
			response = ResponseUtils.setFailure(Constant.INVALID_FLIGHT_DETAILS);
		}
		return response;
	}
	
	//To validate the user request and call checkSeatAvailabilityFromDb method
	public String checkSeatAvailability(FlightModel request) {
		OutputResponse response = null;
		if(validatecheckSeatAvailability(request)) {
			response = checkSeatAvailabilityFromDb(request);
		} else {
			response = new OutputResponse();
			response = ResponseUtils.setFailure(Constant.INVALID_INPUT_MESSAGE);
			MyLogger.printInfo(Constant.INVALID_INPUT_MESSAGE);
		}
		return response.getResponse();
	}

	//Validating the user request for flight seat availability
	private boolean validatecheckSeatAvailability(FlightModel request) {
		boolean result = true;
		if(request.getFlightNumber().equals("") || request.getDate().equals("") ) {
			result = false;
		}
		return result;
	}

	//Perform some operation and calling the datastore class method
	private OutputResponse checkSeatAvailabilityFromDb(FlightModel request) {
		JSONObject checkData = null;
		OutputResponse response = new OutputResponse();
		try {
			checkData = flightDataStore.checkSeatAvailability(request.getFlightNumber(),request.getDate());
			if (checkData != null && checkData.length() > 0) {
				MyLogger.printInfo( checkData.get("seatsAvailable").toString(), Constant.SUCCESS+ checkData);
				response =ResponseUtils.setSuccessWithData(Constant.SUCCESS, checkData.toString());
			}

		} catch (Exception e) {
			MyLogger.printInfo(Constant.INVALID_FLIGHT_DETAILS + e.toString());
			response = ResponseUtils.setFailure(Constant.INVALID_FLIGHT_DETAILS + e.toString());
			System.out.print(e.getMessage());
		}

		if (checkData == null) {
			MyLogger.printInfo(Constant.INVALID_FLIGHT_DETAILS);
			response = ResponseUtils.setFailure(Constant.INVALID_FLIGHT_DETAILS);
		}
		return response;
	}

}
