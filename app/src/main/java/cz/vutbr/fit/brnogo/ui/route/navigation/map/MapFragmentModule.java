package cz.vutbr.fit.brnogo.ui.route.navigation.map;

import cz.vutbr.fit.brnogo.data.model.response.Route;
import cz.vutbr.fit.brnogo.injection.annotation.scope.PerScreen;
import cz.vutbr.fit.brnogo.tools.constant.Constant;
import cz.vutbr.fit.brnogo.ui.route.navigation.RouteNavigationActivity;
import dagger.Module;
import dagger.Provides;

@Module
public class MapFragmentModule {
	@Provides
	@PerScreen
	MapView mapView(MapFragment fragment) {
		return fragment;
	}

	@Provides
	public Route routeObject(MapFragment fragment) {
		return ((RouteNavigationActivity) fragment.getActivity()).getIntent().getParcelableExtra(Constant.Bundle.KEY_ROUTE_OBJ);
		//return activity.getIntent().getParcelableExtra(Constant.Bundle.KEY_ROUTE_OBJ);
	}
}
