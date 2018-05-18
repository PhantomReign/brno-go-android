package cz.vutbr.fit.brnogo.tools.constant;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({QuantityType.TYPE_TIME, QuantityType.TYPE_DISTANCE, QuantityType.TYPE_NONE})
@Retention(RetentionPolicy.SOURCE)
public @interface QuantityType {
	int TYPE_TIME = 0;
	int TYPE_DISTANCE = 1;
	int TYPE_NONE = 2;
}
