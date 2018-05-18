package cz.vutbr.fit.brnogo.interactors;

import java.util.List;

import javax.inject.Inject;

import cz.vutbr.fit.brnogo.data.model.response.Route;
import cz.vutbr.fit.brnogo.data.store.SavedRouteStore;
import cz.vutbr.fit.brnogo.injection.annotation.scope.PerScreen;
import cz.vutbr.fit.brnogo.interactors.base.BaseFlowableInteractor;
import io.reactivex.Flowable;

/**
 * Class used to return saved routes.
 */

@PerScreen
public class GetSavedRouteInteractor extends BaseFlowableInteractor<List<Route>> {

	@Inject SavedRouteStore savedRouteStore;

	@Inject
	public GetSavedRouteInteractor() {
	}

	@Override
	protected Flowable<List<Route>> buildFlowable() {
		return savedRouteStore.getRoutes().onBackpressureLatest()
				.publish()
				.autoConnect();
	}
}
