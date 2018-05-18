package cz.vutbr.fit.brnogo.interactors;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cz.vutbr.fit.brnogo.data.model.recyclerview.DepartureHeader;
import cz.vutbr.fit.brnogo.data.model.recyclerview.DepartureItem;
import cz.vutbr.fit.brnogo.data.model.response.CurrentDeparture;
import cz.vutbr.fit.brnogo.data.store.DepartureStore;
import cz.vutbr.fit.brnogo.injection.annotation.qualifier.ApplicationContext;
import cz.vutbr.fit.brnogo.injection.annotation.scope.PerScreen;
import cz.vutbr.fit.brnogo.interactors.base.BaseSingleInteractor;
import io.reactivex.Single;

/**
 * Class used to return formatted departures information.
 */

@PerScreen
public class GetDeparturesInteractor extends BaseSingleInteractor<List<DepartureItem>> {

	private int stationId;

	@Inject DepartureStore departureStore;
	@Inject @ApplicationContext Context context;

	@Inject
	public GetDeparturesInteractor() {
	}

	public GetDeparturesInteractor init(int stationId) {
		this.stationId = stationId;
		return this;
	}

	@Override
	protected Single<List<DepartureItem>> buildSingle() {
		return departureStore.getDepartures(stationId).map(currentDepartures -> {
			List<DepartureItem> mappedDepartures = new ArrayList<>();

			for (CurrentDeparture currentDeparture: currentDepartures) {
				mappedDepartures.add(new DepartureHeader(currentDeparture.getStationId(), currentDeparture.getDirection()));
				mappedDepartures.addAll(currentDeparture.getVehicles());
			}

			return  mappedDepartures;
		});
	}
}
