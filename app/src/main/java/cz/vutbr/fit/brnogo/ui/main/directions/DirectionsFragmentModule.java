package cz.vutbr.fit.brnogo.ui.main.directions;

import cz.vutbr.fit.brnogo.injection.annotation.scope.PerScreen;
import dagger.Module;
import dagger.Provides;

@PerScreen
@Module
public class DirectionsFragmentModule {

	@Provides
	@PerScreen
	DirectionsView directionsView(DirectionsFragment fragment) {
		return fragment;
	}

}
