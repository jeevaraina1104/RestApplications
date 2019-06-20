package com.loginapp.client.services;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.loginapp.wrapper.UserDataWrapper;

import ch.qos.logback.classic.Logger;

import com.loginapp.common.Constant;
import com.loginapp.datastore.UserDataStore;
import com.loginapp.logger.Log4j;
import com.loginapp.logger.MyLogger;
import com.loginapp.model.UserModel;



@Path("/loginapp")
public class LoginAppService {
	private static UserDataWrapper userDataWrapper = UserDataWrapper.getInstance();
	
	@GET
	@Path("/serviceInfo")
	public String serviceVersion() {
		return "dollar version 0.1";
	}
	
	//Inserting a new user into the db
	@POST
	@Path("/insertuser")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String insertNewUser(@Valid UserModel request) {
		return userDataWrapper.insertNewUser(request);
	}	
	
	//Login the existing user into db
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String loginUser(@Valid UserModel request) {
		return userDataWrapper.loginUser(request);
	}	
	
	//Forgot Password tho check thw user is valid user
	@POST
	@Path("/forget")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String forgetPassword(@Valid UserModel request) {
		return userDataWrapper.forgetPassword(request);
	}	
	
	//Resetting the old password with new passowrd
	@POST
	@Path("/reset")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String resetPassword(@Valid UserModel request) {
		return userDataWrapper.resetPassword(request);
	}	
	
	//Changing the password with new password
	@POST
	@Path("/change")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String changePassword(@Valid UserModel request) {
		return userDataWrapper.changePassword(request);
	}
	
	//To view the profile
	@POST
	@Path("/view")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String viewProfile(@Valid UserModel request) {
		return userDataWrapper.viewProfile(request);
	}
	
	//To edit the profile
	@POST
	@Path("/edit")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String editProfile(@Valid UserModel request) {
		return userDataWrapper.editProfile(request);
	}
	
	//To update the user with userId
	@POST
	@Path("/updateUser")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String updateProfile(@Valid UserModel request) {
		return userDataWrapper.updateProfile(request);
	}
	
	//To get all the information from database
	@POST
	@Path("/getAllUserDetails")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllUserDetails(@Valid UserModel request) {
		return userDataWrapper.getAllUserDetails(request);
	}
	
	
	
	
	
	

}
