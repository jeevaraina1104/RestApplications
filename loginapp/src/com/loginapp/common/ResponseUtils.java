package com.loginapp.common;

import com.wipro.c4.core.utils.common.constants.Constants;
import com.wipro.c4.core.utils.outputResponse.OutputResponse;

public class ResponseUtils {
	static OutputResponse response = new OutputResponse();

	public static OutputResponse setSuccess(String message) {
		response.setOutputCode(Constants.OUTPUT_CODE_SUCCESS);
		response.setOutputStatus(Constants.OUTPUT_STATUS_SUCCESS);
		response.setMessage(message);
		response.setResponse();
		return response;
	}

	public static OutputResponse setFailure(String message) {
		response.setOutputCode(Constants.OUTPUT_CODE_FAILURE);
		response.setOutputStatus(Constants.OUTPUT_STATUS_FAILURE);
		response.setMessage(message);
		response.setResponse();
		return response;
	}

	public static OutputResponse setSuccessWithData(String message, String data) {
		response.setOutputCode(Constants.OUTPUT_CODE_SUCCESS);
		response.setOutputStatus(Constants.OUTPUT_STATUS_SUCCESS);
		response.setMessage(message);
		response.setResponse(data);
		return response;
	}
	
	

}
