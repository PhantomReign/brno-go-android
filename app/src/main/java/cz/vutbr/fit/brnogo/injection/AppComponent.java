package cz.vutbr.fit.brnogo.injection;

import android.app.Application;

import javax.inject.Singleton;

import cz.vutbr.fit.brnogo.App;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {AndroidInjectionModule.class, AppModule.class, UrlModule.class, NetModule.class, ActivityBuilderModule.class, FragmentBuilderModule.class})
public interface AppComponent extends AndroidInjector<App> {

	@Component.Builder
	interface Builder {

		@BindsInstance
		Builder application(Application application);

		AppComponent build();
	}

	@Override
	void inject(App app);
}
