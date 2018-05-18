package cz.vutbr.fit.brnogo.ui.route.detail;

import cz.vutbr.fit.brnogo.data.model.response.Route;
import cz.vutbr.fit.brnogo.injection.annotation.scope.PerScreen;
import cz.vutbr.fit.brnogo.tools.constant.Constant;
import dagger.Module;
import dagger.Provides;

@Module
@PerScreen
public class RouteDetailActivityModule {
	@Provides
	@PerScreen
	RouteDetailView routesView(RouteDetailActivity activity) {
		return activity;
	}

	@Provides
	public Route routeObject(RouteDetailActivity activity) {
		return activity.getIntent().getParcelableExtra(Constant.Bundle.KEY_ROUTE_OBJ);
	}
}
