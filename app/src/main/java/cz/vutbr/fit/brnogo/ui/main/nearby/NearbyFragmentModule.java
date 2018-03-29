package cz.vutbr.fit.brnogo.ui.main.nearby;

import cz.vutbr.fit.brnogo.injection.annotation.scope.PerScreen;
import dagger.Module;
import dagger.Provides;

@PerScreen
@Module
public class NearbyFragmentModule {

	@Provides
	@PerScreen
	NearbyView nearbyView(NearbyFragment fragment) {
		return fragment;
	}

}
