package cz.vutbr.fit.brnogo.interactors;

import android.content.Context;
import android.location.Location;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import cz.vutbr.fit.brnogo.data.store.LocationStore;
import cz.vutbr.fit.brnogo.injection.annotation.qualifier.ApplicationContext;
import cz.vutbr.fit.brnogo.injection.annotation.scope.PerScreen;
import cz.vutbr.fit.brnogo.interactors.base.BaseFlowableInteractor;
import io.reactivex.Flowable;

/**
 * Class used to return location information every second.
 */

@PerScreen
public class GetLocationInformationInteractor extends BaseFlowableInteractor<Location> {

	@Inject LocationStore locationStore;
	@Inject @ApplicationContext Context context;

	@Inject
	public GetLocationInformationInteractor() {
	}

	@Override
	protected Flowable<Location> buildFlowable() {

		return Flowable.interval(1, TimeUnit.SECONDS).startWith((long) 0)
				.flatMap(__ -> locationStore.getLocationObservable().toFlowable().onBackpressureLatest())
				.onBackpressureLatest();
	}
}
