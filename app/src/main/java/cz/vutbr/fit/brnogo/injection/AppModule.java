package cz.vutbr.fit.brnogo.injection;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import cz.vutbr.fit.brnogo.injection.annotation.qualifier.ApplicationContext;
import cz.vutbr.fit.brnogo.injection.annotation.qualifier.BasicGson;
import dagger.Module;
import dagger.Provides;
import pl.charmas.android.reactivelocation2.ReactiveLocationProvider;

/**
 * Module class for dagger providing objects.
 */

@Module
public class AppModule {

	@Provides
	@ApplicationContext
	Context applicationContext(Application application) {
		return application;
	}

	@Provides
	@Singleton
	@BasicGson
	public Gson basicGson() {
		return new GsonBuilder().create();
	}

	@Provides
	@Singleton
	public Gson gson(@BasicGson Gson gson) { // add here type adapters
		return new GsonBuilder().create();
	}

	@Provides
	@Singleton
	public ReactiveLocationProvider locationProvider(@ApplicationContext Context context) {
		return new ReactiveLocationProvider(context);
	}
}
