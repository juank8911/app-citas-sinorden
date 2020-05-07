package com.colsubsidio.health.appointments.withoutorder.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.springframework.stereotype.Component;

@Component
public class DateUtils {

	public Date getDate() {
		return new Date();
	}

	public String getDateString(String formatReturn) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(formatReturn);
			return sdf.format(getDate());
		} catch (Exception e) {
			StringBuilder error = new StringBuilder();
			error.append(DateUtils.class.getName());
			error.append(";");
			error.append("formatDate");
			error.append(";");
			error.append(e.getMessage());
			return "";
		}
	}

	/* "yyyy-MM-dd":"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'" */
	public String formatDate(String format, String dateTxt, String formatReturn) {
		try {
			Date date = new SimpleDateFormat(format, Locale.ENGLISH).parse(dateTxt);
			SimpleDateFormat sdf = new SimpleDateFormat(formatReturn);
			return sdf.format(date);
		} catch (Exception e) {
			StringBuilder error = new StringBuilder();
			error.append(DateUtils.class.getName());
			error.append(";");
			error.append("formatDate");
			error.append(";");
			error.append(e.getMessage());
			return "";
		}
	}

	public Date addSubtractMonthsDate(int months) {
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(getDate());
			calendar.add(Calendar.MONTH, -months);
			// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
			// return sdf.format(calendar.getTime());
			return calendar.getTime();
		} catch (Exception e) {
			StringBuilder error = new StringBuilder();
			error.append(DateUtils.class.getName());
			error.append(";");
			error.append("addSubtractMonthsDate");
			error.append(";");
			error.append(e.getMessage());
			return null;
		}
	}

	public Timestamp getDateTimeTimeStamp() {
		TimeZone timeZone = TimeZone.getTimeZone("America/Bogota");
		Calendar calendar = Calendar.getInstance(timeZone);
		long date = calendar.getTime().getTime();
		Timestamp timeStamp = new Timestamp(date);
		return timeStamp;
	}

}
