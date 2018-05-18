package cz.vutbr.fit.brnogo.ui.stop;

import cz.vutbr.fit.brnogo.injection.annotation.scope.PerScreen;
import dagger.Module;
import dagger.Provides;

@Module
@PerScreen
public class StopSearchActivityModule {
	@Provides
	@PerScreen
	StopSearchView stopSearchView(StopSearchActivity activity) {
		return activity;
	}
}
