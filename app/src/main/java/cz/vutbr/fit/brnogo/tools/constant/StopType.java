package cz.vutbr.fit.brnogo.tools.constant;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@StringDef({StopType.TYPE_TRAM})
@Retention(RetentionPolicy.SOURCE)
public @interface StopType {
	String TYPE_TRAM = "Tram";
}
