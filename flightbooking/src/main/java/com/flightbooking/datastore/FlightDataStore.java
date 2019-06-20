package com.flightbooking.datastore;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.flightbooking.common.Constant;
import com.wipro.c4.core.utils.exception.ConfigException;
import com.wipro.c4.core.utils.exception.DatabaseException;
import com.wipro.c4.core.utils.persistence.PersistenseService;
import com.wipro.c4.core.utils.persistence.model.DataInput;
import com.wipro.c4.core.utils.persistence.model.Search;

public class FlightDataStore {
private static FlightDataStore flightDataStore;
	
	public static FlightDataStore getInstance() {
		if(flightDataStore != null)
			return flightDataStore;
		
		return flightDataStore = new FlightDataStore();
	}
	
	private FlightDataStore() {
	}

	
	//To get flightDetails from the database
	public JSONObject getFlightDetails(String from, String to, String date) throws DatabaseException, ConfigException {
		Search search = new Search();
		List<Search> searchList = new ArrayList<Search>();
		searchList.add(new Search(Constant.FROM, from));
		searchList.add(new Search(Constant.TO, to));
		searchList.add(new Search(Constant.DATE,date));
		search.setAND(searchList);
		DataInput dataInput = new DataInput(1001,  Constant.DB_FLIGHT_NAME, search, null);
		String dbResponse = new PersistenseService().viewDetails(dataInput);
		return new JSONArray(dbResponse).getJSONObject(0);
	}

	//To check the seat availability from db
	public JSONObject checkSeatAvailability(String flightNumber, String date) throws DatabaseException, ConfigException {
		Search search = new Search();
		List<Search> searchList = new ArrayList<Search>();
		searchList.add(new Search(Constant.FLIGHTNUMBER, flightNumber));
		searchList.add(new Search(Constant.DATE,date));
		search.setAND(searchList);
		DataInput dataInput = new DataInput(1001,  Constant.DB_FLIGHT_NAME, search, null);
		String dbResponse = new PersistenseService().viewDetails(dataInput);
		return new JSONArray(dbResponse).getJSONObject(0);
	}
	
	//to get the flight details for cancel
	public JSONObject getFlightDetailsForCancel(String tripNumber) throws DatabaseException, ConfigException {
		Search search = new Search(Constant.TRIPNUMBER,tripNumber);
		DataInput dataInput = new DataInput(1001,  Constant.DB_TRIP_NAME, search, null);
		String dbResponse = new PersistenseService().viewDetails(dataInput);
		return new JSONArray(dbResponse).getJSONObject(0);
	}
	
	//To update the flight cancel in db
	public String updateFlightCancel(String tripNumber, JSONObject data) throws DatabaseException, ConfigException {
		Search search = new Search(Constant.TRIPNUMBER,tripNumber);
		DataInput dataInput = new DataInput(1001,  Constant.DB_TRIP_NAME, search, data);
		String dbResponse = new PersistenseService().updateDetails(dataInput);
		return  dbResponse;
	}

	//to update the seat available in db
	public String updateSeatAvailable(String from, String to, String date, int updateStatus) throws DatabaseException, ConfigException {
		Search search = new Search();
		List<Search> searchList = new ArrayList<Search>();
		searchList.add(new Search(Constant.FROM, from));
		searchList.add(new Search(Constant.TO, to));
		searchList.add(new Search(Constant.DATE,date));
		search.setAND(searchList);
		JSONObject data = new JSONObject();
		data.put("seatsAvailable",updateStatus);
		DataInput dataInput = new DataInput(1001,  Constant.DB_FLIGHT_NAME, search, data);
		String dbResponse = new PersistenseService().updateDetails(dataInput);
		return dbResponse;
	}
	
	//To generate the trip number getting number from db
	public JSONObject getTripNumberCount() throws DatabaseException, ConfigException {
		DataInput dataInput = new DataInput(1001, Constant.DB_NAME_CONFIG);
		String dbResponse = new PersistenseService().viewDetails(dataInput);
		return new JSONArray(dbResponse).getJSONObject(0);
	}
	
	//Update the tripnumber after generating 
	public String updateTripNumberCount(String newId) throws DatabaseException, ConfigException {
		JSONObject data = new JSONObject();
		data.put(Constant.DB_USERCOUNT,newId);
		DataInput dataInput = new DataInput(1001, Constant.DB_NAME_CONFIG,null,data);
		String dbResponse = new PersistenseService().updateDetails(dataInput);
		return dbResponse;
		
	}
	
	//To book the flight for the user
	public String bookFlight(JSONObject jsondata) throws DatabaseException, ConfigException {
		String dbresponse = "";
		DataInput dataInput = new DataInput(1001, Constant.DB_FLIGHT_NAME, null, jsondata);
		dbresponse = new PersistenseService().insertDetails(dataInput);
		return dbresponse;
	}
	
}
