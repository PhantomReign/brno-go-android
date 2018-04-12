package cz.vutbr.fit.brnogo.tools.constant;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({UserActionType.TYPE_WALK, UserActionType.TYPE_RIDE, UserActionType.TYPE_WAIT})
@Retention(RetentionPolicy.SOURCE)
public @interface UserActionType {
	int TYPE_WALK = 0;
	int TYPE_RIDE = 1;
	int TYPE_WAIT = 2;
}
