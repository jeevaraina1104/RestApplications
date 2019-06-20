package com.loginapp.common;

import java.util.logging.FileHandler;

import org.apache.log4j.Logger;

import com.loginapp.main.AppMain;

public class Constant {

	public static final String DB_NAME = "u_login_data";
	public static final String DB_NAME_CONFIG="u_user_config";
	public static final String DB_USERNAME = "username";
	public static final String DB_FIRSTNAME = "firstname";
	public static final String DB_LASTNAME = "lastname";
	public static final String DB_EMAIL = "email";
	public static final String DB_PASSWORD = "password";
	public static final String DB_QUESTION = "question";
	public static final String DB_ANSWER = "answer";
	public static final String DB_USERID = "userId";
	public static final String DB_CREATED_DATE = "created_date";
	public static final String DB_USERCOUNT = "userCount";
	public static final String INVALID_INPUT_MESSAGE = "Invalid input";
	public static final String INVALID_INPUT_GIVEN_MESSAGE = "Invalid input given";
	public static final String USERNAME_EXITS = "Username Already Exits";
	public static final String INVALID_USERNAME_PASSWORD = "Invalid Username and password";
	public static final String PASSWORD_CHANGE = "Password change successfully";
	public static final String PASSWORD_RESET = "Password reset successfully";
	public static final String ACCOUNT_ADDED = "User Account Added";
	public static final String LOGIN_SUCCESS = "Login success";
	public static final String VALID_USER = "User Details are valid";
	public static final String SUCCESS = "success";
	public static final String PROFILE_EDIT = "User details edited successfully";
	public final static Logger LOGGER = Logger.getLogger(AppMain.class);

	
	
	
	

	

}
