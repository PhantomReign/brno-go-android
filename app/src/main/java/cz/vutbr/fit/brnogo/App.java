package cz.vutbr.fit.brnogo;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.jakewharton.threetenabp.AndroidThreeTen;

import cz.vutbr.fit.brnogo.injection.AppComponent;
import cz.vutbr.fit.brnogo.injection.DaggerAppComponent;
import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;
import timber.log.Timber;


public class App extends DaggerApplication {

	@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);
		MultiDex.install(this);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		AndroidThreeTen.init(this);

		if (BuildConfig.DEBUG) {
			Timber.plant(new Timber.DebugTree());
		}
	}

	@Override
	protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
		AppComponent appComponent = DaggerAppComponent.builder().application(this).build();
		appComponent.inject(this);
		return appComponent;
	}
}
