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
import cz.vutbr.fit.brnogo.tools.constant.Constant;
import io.reactivex.Single;

@PerScreen
public class GetFasterRouteInteractor extends BaseSingleInteractor<Route> {

	private int startStationId;
	private int destinationStationId;
	private long dateTime;
	private int minTimeToMove;
	private int maxTransfers;

	private long endTime;

	@Inject RouteStore routeStore;
	@Inject @ApplicationContext Context context;

	@Inject
	public GetFasterRouteInteractor() {
	}

	public GetFasterRouteInteractor init(int startStationId, int destinationStationId,
										 long dateTime, int minTimeToMove, int maxTransfers,
										 long endTime) {
		this.startStationId = startStationId;
		this.destinationStationId = destinationStationId;
		this.dateTime = dateTime;
		this.minTimeToMove = minTimeToMove;
		this.maxTransfers = maxTransfers;
		this.endTime = endTime;
		return this;
	}

	@Override
	protected Single<Route> buildSingle() {
		return routeStore
				.getRoutes(startStationId, destinationStationId, dateTime, minTimeToMove, maxTransfers, true, 1)
				.map(routes -> {
					if (routes.isEmpty()) {
						ArrayList<Vehicle> vehicles = new ArrayList<>();
						return new Route("", -1 , -1, "", "", -1, -1, vehicles, false);
					}

					Route route = routes.get(0);
					int lastVehicleIndex = route.getVehicles().size() - 1;
					int lastNodeIndex = route.getVehicles().get(lastVehicleIndex).getPath().size() - 1;
					long newRouteEndTime = route.getVehicles().get(lastVehicleIndex).getPath().get(lastNodeIndex).getTimeOfArrival();

					if (newRouteEndTime < endTime - Constant.Navigation.FASTER_ROUTE_TIME_OFFSET) {
						return route;
					}

					ArrayList<Vehicle> vehicles = new ArrayList<>();
					return new Route("", -1 , -1, "", "", -1, -1, vehicles, false);
					});
	}
}
