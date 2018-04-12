package cz.vutbr.fit.brnogo.tools.constant;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@StringDef({StartScreenType.TYPE_ROUTES,
		StartScreenType.TYPE_DEPARTURES,
		StartScreenType.TYPE_NEARBY})
@Retention(RetentionPolicy.SOURCE)
public @interface StartScreenType {
	String TYPE_ROUTES = "0";
	String TYPE_DEPARTURES = "1";
	String TYPE_NEARBY = "2";
}
