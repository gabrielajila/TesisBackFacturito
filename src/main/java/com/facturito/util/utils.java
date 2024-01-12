package com.facturito.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;


public class utils {

	public static Instant obtenerFechaString(String fechaInicio) {
		 DateTimeFormatter f = new DateTimeFormatterBuilder()
                 .appendPattern("yyyy-MM-dd")
                 .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                 .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                 .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                 .parseDefaulting(ChronoField.MILLI_OF_SECOND, 0)
                 .toFormatter();						// in translating that input string.
		LocalDateTime ldt = LocalDateTime.parse(fechaInicio, f);
		ZoneId z = ZoneId.of("America/Guayaquil");
		ZonedDateTime zdt = ldt.atZone(z);
		Instant instant = zdt.toInstant();
		return instant;
	}
}
