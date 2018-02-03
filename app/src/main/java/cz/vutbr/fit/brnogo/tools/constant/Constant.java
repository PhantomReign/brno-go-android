package cz.vutbr.fit.brnogo.tools.constant;

import org.threeten.bp.format.DateTimeFormatter;

public interface Constant {

	interface Bundle {
		String KEY_STOP_OBJ = "stopObject";
	}

	interface ErrorCode {
		int UNKNOWN_ERROR = 500;
	}

	interface RequestCode {
		int STOP_FROM = 201;
		int STOP_TO = 202;
	}

	interface Formatter {
		DateTimeFormatter DAY_MONTH_YEAR = DateTimeFormatter.ofPattern("d. M. yyyy");
		DateTimeFormatter DAY_LONG_MONTH_YEAR = DateTimeFormatter.ofPattern("d. MMMM yyyy");
		DateTimeFormatter DAY_MONTH = DateTimeFormatter.ofPattern("d. M.");
		DateTimeFormatter HOUR_MINUTE = DateTimeFormatter.ofPattern("HH:mm");
		DateTimeFormatter HOUR_MINUTE_SECONDS = DateTimeFormatter.ofPattern("HH:mm:ss");
		DateTimeFormatter MONTH_YEAR = DateTimeFormatter.ofPattern("MMM. yyyy");
		DateTimeFormatter YEAR = DateTimeFormatter.ofPattern("yyyy");
	}

}
