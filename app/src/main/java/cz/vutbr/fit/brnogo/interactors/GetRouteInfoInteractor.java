package cz.vutbr.fit.brnogo.interactors;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cz.vutbr.fit.brnogo.data.model.recyclerview.RouteItem;
import cz.vutbr.fit.brnogo.data.model.response.Route;
import cz.vutbr.fit.brnogo.data.model.response.Vehicle;
import cz.vutbr.fit.brnogo.injection.annotation.qualifier.ApplicationContext;
import cz.vutbr.fit.brnogo.injection.annotation.scope.PerScreen;
import cz.vutbr.fit.brnogo.interactors.base.BaseSingleInteractor;
import io.reactivex.Single;

@PerScreen
public class GetRouteInfoInteractor extends BaseSingleInteractor<List<RouteItem>> {

	@Inject Route route;
	@Inject @ApplicationContext Context context;

	@Inject
	public GetRouteInfoInteractor() {
	}

	@Override
	protected Single<List<RouteItem>> buildSingle() {
		return Single.fromCallable(() -> {
			List<RouteItem> sorted = new ArrayList<>();

			for (Vehicle vehicle: route.getVehicles()) {
				sorted.add(vehicle);
				sorted.addAll(vehicle.getPath());
			}
			return sorted;
		});
	}
}
