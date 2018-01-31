package cz.vutbr.fit.brnogo.injection;

import cz.vutbr.fit.brnogo.injection.annotation.scope.PerScreen;
import cz.vutbr.fit.brnogo.ui.main.MainActivity;
import cz.vutbr.fit.brnogo.ui.main.MainActivityModule;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilderModule {

	@PerScreen
	@ContributesAndroidInjector(modules = MainActivityModule.class)
	abstract MainActivity bindMainActivity();

}
