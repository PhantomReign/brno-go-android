package cz.vutbr.fit.brnogo.tools.constant;

import org.threeten.bp.format.DateTimeFormatter;

public interface Constant {

	interface Bundle {
		String KEY_STOP_OBJ = "stopObject";
		String KEY_STOP_TO_DEP_OBJ = "stopToDepObject";
		String KEY_SEARCH_OBJ = "searchObject";
		String KEY_ROUTE_OBJ = "routeObject";
		String KEY_DIRECTIONS_SEARCH_OBJ = "searchObject";
		String KEY_DIRECTIONS_ROUTE_OBJ = "routeObject";
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

	interface Persistence {
		String FAVORITE_ROUTE_KEYS = "favorites_route_keys";
		String SAVED_ROUTE_KEYS = "saved_route_keys";
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
		String DAY_MONTH_YEAR_STRING = "dd.MM.yyyy";
		String HOUR_MINUTE_STRING = "HH:mm";

		DateTimeFormatter DAY_MONTH_YEAR = DateTimeFormatter.ofPattern(DAY_MONTH_YEAR_STRING);
		DateTimeFormatter HOUR_MINUTE = DateTimeFormatter.ofPattern(HOUR_MINUTE_STRING);

		DateTimeFormatter DAY_MONTH_YEAR_HOUR_MINUTE = DateTimeFormatter.ofPattern(DAY_MONTH_YEAR_STRING + " - " + HOUR_MINUTE_STRING);

	}

	interface ViewType {
		int DEPARTURE_LIST_HEADER = 1;
		int DEPARTURE_LIST_VEHICLE = 2;

		int ROUTE_LIST_ITEM = 1;
		int ROUTE_LIST_FOOTER = 2;

		int ROUTE_DETAIL_LIST_ITEM = 1;
		int ROUTE_DETAIL_LIST_PATH = 2;

		int DIRECTION_LIST_ROUTE = 1;
		int DIRECTION_LIST_ITEM = 2;
	}

	interface Preference {
		String DATA_INITIALIZED = "settings_data_initialized";
		String FIRST_STOP_SYNC_DONE = "first_stop_sync_done";
	}

	interface SyncStatus {
		int SYNC = 0;
		int DONE = 1;
		int ERROR = 2;
	}
}
