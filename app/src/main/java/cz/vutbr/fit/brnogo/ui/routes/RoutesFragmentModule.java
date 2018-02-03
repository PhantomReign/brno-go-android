package cz.vutbr.fit.brnogo.ui.routes;

import cz.vutbr.fit.brnogo.injection.annotation.scope.PerScreen;
import dagger.Module;
import dagger.Provides;

@PerScreen
@Module
public class RoutesFragmentModule {

	@Provides
	@PerScreen
	RoutesView routesView(RoutesFragment fragment) {
		return fragment;
	}
}
