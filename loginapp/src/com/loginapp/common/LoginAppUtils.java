package com.loginapp.common;

import org.json.JSONObject;

public class LoginAppUtils {
	public static JSONObject getResponse(String status, String messgae, Object data) {
		return new JSONObject().put("status", status).put("message", messgae).put("data", data);
	}
	

}
