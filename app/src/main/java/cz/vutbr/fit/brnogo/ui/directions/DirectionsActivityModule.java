package cz.vutbr.fit.brnogo.ui.directions;

import android.support.annotation.Nullable;

import cz.vutbr.fit.brnogo.data.model.response.Route;
import cz.vutbr.fit.brnogo.data.model.store.Search;
import cz.vutbr.fit.brnogo.injection.annotation.scope.PerScreen;
import cz.vutbr.fit.brnogo.tools.constant.Constant;
import dagger.Module;
import dagger.Provides;

@Module
@PerScreen
public class DirectionsActivityModule {
	@Provides
	@PerScreen
	DirectionsView directionsView(DirectionsActivity activity) {
		return activity;
	}

	@Provides
	public Search searchObject(DirectionsActivity activity) {
		return activity.getIntent().getParcelableExtra(Constant.Bundle.KEY_DIRECTIONS_SEARCH_OBJ);
	}

	@Nullable
	@Provides
	public Route routeObject(DirectionsActivity activity) {
		return activity.getIntent().getParcelableExtra(Constant.Bundle.KEY_DIRECTIONS_ROUTE_OBJ);
	}
}
