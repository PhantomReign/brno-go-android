package cz.vutbr.fit.brnogo.interactors;

import android.content.Context;
import android.location.Location;
import android.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import cz.vutbr.fit.brnogo.R;
import cz.vutbr.fit.brnogo.data.model.recyclerview.RouteItem;
import cz.vutbr.fit.brnogo.data.model.response.Route;
import cz.vutbr.fit.brnogo.data.model.response.Vehicle;
import cz.vutbr.fit.brnogo.data.persistance.Persistence;
import cz.vutbr.fit.brnogo.data.store.LocationStore;
import cz.vutbr.fit.brnogo.data.store.RouteStore;
import cz.vutbr.fit.brnogo.data.store.VehicleStore;
import cz.vutbr.fit.brnogo.injection.annotation.qualifier.ApplicationContext;
import cz.vutbr.fit.brnogo.injection.annotation.scope.PerScreen;
import cz.vutbr.fit.brnogo.interactors.base.BaseFlowableInteractor;
import cz.vutbr.fit.brnogo.interactors.base.BaseSingleInteractor;
import io.reactivex.Flowable;
import io.reactivex.Single;

@PerScreen
public class GetNavigationInformationInteractor extends BaseFlowableInteractor<Location> {

	private Route route;

	@Inject LocationStore locationStore;
	@Inject @ApplicationContext Context context;

	@Inject
	public GetNavigationInformationInteractor() {
	}

	public GetNavigationInformationInteractor init(Route route) {
		this.route = route;
		return this;
	}

	@Override
	protected Flowable<Location> buildFlowable() {

		return Flowable.interval(1, TimeUnit.SECONDS).startWith((long) 0)
				.flatMap(__ -> locationStore.getLocationObservable().toFlowable()).map(location -> {
					return location;
				}).onBackpressureLatest()
				.publish()
				.autoConnect();
	}
}
