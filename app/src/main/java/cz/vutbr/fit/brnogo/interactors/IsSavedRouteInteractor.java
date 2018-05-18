package cz.vutbr.fit.brnogo.interactors;

import android.content.Context;

import java.util.ArrayList;

import javax.inject.Inject;

import cz.vutbr.fit.brnogo.data.model.response.Route;
import cz.vutbr.fit.brnogo.data.persistance.Persistence;
import cz.vutbr.fit.brnogo.injection.annotation.qualifier.ApplicationContext;
import cz.vutbr.fit.brnogo.injection.annotation.scope.PerScreen;
import cz.vutbr.fit.brnogo.interactors.base.BaseSingleInteractor;
import cz.vutbr.fit.brnogo.tools.constant.Constant;
import io.reactivex.Single;

/**
 * Class used to return saved route status.
 */

@PerScreen
public class IsSavedRouteInteractor extends BaseSingleInteractor<Boolean> {

	@Inject Persistence persistence;
	@Inject @ApplicationContext Context context;

	private Route route;

	@Inject
	public IsSavedRouteInteractor() {
	}

	public IsSavedRouteInteractor init(Route route) {
		this.route = route;
		return this;
	}

	@Override
	protected Single<Boolean> buildSingle() {
		return Single.fromCallable(() -> {
			if (route == null) {
				return false;
			}

			ArrayList<String> list = persistence.get(Constant.Persistence.SAVED_ROUTE_KEYS, new ArrayList<String>());
			if (list.contains(route.getId())) {
				route.setSaved(true);
				return true;
			} else {
				route.setSaved(false);
				return false;
			}
		});
	}
}
