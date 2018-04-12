package cz.vutbr.fit.brnogo.interactors;

import android.support.annotation.Nullable;

import javax.inject.Inject;

import cz.vutbr.fit.brnogo.data.model.response.Route;
import cz.vutbr.fit.brnogo.data.store.SavedRouteStore;
import cz.vutbr.fit.brnogo.injection.annotation.scope.PerScreen;
import cz.vutbr.fit.brnogo.interactors.base.BaseCompletableInteractor;
import io.reactivex.Completable;

@PerScreen
public class SetSavedRouteInteractor extends BaseCompletableInteractor {

	@Inject SavedRouteStore savedRouteStore;

	private Route route;

	@Inject
	public SetSavedRouteInteractor() {
	}

	public SetSavedRouteInteractor init(Route route) {
		this.route = route;

		return this;
	}

	@Override
	protected Completable buildCompletable() {
		return Completable.fromAction(() -> {
			if (!route.getSaved()) {
				savedRouteStore.setRouteAsSaved(route);
			} else {
				savedRouteStore.unsetRouteAsSaved(route);
			}
		});
	}
}
