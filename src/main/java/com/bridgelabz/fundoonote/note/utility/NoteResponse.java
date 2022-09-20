package com.bridgelabz.fundoonote.note.utility;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NoteResponse {
	private String responseStatus;
	private int responseCode;
	private Object responseData;
}