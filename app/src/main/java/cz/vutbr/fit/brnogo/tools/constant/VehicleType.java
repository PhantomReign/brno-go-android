package cz.vutbr.fit.brnogo.tools.constant;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({VehicleType.TYPE_BUS,
		VehicleType.TYPE_TRAM,
		VehicleType.TYPE_TROLLEY})
@Retention(RetentionPolicy.SOURCE)
public @interface VehicleType {
	int TYPE_BUS = 0;
	int TYPE_TRAM = 1;
	int TYPE_TROLLEY = 2;
}
