package cz.vutbr.fit.brnogo.ui.departures;

import cz.vutbr.fit.brnogo.injection.annotation.scope.PerScreen;
import dagger.Module;
import dagger.Provides;

@PerScreen
@Module
public class DeparturesFragmentModule {

	@Provides
	@PerScreen
	DeparturesView departuresView(DeparturesFragment fragment) {
		return fragment;
	}
}
