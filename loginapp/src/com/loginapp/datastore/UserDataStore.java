package com.loginapp.datastore;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loginapp.common.Constant;
import com.loginapp.datastore.UserDataStore;
import com.loginapp.logger.Log4j;
import com.loginapp.model.UserModel;
import com.wipro.c4.core.utils.exception.ConfigException;
import com.wipro.c4.core.utils.exception.DatabaseException;
import com.wipro.c4.core.utils.persistence.PersistenseService;
import com.wipro.c4.core.utils.persistence.model.DataInput;
import com.wipro.c4.core.utils.persistence.model.Search;

public class UserDataStore {
	
private static UserDataStore userDataStore;
	
	public static UserDataStore getInstance() {
		if(userDataStore != null)
			return userDataStore;
		
		return userDataStore = new UserDataStore();
	}
	
	private UserDataStore() {
	}
	
	//Inserting the new user into the databse
	public String insertNewUser(JSONObject jsondata) throws DatabaseException, ConfigException {
		String dbresponse = "";
		DataInput dataInput = new DataInput(1001, Constant.DB_NAME, null, jsondata);
		dbresponse = new PersistenseService().insertDetails(dataInput);
		return dbresponse;
	}
	
	//Getting the userdetails from the database for login operation with both username and password
	public JSONObject getUserDetails(String username, String password)
			throws JSONException, DatabaseException, ConfigException {
		Search search = new Search();
		List<Search> searchList = new ArrayList<Search>();
		searchList.add(new Search(Constant.DB_USERNAME, username));
		searchList.add(new Search(Constant.DB_PASSWORD, password));
		search.setAND(searchList);
		DataInput dataInput = new DataInput(1001,  Constant.DB_NAME, search, null);
		String dbResponse = new PersistenseService().viewDetails(dataInput);
		return new JSONArray(dbResponse).getJSONObject(0);
	}
	
	//Getting the username count from the databse to check whether the username exists or not 
	public JSONArray getUsernameCount(UserModel request) throws DatabaseException, ConfigException {
		Search search = new Search(Constant.DB_USERNAME,request.getUsername());
		DataInput dataInput = new DataInput(1001,  Constant.DB_NAME, search, null);
		String dbResponse = new PersistenseService().fetchCount(dataInput);
		return new JSONArray(dbResponse);

	}

	//Updating the old passowrd with new password in db
	public String updatePassword(String username, String newpassword) throws DatabaseException, ConfigException {
		JSONObject data = new JSONObject();
		data.put(Constant.DB_PASSWORD,newpassword);
		Search search = new Search(Constant.DB_USERNAME,username);
		DataInput dataInput = new DataInput(1001, Constant.DB_NAME, search, data);
		String dbResponse = new PersistenseService().updateDetails(dataInput);
		return dbResponse;
	}
	
	//Getting the userdetails from the database for login operation with username
	public JSONObject getUserDetails(String username) throws DatabaseException, ConfigException {
		Search search = new Search(Constant.DB_USERNAME,username);
        DataInput dataInput = new DataInput(1001,  Constant.DB_NAME, search, null);
        String dbResponse = new PersistenseService().viewDetails(dataInput);
        return new JSONArray(dbResponse).getJSONObject(0);
	}
	
	// Updating the old passowrd with new password in db
	public String editUserDetails(String username, JSONObject jsondata) throws DatabaseException, ConfigException {
		Search search = new Search(Constant.DB_USERNAME, username);
		DataInput dataInput = new DataInput(1001, Constant.DB_NAME, search, jsondata);
		String dbResponse = new PersistenseService().updateDetails(dataInput);
		return dbResponse;
	}
	
	public JSONObject getUserIdCount() throws DatabaseException, ConfigException {
		DataInput dataInput = new DataInput(1001, Constant.DB_NAME_CONFIG);
		String dbResponse = new PersistenseService().viewDetails(dataInput);
		return new JSONArray(dbResponse).getJSONObject(0);
	}
	

	public String updateUserIdCount(String newId) throws DatabaseException, ConfigException {
		JSONObject data = new JSONObject();
		data.put(Constant.DB_USERCOUNT,newId);
		DataInput dataInput = new DataInput(1001, Constant.DB_NAME_CONFIG,null,data);
		String dbResponse = new PersistenseService().updateDetails(dataInput);
		return dbResponse;
		
	}

	public String getAllUserDetails() throws DatabaseException, ConfigException {
	
		DataInput dataInput = new DataInput(1001,  Constant.DB_NAME);
		String dbResponse = new PersistenseService().viewDetails(dataInput);
		return	dbResponse;
		}
}
