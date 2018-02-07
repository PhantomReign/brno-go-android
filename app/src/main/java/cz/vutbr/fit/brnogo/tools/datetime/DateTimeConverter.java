package cz.vutbr.fit.brnogo.tools.datetime;

import android.support.annotation.Nullable;

import org.threeten.bp.Instant;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZoneOffset;
import org.threeten.bp.ZonedDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.Calendar;
import java.util.Date;

public class DateTimeConverter {

	private DateTimeConverter() {
	}

	@Nullable
	public static LocalDateTime dateTime(Date date) {
		if (date == null) {
			return null;
		}

		Instant instant = Instant.ofEpochMilli(date.getTime());
		return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
	}

	public static LocalDateTime localDatetime(long milliseconds) {
		return LocalDateTime.ofInstant(Instant.ofEpochSecond(milliseconds), ZoneId.systemDefault());
	}

	public static long milliseconds(LocalDateTime localDateTime) {
		ZoneId zoneId = ZoneId.systemDefault();
		return localDateTime.atZone(zoneId).toEpochSecond();
	}

	public static ZonedDateTime getUTCZonedDateTime(LocalDateTime localDateTime) {
		return localDateTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneOffset.UTC);
	}

	public static LocalDateTime localDate(String dateString) {
		DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
		return LocalDateTime.parse(dateString, formatter);
	}

	public static Calendar getCalendar(int year, int month, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day);
		return calendar;
	}

	public static Calendar getCalendar() {
		return Calendar.getInstance();
	}
}
