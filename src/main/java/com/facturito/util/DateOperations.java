package com.facturito.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateOperations {

	public static Date getDateFormat(String formatPattern, String date) {
		SimpleDateFormat formatter = new SimpleDateFormat(formatPattern);
		try {
			return formatter.parse(date);
		} catch (ParseException e) {
			return new Date();
		}
	}

	public Date asiganarHora(Date fecha, String value) {
		switch (value) {
		case "inicio": {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(fecha);
			calendar.set(Calendar.HOUR_OF_DAY, 0); // Hora 00
			calendar.set(Calendar.MINUTE, 0); // Minuto 00
			calendar.set(Calendar.SECOND, 0); // Segundo 00
			calendar.set(Calendar.MILLISECOND, 0); // Milisegundo 000
			return calendar.getTime();
		}
		case "fin": {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(fecha);
			calendar.set(Calendar.HOUR_OF_DAY, 23); // Hora 00
			calendar.set(Calendar.MINUTE, 59); // Minuto 00
			calendar.set(Calendar.SECOND, 59); // Segundo 00
			calendar.set(Calendar.MILLISECOND, 0); // Milisegundo 000
			return calendar.getTime();
		}
		default:
			return fecha;
		}
	}
}
