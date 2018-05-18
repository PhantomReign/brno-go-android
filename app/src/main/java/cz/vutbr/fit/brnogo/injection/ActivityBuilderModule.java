package cz.vutbr.fit.brnogo.injection;

import cz.vutbr.fit.brnogo.injection.annotation.scope.PerScreen;
import cz.vutbr.fit.brnogo.ui.departures.DeparturesActivity;
import cz.vutbr.fit.brnogo.ui.departures.DeparturesActivityModule;
import cz.vutbr.fit.brnogo.ui.directions.DirectionsActivity;
import cz.vutbr.fit.brnogo.ui.directions.DirectionsActivityModule;
import cz.vutbr.fit.brnogo.ui.main.MainActivity;
import cz.vutbr.fit.brnogo.ui.main.MainActivityModule;
import cz.vutbr.fit.brnogo.ui.route.RoutesActivity;
import cz.vutbr.fit.brnogo.ui.route.RoutesActivityModule;
import cz.vutbr.fit.brnogo.ui.route.detail.RouteDetailActivity;
import cz.vutbr.fit.brnogo.ui.route.detail.RouteDetailActivityModule;
import cz.vutbr.fit.brnogo.ui.route.navigation.RouteNavigationActivity;
import cz.vutbr.fit.brnogo.ui.route.navigation.RouteNavigationActivityModule;
import cz.vutbr.fit.brnogo.ui.settings.SettingsActivity;
import cz.vutbr.fit.brnogo.ui.settings.SettingsActivityModule;
import cz.vutbr.fit.brnogo.ui.stop.StopSearchActivity;
import cz.vutbr.fit.brnogo.ui.stop.StopSearchActivityModule;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Class containing all activities for Dagger.
 */

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

	@PerScreen
	@ContributesAndroidInjector(modules = DirectionsActivityModule.class)
	abstract DirectionsActivity bindDirectionsActivity();

	@PerScreen
	@ContributesAndroidInjector(modules = RoutesActivityModule.class)
	abstract RoutesActivity bindRoutesActivity();

	@PerScreen
	@ContributesAndroidInjector(modules = RouteDetailActivityModule.class)
	abstract RouteDetailActivity bindRouteDetailActivity();

	@PerScreen
	@ContributesAndroidInjector(modules = RouteNavigationActivityModule.class)
	abstract RouteNavigationActivity bindRouteNavigationActivity();

	@PerScreen
	@ContributesAndroidInjector(modules = SettingsActivityModule.class)
	abstract SettingsActivity bindSettingsActivity();

}
