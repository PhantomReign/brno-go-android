package cz.vutbr.fit.brnogo.interactors;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cz.vutbr.fit.brnogo.R;
import cz.vutbr.fit.brnogo.data.model.recyclerview.Direction;
import cz.vutbr.fit.brnogo.data.model.recyclerview.RouteItem;
import cz.vutbr.fit.brnogo.data.model.response.Route;
import cz.vutbr.fit.brnogo.data.model.response.Vehicle;
import cz.vutbr.fit.brnogo.data.model.store.Search;
import cz.vutbr.fit.brnogo.data.persistance.Persistence;
import cz.vutbr.fit.brnogo.data.store.DirectionStore;
import cz.vutbr.fit.brnogo.injection.annotation.qualifier.ApplicationContext;
import cz.vutbr.fit.brnogo.injection.annotation.scope.PerScreen;
import cz.vutbr.fit.brnogo.interactors.base.BaseSingleInteractor;
import cz.vutbr.fit.brnogo.tools.constant.DirectionType;
import cz.vutbr.fit.brnogo.tools.constant.VehicleType;
import io.reactivex.Single;

@PerScreen
public class GetDirectionsInteractor extends BaseSingleInteractor<List<RouteItem>> {

	private Search search;
	private Route route;

	@Inject DirectionStore directionStore;
	@Inject Persistence persistence;
	@Inject @ApplicationContext Context context;

	@Inject
	public GetDirectionsInteractor() {
	}

	public GetDirectionsInteractor init(Search search, Route route) {
		this.search = search;
		this.route = route;
		return this;
	}

	@Override
	protected Single<List<RouteItem>> buildSingle() {
		if (route != null) {
			return Single.just(route).map(this::getDirections);
		}

		return directionStore.getDirections(Integer.valueOf(search.getStartStop().getId()), Integer.valueOf(search.getDestinationStop().getId()), search.getDateTime(), search.getTransferTime(), search.getTransfers())
				.map(this::getDirections
		);
	}

	private List<RouteItem> getDirections(Route route) {
		List<RouteItem> directions = new ArrayList<>();

		directions.add(route);

		for (Vehicle vehicle: route.getVehicles()) {
			directions.add(new Direction(context.getString(R.string.direction_walk, vehicle.getPath().get(0).getStationName()), DirectionType.TYPE_WALK));

			if (vehicle.getType() == VehicleType.TYPE_BUS) {
				directions.add(new Direction(context.getString(R.string.direction_board_bus, vehicle.getPath().get(0).getFormattedTimeOfDeparture(), vehicle.getLineId()), DirectionType.TYPE_BOARD_BUS));
			} else if (vehicle.getType() == VehicleType.TYPE_TRAM) {
				directions.add(new Direction(context.getString(R.string.direction_board_tram, vehicle.getPath().get(0).getFormattedTimeOfDeparture(), vehicle.getLineId()), DirectionType.TYPE_BOARD_TRAM));

			} else {
				directions.add(new Direction(context.getString(R.string.direction_board_trolley, vehicle.getPath().get(0).getFormattedTimeOfDeparture(), vehicle.getLineId()), DirectionType.TYPE_BOARD_TROLLEY));

			}

			int size = vehicle.getPath().size();

			int time = (int) (Math.abs(vehicle.getPath().get(0).getTimeOfDeparture() - vehicle.getPath().get(size - 1).getTimeOfArrival())) / 60;

			directions.add(new Direction(context.getString(R.string.direction_stay, time), DirectionType.TYPE_STAY));

			if (vehicle.getType() == VehicleType.TYPE_BUS) {
				directions.add(new Direction(context.getString(R.string.direction_exit_bus, vehicle.getPath().get(size - 1).getStationName()), DirectionType.TYPE_EXIT_BUS));
			} else if (vehicle.getType() == VehicleType.TYPE_TRAM) {
				directions.add(new Direction(context.getString(R.string.direction_exit_tram, vehicle.getPath().get(size - 1).getStationName()), DirectionType.TYPE_EXIT_TRAM));

			} else {
				directions.add(new Direction(context.getString(R.string.direction_exit_trolley, vehicle.getPath().get(size - 1).getStationName()), DirectionType.TYPE_EXIT_TROLLEY));

			}
		}

		directions.add(new Direction(context.getString(R.string.direction_done), DirectionType.TYPE_DONE));

		return directions;
	}
}
