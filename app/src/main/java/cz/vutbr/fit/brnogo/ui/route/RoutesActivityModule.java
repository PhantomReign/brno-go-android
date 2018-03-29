package cz.vutbr.fit.brnogo.ui.route;

import cz.vutbr.fit.brnogo.data.model.store.Search;
import cz.vutbr.fit.brnogo.injection.annotation.scope.PerScreen;
import cz.vutbr.fit.brnogo.tools.constant.Constant;
import dagger.Module;
import dagger.Provides;

@Module
@PerScreen
public class RoutesActivityModule {
	@Provides
	@PerScreen
	RoutesView routesView(RoutesActivity activity) {
		return activity;
	}

	@Provides
	public Search searchObject(RoutesActivity activity) {
		return activity.getIntent().getParcelableExtra(Constant.Bundle.KEY_SEARCH_OBJ);
	}
}
