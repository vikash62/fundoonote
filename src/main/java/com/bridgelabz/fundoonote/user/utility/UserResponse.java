package com.bridgelabz.fundoonote.user.utility;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponse {

	private String responseStatus;
	private int responseCode;
	private Object responseData;

}