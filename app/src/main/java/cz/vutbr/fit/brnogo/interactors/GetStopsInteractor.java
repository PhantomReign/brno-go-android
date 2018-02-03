package cz.vutbr.fit.brnogo.interactors;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cz.vutbr.fit.brnogo.data.model.response.Stop;
import cz.vutbr.fit.brnogo.injection.annotation.qualifier.ApplicationContext;
import cz.vutbr.fit.brnogo.injection.annotation.scope.PerScreen;
import cz.vutbr.fit.brnogo.interactors.base.BaseSingleInteractor;
import io.reactivex.Single;

@PerScreen
public class GetStopsInteractor extends BaseSingleInteractor<List<Stop>> {

	@Inject @ApplicationContext Context context;

	@Inject
	public GetStopsInteractor() {
	}

	@Override
	protected Single<List<Stop>> buildSingle() {
		return Single.fromCallable(() -> {
			List<Stop> stops = new ArrayList<>();
			stops.add(new Stop("0", "Skácelova", "Tram"));
			stops.add(new Stop("1", "Červinkova", "Tram"));
			stops.add(new Stop("2", "Česká", "Tram"));
			stops.add(new Stop("3", "Hlavní nádraží", "Bus"));
			stops.add(new Stop("4", "Grohova", "Tram"));
			stops.add(new Stop("5", "Rybkova", "Tram"));
			stops.add(new Stop("6", "Klusáčkova", "Tram"));
			stops.add(new Stop("7", "Dobrovského", "Tram"));
			stops.add(new Stop("8", "Husitská", "Bus"));
			stops.add(new Stop("9", "Přívrat", "Bus"));
			return stops;
		});
	}
}
