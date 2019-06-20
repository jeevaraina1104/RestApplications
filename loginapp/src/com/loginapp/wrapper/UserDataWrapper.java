package com.loginapp.wrapper;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loginapp.common.Constant;
import com.loginapp.common.LoginAppUtils;
import com.loginapp.common.ResponseUtils;
import com.loginapp.datastore.UserDataStore;
import com.loginapp.logger.MyLogger;
import com.loginapp.model.UserModel;
import com.loginapp.wrapper.UserDataWrapper;
import com.wipro.c4.core.utils.common.constants.Constants;
import com.wipro.c4.core.utils.exception.ConfigException;
import com.wipro.c4.core.utils.exception.DatabaseException;
import com.wipro.c4.core.utils.outputResponse.OutputResponse;

public class UserDataWrapper {
	
	private static UserDataWrapper userDataWrapper;
	private static UserDataStore userDataStore = UserDataStore.getInstance();

	
	public static UserDataWrapper getInstance() {
		if(userDataWrapper != null)
			return userDataWrapper;
		
		return userDataWrapper = new UserDataWrapper();
	}
	
	private UserDataWrapper() {
	}
	
	//Performing insert operation 
	public String insertNewUser(UserModel request) {
		OutputResponse response = null;
		if(validate(request)) {
			response = insertNewUserToDB(request);
		} else {
			response = new OutputResponse();
			response = ResponseUtils.setFailure(Constant.INVALID_INPUT_MESSAGE);
			MyLogger.printError(Constant.INVALID_INPUT_MESSAGE);
		}
		return response.getResponse();
	}
	
	//Validating the input whether it is having empty string
	public boolean validate(UserModel request) {
		boolean result = true;
		if(request.getUsername().equals("") || request.getPassword().equals("") || request.getFirstname().equals("") ||
				request.getLastname().equals("")||request.getEmail().equals("")||request.getQuestion().equals("")||request.getAnswer().equals("")) {
			result = false;
		}
		return result;
	}
	
	// Calling the DataStore Class to perform insert operation
	private OutputResponse insertNewUserToDB(UserModel request) {
		JSONArray data = null;
		String data1 = null;
		String message = Constant.SUCCESS;
		OutputResponse response = new OutputResponse();
		try {
			data = userDataStore.getUsernameCount(request);
			MyLogger.printInfo( data.toString(), "Getting the UserName Count");


		} catch (Exception e) {
			System.out.print(e.getMessage());
			message = e.getMessage();
		}

		if (data.length() == 0) {
			try {
				Instant instant = Instant.now();
				request.setDatefield(instant.toString());
				JSONObject jsondata = new JSONObject();
				jsondata.put(Constant.DB_FIRSTNAME, request.getFirstname());
				jsondata.put(Constant.DB_LASTNAME, request.getLastname());
				jsondata.put(Constant.DB_EMAIL, request.getEmail());
				jsondata.put(Constant.DB_USERNAME, request.getUsername());
				jsondata.put(Constant.DB_PASSWORD, request.getPassword());
				jsondata.put(Constant.DB_QUESTION, request.getQuestion());
				jsondata.put(Constant.DB_ANSWER, request.getAnswer());
				jsondata.put(Constant.DB_USERID, request.getUserId());
				jsondata.put(Constant.DB_CREATED_DATE, request.getDatefield());
				data1 = userDataStore.insertNewUser(jsondata);

			} catch (Exception e) {
				e.printStackTrace();
				message = e.getMessage();
			}
			if (data1 != null) {
				response = ResponseUtils.setSuccess(Constant.ACCOUNT_ADDED);
				MyLogger.printInfo( data1, Constant.ACCOUNT_ADDED);
			}

		} else {
			MyLogger.printError(Constant.USERNAME_EXITS);
			response = ResponseUtils.setFailure(Constant.USERNAME_EXITS);
		}

		return response;
	}
	
	//Perform Login Operation
	public String loginUser(UserModel request) {
		OutputResponse response = null;		
		if( !(request.getUsername().equals("") && request.getPassword().equals(""))) {
			response  = loginUserToDB(request);
		} else {
			response = new OutputResponse();
			response = ResponseUtils.setFailure(Constant.INVALID_INPUT_MESSAGE);
			MyLogger.printError(Constant.INVALID_INPUT_MESSAGE);
		}
		return response.getResponse();
	}
	
	//Calling the dataStore class to perform login
	private OutputResponse loginUserToDB(UserModel request) {
		JSONObject data = null;
		OutputResponse response = new OutputResponse();
		String uname = request.getUsername();
		String password = request.getPassword();

		try {
			data = userDataStore.getUserDetails(uname, password);
			if (data != null && data.length() > 0) {
				MyLogger.printInfo( data.get("userId").toString(), Constant.LOGIN_SUCCESS+ data);
				response.setOutputCode(Constants.OUTPUT_CODE_SUCCESS);
				response.setOutputStatus(Constants.OUTPUT_STATUS_SUCCESS);
				response.setMessage(Constant.LOGIN_SUCCESS);
				response.setResponse(data.toString());
			}

		} catch (Exception e) {
			MyLogger.printError( "Login Failed");
			System.out.print(e.getMessage());
		}

		if (data == null) {
			MyLogger.printError(Constant.INVALID_USERNAME_PASSWORD);
			response = ResponseUtils.setFailure(Constant.INVALID_USERNAME_PASSWORD);
		}
		return response;
	}

	
	//To reset the password into the db
	public String resetPassword(UserModel request) {
		OutputResponse response = null;		
		if( !(request.getUsername().equals("") && request.getPassword().equals("") && request.getNewpassword().equals(""))) {
			response  = resetPasswordToDB(request);
		} else {
			response = new OutputResponse();
			response = ResponseUtils.setFailure(Constant.INVALID_INPUT_MESSAGE);
			MyLogger.printError(Constant.INVALID_INPUT_MESSAGE);

		}
		return response.getResponse();
	}
	
	// Calling the datastore to perform resetting the password
	private OutputResponse resetPasswordToDB(UserModel request) {
		JSONObject data = null;
		String data1 = null;
		String message = Constant.SUCCESS;
		OutputResponse response = new OutputResponse();
		String username = request.getUsername();
		String oldpassword = request.getPassword();
		String newpassword = request.getNewpassword();
		try {
			data = userDataStore.getUserDetails(username, oldpassword);
			if (data != null && data.length() > 0) {
				MyLogger.printInfo( data.toString(), "Getting User Details from db");
				data1 = userDataStore.updatePassword(username, newpassword);
				if (data1 != null) {
					MyLogger.printInfo( data1.toString(), Constant.PASSWORD_RESET);
					response = ResponseUtils.setSuccess(Constant.PASSWORD_RESET);
				}
			}
		} catch (Exception e) {
			System.out.print(e.getMessage());
			message = e.getMessage();
		}
		if (data1 == null || data == null) {
			response = ResponseUtils.setFailure(Constant.INVALID_INPUT_GIVEN_MESSAGE);
			MyLogger.printError(Constant.INVALID_INPUT_GIVEN_MESSAGE);

		}
		return response;
	}
	
	//To perform the forget Password in the db
	public String forgetPassword(UserModel request) {
		OutputResponse response = null;
		if (!(request.getUsername().equals("") && request.getQuestion().equals("") && request.getAnswer().equals(""))) {
			response = forgetPasswordToDB(request);
		} else {
			response = new OutputResponse();
			response = ResponseUtils.setFailure(Constant.INVALID_INPUT_MESSAGE);
			MyLogger.printError(Constant.INVALID_INPUT_MESSAGE);

		}
		return response.getResponse();
	}
	
	//Clling the datastore class to perform forgot password
	private OutputResponse forgetPasswordToDB(UserModel request) {
		JSONObject data = null;
		String data1 = null;
		String message = Constant.SUCCESS;
		OutputResponse response = new OutputResponse();
		String username = request.getUsername();
		String question = request.getQuestion();
		String answer = request.getAnswer();
		try {
			data = userDataStore.getUserDetails(username);
			if (data != null && data.get(Constant.DB_QUESTION).equals(question)
					&& data.get(Constant.DB_ANSWER).equals(answer)) {
				MyLogger.printInfo( data.toString(), Constant.VALID_USER);
				response = ResponseUtils.setSuccess(Constant.VALID_USER);
			} else {
				response = ResponseUtils.setFailure(Constant.INVALID_INPUT_MESSAGE);
				MyLogger.printError(Constant.INVALID_INPUT_MESSAGE);

			}
		} catch (Exception e) {
			System.out.print(e.getMessage());
			message = e.getMessage();
		}

		if (data == null) {
			response = ResponseUtils.setFailure(Constant.INVALID_INPUT_GIVEN_MESSAGE);
			MyLogger.printError(Constant.INVALID_INPUT_GIVEN_MESSAGE);

		}

		return response;
	}
	
	//To perform change password in the database
	public String changePassword(UserModel request) {
		OutputResponse response = null;
		if (!(request.getUsername().equals("") && request.getPassword().equals(""))) {
			response = changePasswordToDB(request);
		} else {
			response = new OutputResponse();
			response = ResponseUtils.setFailure(Constant.INVALID_INPUT_MESSAGE);
			MyLogger.printError(Constant.INVALID_INPUT_MESSAGE);
		}
		return response.getResponse();
	}
	
	// Calling the datasore class to perform change password
	private OutputResponse changePasswordToDB(UserModel request) {
		String data = null;

		String message = Constant.SUCCESS;
		OutputResponse response = new OutputResponse();
		String username = request.getUsername();
		String password = request.getPassword();
		try {
			data = userDataStore.updatePassword(username, password);
			if (data != null) {
				MyLogger.printInfo( data.toString(), Constant.PASSWORD_CHANGE);
				response = ResponseUtils.setSuccess(Constant.PASSWORD_CHANGE);
			}

		} catch (Exception e) {
			System.out.print(e.getMessage());
			message = e.getMessage();
		}

		if (data == null) {
			response = ResponseUtils.setFailure(Constant.INVALID_INPUT_GIVEN_MESSAGE);
			MyLogger.printError(Constant.INVALID_INPUT_GIVEN_MESSAGE);

		}

		return response;
	}
	
	//To view the profile of the user
	public String viewProfile(UserModel request) {
		OutputResponse response = null;
		if (!(request.getUsername().equals("") && request.getPassword().equals(""))) {
			response = viewProfileFromDB(request);
		} else {
			response = new OutputResponse();
			response = ResponseUtils.setFailure(Constant.INVALID_INPUT_MESSAGE);
			MyLogger.printError(Constant.INVALID_INPUT_MESSAGE);

		}
		return response.getResponse();
	}
	
	//To view the profile from the databse
	private OutputResponse viewProfileFromDB(UserModel request) {
		JSONObject data = null;
		OutputResponse response = new OutputResponse();
		String uname = request.getUsername();
		String password = request.getPassword();

		try {
			data = userDataStore.getUserDetails(uname, password);
			if (data != null && data.length() > 0) {
				MyLogger.printInfo( data.toString(), Constant.SUCCESS);
				response = ResponseUtils.setSuccessWithData(Constant.SUCCESS,data.toString());
			}

		} catch (Exception e) {
			System.out.print(e.getMessage());
		}

		if (data == null) {
			response = ResponseUtils.setFailure(Constant.INVALID_USERNAME_PASSWORD);
			MyLogger.printError(Constant.INVALID_USERNAME_PASSWORD);
		}
		return response;
	}

	//For edit the profile
	public String editProfile(UserModel request) {
		OutputResponse response = null;
		if(validateEdit(request)) {
			response = editProfileToDB(request);
		} else {
			response = new OutputResponse();
			response = ResponseUtils.setFailure(Constant.INVALID_INPUT_MESSAGE);
			MyLogger.printError(Constant.INVALID_INPUT_MESSAGE);
		}
		return response.getResponse();
	}
	
	//Validating the input whether it is having empty string
	public boolean validateEdit(UserModel request) {
		boolean result = true;
		if(request.getUsername().equals("") || request.getFirstname().equals("") ||
				request.getLastname().equals("")||request.getEmail().equals("")||request.getQuestion().equals("")||request.getAnswer().equals("")) {
			result = false;
		}
		return result;
	}
	
	//Editting the profile in the databse
	private OutputResponse editProfileToDB(UserModel request) {
		JSONArray data = null;
		String data1 = null;
		String message = Constant.SUCCESS;
		OutputResponse response = new OutputResponse();
			try {
				JSONObject jsondata = new JSONObject();
				jsondata.put(Constant.DB_FIRSTNAME, request.getFirstname());
				jsondata.put(Constant.DB_LASTNAME, request.getLastname());
				jsondata.put(Constant.DB_EMAIL, request.getEmail());
				jsondata.put(Constant.DB_QUESTION, request.getQuestion());
				jsondata.put(Constant.DB_ANSWER, request.getAnswer());
				data1 = userDataStore.editUserDetails(request.getUsername(), jsondata);
			} catch (Exception e) {
				e.printStackTrace();
				message = e.getMessage();
			}
			if (data1 != null) {
				MyLogger.printInfo( data.toString(), Constant.PROFILE_EDIT);
				response = ResponseUtils.setSuccess(Constant.PROFILE_EDIT);
			}
			return response;
	}

	//Update the profile with the help of userId
	public String updateProfile(UserModel request) {
		String result = "";
		long id;
		String count = "";
		String newId = "";

		if (request.getUserId() != 0) {
			result = editProfile(request);
		} else {
			try {
				JSONObject userId = userDataStore.getUserIdCount();
				if (userId != null) {
					count = userId.get("userCount").toString();
					id = Long.parseLong(count);
					id++;
					request.setUserId(id);
					newId = Long.toString(id);
					String response = userDataStore.updateUserIdCount(newId);
					if (response != null) {
						result = insertNewUser(request);
					}

				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return result;
	}
	
	//Get all user detail 
	public String getAllUserDetails(UserModel request) {
		OutputResponse response = null;		
		if(validateGet(request)) {
			response  = getAllUserDetailsFromDb(request);
		} else {
			response = new OutputResponse();
			response = ResponseUtils.setFailure(Constant.INVALID_INPUT_MESSAGE);
			MyLogger.printError(Constant.INVALID_INPUT_MESSAGE);
		}
		return response.getResponse();
	}
	
	private boolean validateGet(UserModel request) {
		boolean result = true;
		if(request.getLimit()==0 || request.getSortBy().equals("") ||
				request.getSortOrder()==0) {
			result = false;
		}
		return result;
	}

	//Getting all the user from the databse
	private OutputResponse getAllUserDetailsFromDb(UserModel request) {
		String data = null;
		OutputResponse response = new OutputResponse();
		int limit = request.getLimit();
		int sortOrder = request.getSortOrder();
		String sortBy = request.getSortBy();
		JSONArray sortedArray = new JSONArray();

		try {
			data = userDataStore.getAllUserDetails();
			if (data != null && data.length() > 0) {
				sortedArray = sortData(data, limit, sortOrder, sortBy);
				response = ResponseUtils.setSuccessWithData(Constant.SUCCESS, sortedArray.toString());
				MyLogger.printInfo(sortedArray.toString(), Constant.SUCCESS);
			}

		} catch (Exception e) {
			MyLogger.printError("Get User Details Failed");
			System.out.print(e.getMessage());
		}

		if (data == null) {
			MyLogger.printError(Constant.INVALID_USERNAME_PASSWORD);
			response = ResponseUtils.setFailure(Constant.INVALID_USERNAME_PASSWORD);
		}
		return response;
	}
	
	//Sorting the Jsonarray based on limit,sortorder,sortby
	private JSONArray sortData(String data, int limit, int sortOrder, String sortBy) {
		JSONArray sortedJsonArray = new JSONArray();
		JSONArray jsonArr = new JSONArray(data);

		List<JSONObject> jsonValues = new ArrayList<JSONObject>();
		for (int i = 0; i < jsonArr.length(); i++) {
			jsonValues.add(jsonArr.getJSONObject(i));
		}
		Collections.sort(jsonValues, new Comparator<JSONObject>() {
			private final String KEY_NAME = sortBy;
			@Override
			public int compare(JSONObject a, JSONObject b) {
				String valA = new String();
				String valB = new String();
				int value;
				try {
					valA = a.get(KEY_NAME).toString();
					valB = b.get(KEY_NAME).toString();
				} catch (JSONException e) {
					e.printStackTrace();
				}
				if (sortOrder == 1) {
					value = valA.compareTo(valB);
				} else {
					value = valB.compareTo(valA);
				}
				return value;
			}
		});
		for (int i = 0; i < jsonArr.length(); i++) {
			sortedJsonArray.put(jsonValues.get(i));
		}
		JSONArray limitJsonArray = new JSONArray();
		if (limit < sortedJsonArray.length()) {
			for (int i = 0; i < limit; i++) {
				JSONObject limitObj = sortedJsonArray.optJSONObject(i);
				if (limitObj != null) {
					limitJsonArray.put(limitObj);
				}
			}
		} else {
			limitJsonArray = sortedJsonArray;
		}
		return limitJsonArray;
	}
}

	


