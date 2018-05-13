package cz.vutbr.fit.brnogo.tools.datetime;

import org.threeten.bp.Instant;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.Calendar;
import java.util.StringTokenizer;

import cz.vutbr.fit.brnogo.tools.constant.Constant;

public class DateTimeConverter {

	private DateTimeConverter() {
	}

	public static LocalDateTime localDate(String dateString) {
		DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
		return LocalDateTime.parse(dateString, formatter);
	}

	public static long zonedDateTimeToEpochSec(String date, String time) {
		Instant now = Instant.now();
		ZoneId zoneId = ZoneId.of("Europe/Prague");
		ZonedDateTime dateAndTimeInBrno = ZonedDateTime.ofInstant(now, zoneId);

		if (date.equals(Constant.SearchRequest.DEFAULT_DATE)) {
			date = DateTimeFormatter.ofPattern(Constant.Formatter.DAY_MONTH_YEAR_STRING).format(dateAndTimeInBrno);
		}

		if (time.equals(Constant.SearchRequest.DEFAULT_TIME)) {
			time = DateTimeFormatter.ofPattern(Constant.Formatter.HOUR_MINUTE_STRING).format(dateAndTimeInBrno);
		}

		StringTokenizer parsedDate = new StringTokenizer(date.trim(), ".");
		StringTokenizer parsedTime = new StringTokenizer(time.trim(), ":");

		int day = Integer.parseInt(parsedDate.nextToken());
		int month = Integer.parseInt(parsedDate.nextToken());
		int year = Integer.parseInt(parsedDate.nextToken());
		int hour = Integer.parseInt(parsedTime.nextToken());
		int minute = Integer.parseInt(parsedTime.nextToken());

		return ZonedDateTime.of(year, month, day, hour, minute, 0, 0, zoneId).toEpochSecond();
	}

	public static long currentZonedDateTimeToEpochSec() {
		Instant now = Instant.now();
		ZoneId zoneId = ZoneId.of("Europe/Prague");
		ZonedDateTime dateAndTimeInBrno = ZonedDateTime.ofInstant(now, zoneId);

		return dateAndTimeInBrno.toEpochSecond();
	}

	public static long zonedTimeToDayStartEpochSec() {
		Instant now = Instant.now();
		ZoneId zoneId = ZoneId.of("Europe/Prague");
		ZonedDateTime dateAndTimeInBrno = ZonedDateTime.ofInstant(now, zoneId);

		return dateAndTimeInBrno.toLocalDate().atStartOfDay(zoneId).toEpochSecond();
	}

	public static String epochSecToZonedDayTime(long seconds) {
		Instant dateTime = Instant.ofEpochSecond(seconds);
		ZoneId zoneId = ZoneId.of("Europe/Prague");
		ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(dateTime, zoneId);

		return Constant.Formatter.DAY_MONTH_YEAR_HOUR_MINUTE.format(zonedDateTime);
	}

	public static String epochSecToZonedHourMinute(long seconds) {
		Instant dateTime = Instant.ofEpochSecond(seconds);
		ZoneId zoneId = ZoneId.of("Europe/Prague");
		ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(dateTime, zoneId);

		return Constant.Formatter.HOUR_MINUTE.format(zonedDateTime);
	}

	public static Calendar getCalendar(int year, int month, int day, int hour, int minute) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day, hour, minute);
		return calendar;
	}

	public static Calendar getCalendar() {
		return Calendar.getInstance();
	}
}
