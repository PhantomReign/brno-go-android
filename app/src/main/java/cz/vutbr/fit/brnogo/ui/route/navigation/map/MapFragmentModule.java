package cz.vutbr.fit.brnogo.ui.route.navigation.map;

import cz.vutbr.fit.brnogo.data.model.response.Route;
import cz.vutbr.fit.brnogo.data.model.store.Search;
import cz.vutbr.fit.brnogo.injection.annotation.scope.PerScreen;
import cz.vutbr.fit.brnogo.tools.constant.Constant;
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
		return fragment.getActivity().getIntent().getParcelableExtra(Constant.Bundle.KEY_ROUTE_OBJ);
	}

	@Provides
	public Search searchObject(MapFragment fragment) {
		return fragment.getActivity().getIntent().getParcelableExtra(Constant.Bundle.KEY_SEARCH_OBJ);
	}
}
