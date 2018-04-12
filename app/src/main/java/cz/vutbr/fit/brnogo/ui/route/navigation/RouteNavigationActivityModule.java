package cz.vutbr.fit.brnogo.ui.route.navigation;

import cz.vutbr.fit.brnogo.data.model.response.Route;
import cz.vutbr.fit.brnogo.injection.annotation.scope.PerScreen;
import cz.vutbr.fit.brnogo.tools.constant.Constant;
import dagger.Module;
import dagger.Provides;

@Module
@PerScreen
public class RouteNavigationActivityModule {
	@Provides
	@PerScreen
	RouteNavigationView routeNavigationView(RouteNavigationActivity activity) {
		return activity;
	}

	@Provides
	public Route routeObject(RouteNavigationActivity activity) {
		return activity.getIntent().getParcelableExtra(Constant.Bundle.KEY_ROUTE_OBJ);
	}
}
