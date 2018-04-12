package cz.vutbr.fit.brnogo.interactors;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cz.vutbr.fit.brnogo.R;
import cz.vutbr.fit.brnogo.data.model.recyclerview.RouteItem;
import cz.vutbr.fit.brnogo.data.model.response.Route;
import cz.vutbr.fit.brnogo.data.model.response.Vehicle;
import cz.vutbr.fit.brnogo.data.persistance.Persistence;
import cz.vutbr.fit.brnogo.data.store.RouteStore;
import cz.vutbr.fit.brnogo.injection.annotation.qualifier.ApplicationContext;
import cz.vutbr.fit.brnogo.injection.annotation.scope.PerScreen;
import cz.vutbr.fit.brnogo.interactors.base.BaseSingleInteractor;
import io.reactivex.Single;
import timber.log.Timber;

@PerScreen
public class GetRoutesInteractor extends BaseSingleInteractor<List<RouteItem>> {

	private int startStationId;
	private int destinationStationId;
	private double userLatitude;
	private double userLongitude;
	private long dateTime;
	private int minTimeToMove;
	private int maxTransfers;
	private boolean liveDataEnabled;

	@Inject RouteStore routeStore;
	@Inject Persistence persistence;
	@Inject @ApplicationContext Context context;

	@Inject
	public GetRoutesInteractor() {
	}

	public GetRoutesInteractor init(int startStationId, int destinationStationId,
										double userLatitude, double userLongitude,
										long dateTime, int minTimeToMove, int maxTransfers,
										boolean liveDataEnabled) {
		this.startStationId = startStationId;
		this.destinationStationId = destinationStationId;
		this.userLatitude = userLatitude;
		this.userLongitude = userLongitude;
		this.dateTime = dateTime;
		this.minTimeToMove = minTimeToMove;
		this.maxTransfers = maxTransfers;
		this.liveDataEnabled = liveDataEnabled;
		return this;
	}

	@Override
	protected Single<List<RouteItem>> buildSingle() {
		int routeLimit = Integer.valueOf(persistence.get(context.getString(R.string.settings_key_limit), "3"));

		return routeStore
				.getRoutes(startStationId, destinationStationId, userLatitude, userLongitude, dateTime, minTimeToMove, maxTransfers, liveDataEnabled, routeLimit)
				.map(routes -> {
					List<RouteItem> filteredRoutes = new ArrayList<>();
					for (Route route: routes) {
						List<Vehicle> vehicles = route.getVehicles();

						filteredRoutes.addAll(vehicles);
						filteredRoutes.add(route);
					}

					return filteredRoutes;
				});
	}
}