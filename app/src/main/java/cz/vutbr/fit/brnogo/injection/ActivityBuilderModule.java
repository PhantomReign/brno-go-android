package cz.vutbr.fit.brnogo.injection;

import cz.vutbr.fit.brnogo.injection.annotation.scope.PerScreen;
import cz.vutbr.fit.brnogo.ui.departures.DeparturesActivity;
import cz.vutbr.fit.brnogo.ui.departures.DeparturesActivityModule;
import cz.vutbr.fit.brnogo.ui.main.MainActivity;
import cz.vutbr.fit.brnogo.ui.main.MainActivityModule;
import cz.vutbr.fit.brnogo.ui.stop.StopSearchActivity;
import cz.vutbr.fit.brnogo.ui.stop.StopSearchActivityModule;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilderModule {

	@PerScreen
	@ContributesAndroidInjector(modules = MainActivityModule.class)
	abstract MainActivity bindMainActivity();

	@PerScreen
	@ContributesAndroidInjector(modules = StopSearchActivityModule.class)
	abstract StopSearchActivity bindSearchActivity();

	@PerScreen
	@ContributesAndroidInjector(modules = DeparturesActivityModule.class)
	abstract DeparturesActivity bindDeparturesActivity();

}
