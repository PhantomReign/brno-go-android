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

	interface SearchRequest {
		String DEFAULT_DATE = "Today";
		String DEFAULT_TIME = "Now";
		int DEFAULT_TRANSFERS = 999;
		int DEFAULT_TRANSFER_TIME = 999;
	}

	interface Formatter {
		DateTimeFormatter DAY_MONTH_YEAR = DateTimeFormatter.ofPattern("dd.MM.yyyy");
		DateTimeFormatter DAY_LONG_MONTH_YEAR = DateTimeFormatter.ofPattern("d. MMMM yyyy");
		DateTimeFormatter HOUR_MINUTE = DateTimeFormatter.ofPattern("HH:mm");
	}

}
