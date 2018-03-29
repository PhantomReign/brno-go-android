package cz.vutbr.fit.brnogo.ui.departures;

import cz.vutbr.fit.brnogo.data.model.response.CurrentDeparture;
import cz.vutbr.fit.brnogo.data.model.response.Stop;
import cz.vutbr.fit.brnogo.injection.annotation.scope.PerScreen;
import cz.vutbr.fit.brnogo.tools.constant.Constant;
import dagger.Module;
import dagger.Provides;

@Module
@PerScreen
public class DeparturesActivityModule {
	@Provides
	@PerScreen
	DeparturesView stopSearchView(DeparturesActivity activity) {
		return activity;
	}

	@Provides
	public Stop stopObject(DeparturesActivity activity) {
		return activity.getIntent().getParcelableExtra(Constant.Bundle.KEY_STOP_TO_DEP_OBJ);
	}
}
