package cz.vutbr.fit.brnogo.tools.constant;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({UserActionType.TYPE_WALK,
		UserActionType.TYPE_RIDE,
		UserActionType.TYPE_WAIT,
		UserActionType.TYPE_WAIT_FOR_NEW_ROUTE,
		UserActionType.TYPE_WAIT_FOR_VEHICLE_TO_LEAVE,
		UserActionType.TYPE_BOARD,
		UserActionType.TYPE_EXIT})
@Retention(RetentionPolicy.SOURCE)
public @interface UserActionType {
	int TYPE_WALK = 0;
	int TYPE_RIDE = 1;
	int TYPE_WAIT = 2;
	int TYPE_WAIT_FOR_NEW_ROUTE = 3;
	int TYPE_WAIT_FOR_VEHICLE_TO_LEAVE = 4;
	int TYPE_BOARD = 5;
	int TYPE_EXIT = 6;
}
