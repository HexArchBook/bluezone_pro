package io.github.hexarchbook.bluezone.lib.javautils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class DateTimeUtils {

	public static final String YYYYMMDD_HHMM_FORMAT = "yyyy/MM/dd HH:mm";
	public static final String MMYYYY = "MM/yyyy";
	public static final String YYYYMMDDHHMM_FORMAT = "yyyyMMddHHmm";

	private DateTimeUtils() { }

	public static LocalDateTime parseDateTime ( String formattedDateTime, String format ) {
		return LocalDateTime.parse(formattedDateTime, DateTimeFormatter.ofPattern(format));
	}

	public static String formatDateTime ( LocalDateTime dateTime, String format ) {
		return dateTime.format(DateTimeFormatter.ofPattern(format));
	}

}
