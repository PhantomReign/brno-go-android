package cz.vutbr.fit.brnogo.interactors;

import android.content.Context;

import java.util.ArrayList;

import javax.inject.Inject;

import cz.vutbr.fit.brnogo.data.model.response.Route;
import cz.vutbr.fit.brnogo.data.model.response.Vehicle;
import cz.vutbr.fit.brnogo.data.store.RouteStore;
import cz.vutbr.fit.brnogo.injection.annotation.qualifier.ApplicationContext;
import cz.vutbr.fit.brnogo.injection.annotation.scope.PerScreen;
import cz.vutbr.fit.brnogo.interactors.base.BaseSingleInteractor;
import io.reactivex.Single;

@PerScreen
public class GetNewRouteInteractor extends BaseSingleInteractor<Route> {

	private int startStationId;
	private int destinationStationId;
	private long dateTime;
	private int minTimeToMove;
	private int maxTransfers;

	@Inject RouteStore routeStore;
	@Inject @ApplicationContext Context context;

	@Inject
	public GetNewRouteInteractor() {
	}

	public GetNewRouteInteractor init(int startStationId, int destinationStationId,
									long dateTime, int minTimeToMove, int maxTransfers) {
		this.startStationId = startStationId;
		this.destinationStationId = destinationStationId;
		this.dateTime = dateTime;
		this.minTimeToMove = minTimeToMove;
		this.maxTransfers = maxTransfers;
		return this;
	}

	@Override
	protected Single<Route> buildSingle() {

		return routeStore
				.getRoutes(startStationId, destinationStationId, dateTime, minTimeToMove, maxTransfers, true, 1)
				.map(routes -> {
					if (!routes.isEmpty()) {
						return routes.get(0);
					}

					ArrayList<Vehicle> vehicles = new ArrayList<>();
					return new Route("", -1 , -1, "", "", -1, -1, vehicles, false);
				});
	}
}
