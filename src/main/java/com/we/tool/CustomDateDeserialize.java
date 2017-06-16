package com.we.tool;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class CustomDateDeserialize extends JsonDeserializer<Long> {

	private SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

	@Override
	public Long deserialize(JsonParser jp, DeserializationContext ctxt){
		// TODO Auto-generated method stub
		Date date = null;
		long timestamp = 0L;
		try {
			String dateString = jp.getText();
			date = sdf.parse(dateString);
			timestamp = date.getTime();
		} catch (ParseException e) {
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
		}
		return timestamp;
	}

}
