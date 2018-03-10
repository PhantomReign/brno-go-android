package cz.vutbr.fit.brnogo.tools.constant;

import org.threeten.bp.format.DateTimeFormatter;

public interface Constant {

	interface Bundle {
		String KEY_STOP_OBJ = "stopObject";
		String KEY_STOP_TO_DEP_OBJ = "stopToDepObject";
	}

	interface ErrorCode {
		int UNKNOWN_ERROR = 500;
	}

	interface RequestCode {
		int STOP_FROM = 201;
		int STOP_TO = 202;

		int DIALOG_TRANSFERS = 261;
		int DIALOG_TRANSFER_TIME = 262;
	}

	interface Tag {
		String DIALOG_TRANSFERS = "transfers";
		String DIALOG_TRANSFER_TIME = "transfersTime";

	}

	interface SearchRequest {
		String DEFAULT_DATE = "Today";
		String DEFAULT_TIME = "Now";
		int DEFAULT_TRANSFERS = 999;
		int DEFAULT_TRANSFER_TIME = 999;
	}

	interface TransfersDialog {
		int MAX = 20;
		int MIN = 0;
		int DEFAULT = 20;
	}

	interface TransferTimeDialog {
		int MAX = 30;
		int MIN = 0;
		int DEFAULT = 5;
	}

	interface Formatter {
		DateTimeFormatter DAY_MONTH_YEAR = DateTimeFormatter.ofPattern("dd.MM.yyyy");
		DateTimeFormatter DAY_LONG_MONTH_YEAR = DateTimeFormatter.ofPattern("d. MMMM yyyy");
		DateTimeFormatter HOUR_MINUTE = DateTimeFormatter.ofPattern("HH:mm");
	}

	interface ViewType {
		int DEPARTURE_LIST_HEADER = 1;
		int DEPARTURE_LIST_VEHICLE = 2;
	}

}
