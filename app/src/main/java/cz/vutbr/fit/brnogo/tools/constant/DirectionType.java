package cz.vutbr.fit.brnogo.tools.constant;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({DirectionType.TYPE_WALK,
		DirectionType.TYPE_BOARD_BUS,
		DirectionType.TYPE_BOARD_TRAM,
		DirectionType.TYPE_BOARD_TROLLEY,
		DirectionType.TYPE_STAY,
		DirectionType.TYPE_EXIT_BUS,
		DirectionType.TYPE_EXIT_TRAM,
		DirectionType.TYPE_EXIT_TROLLEY,
		DirectionType.TYPE_DONE})
@Retention(RetentionPolicy.SOURCE)
public @interface DirectionType {
	int TYPE_WALK = 0;
	int TYPE_BOARD_BUS = 1;
	int TYPE_BOARD_TRAM = 2;
	int TYPE_BOARD_TROLLEY = 3;
	int TYPE_STAY = 4;
	int TYPE_EXIT_BUS = 5;
	int TYPE_EXIT_TRAM = 6;
	int TYPE_EXIT_TROLLEY = 7;
	int TYPE_DONE = 8;
}
