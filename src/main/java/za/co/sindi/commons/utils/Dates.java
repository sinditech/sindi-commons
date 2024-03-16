/**
 * 
 */
package za.co.sindi.commons.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

/**
 * @author Buhake Sindi
 * @since 13 September 2023
 */
public final class Dates {

	private Dates() {
		throw new AssertionError("Private Constructor.");
	}
	
	public static Date toDate(final Instant instant) {
		return Date.from(instant);
	}
	
	public static Date toDate(final LocalDate date) {
		return toDate(date, ZoneId.systemDefault());
	}
	
	public static Date toDate(final LocalDate date, final ZoneId zoneId) {
		return toDate(date.atStartOfDay()
						   .atZone(zoneId)
						   .toInstant());
	}
	
	public static Date toDate(final LocalDateTime dateTime) {
		return toDate(dateTime, ZoneId.systemDefault());
	}
	
	public static Date toDate(final LocalDateTime dateTime, final ZoneId zoneId) {
		return toDate(dateTime.atZone(zoneId).toInstant());
	}
	
	public static Date toDate(final OffsetDateTime dateTime) {
		return toDate(dateTime, ZoneOffset.UTC);
	}
	
	public static Date toDate(final OffsetDateTime dateTime, final ZoneOffset offset) {
		return toDate(dateTime.withOffsetSameInstant(offset).toInstant());
	}
	
	public static Date toDate(final String date, final String pattern) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		sdf.setLenient(false);
		return sdf.parse(date);
	}
	
	public static LocalDate toLocalDate(final Date date) {
		return toLocalDate(date, ZoneId.systemDefault());
	}
	
	public static LocalDate toLocalDate(final Date date, final ZoneId zoneId) {
		return date.toInstant()
			      .atZone(zoneId)
			      .toLocalDate();
	}
	
	public static LocalDateTime toLocalDateTime(final Date date) {
		return toLocalDateTime(date, ZoneId.systemDefault());
	}
	
	public static LocalDateTime toLocalDateTime(final Date date, final ZoneId zoneId) {
		return date.toInstant()
			      .atZone(zoneId)
			      .toLocalDateTime();
	}
	
	public static OffsetDateTime toOffsetDateTime(final Date date) {
		return toOffsetDateTime(date, ZoneOffset.UTC);
	}
	
	public static OffsetDateTime toOffsetDateTime(final Date date, final ZoneOffset offset) {
		return date.toInstant()
			      .atOffset(offset);
	}
	
	public static String toString(final Date date, final String pattern) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		sdf.setLenient(false);
		return sdf.format(date);
	}
	
	public static String toString(final TemporalAccessor temporal, final String pattern) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return formatter.format(temporal);
	}
	
	public static String toString(final LocalDate localDate, final String pattern) {
		return toString((Temporal)localDate, pattern);
	}
	
	public static String toString(final LocalDateTime localDateTime, final String pattern) {
		return toString((Temporal)localDateTime, pattern);
	}
	
	public static String toString(final LocalTime localTime, final String pattern) {
		return toString((Temporal)localTime, pattern);
	}
	
	public static String toString(final OffsetDateTime offsetDateTime, final String pattern) {
		return toString((Temporal)offsetDateTime, pattern);
	}
}
