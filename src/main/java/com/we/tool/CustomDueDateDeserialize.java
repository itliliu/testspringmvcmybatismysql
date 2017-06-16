package com.we.tool;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

/**
 * Deserializer DueDate
 * 
 * @author hongfengma
 */
public class CustomDueDateDeserialize extends JsonDeserializer<String> {
	private SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
	private String[] months = new String[] { "January", "February", "March", "April", "May", "June", "July", "August",
			"September", "October", "November", "December" };
	private final String[] weeks = new String[] { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday",
			"Saturday" };

	@Override
	public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
		String data = p.getText();
		try {
			Date time = format.parse(data);
			Calendar calender = Calendar.getInstance();
			calender.setTime(time);
			int weekNumber = calender.get(Calendar.DAY_OF_WEEK);
			String week = weeks[weekNumber - 1];

			int monthNumber = calender.get(Calendar.MONTH);
			String month = months[monthNumber];

			int dateNumber = calender.get(Calendar.DATE);
			String date = "" + dateNumber;
			int mod = dateNumber % Constant.DIVISOR;
			if (dateNumber >= Constant.MONTH_ELEVEN && dateNumber <= Constant.MONTH_THIRTEEN) {
				date += Constant.DATESUFFIX_TH;
			} else {
				switch (mod) {
				case Constant.MOD_ONE:
					date += Constant.DATESUFFIX_ST;
					break;
				case Constant.MOD_TWO:
					date += Constant.DATESUFFIX_ND;
					break;
				case Constant.MOD_THREE:
					date += Constant.DATESUFFIX_RD;
					break;
				default:
					date += Constant.DATESUFFIX_TH;
					break;
				}
			}
			return week + ", " + month + " " + date;
		} catch (ParseException e) {
			return null;
		}
	}

}
