package cz.vutbr.fit.brnogo.interactors;

import android.content.Context;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import cz.vutbr.fit.brnogo.data.model.response.LiveVehicle;
import cz.vutbr.fit.brnogo.data.store.VehicleStore;
import cz.vutbr.fit.brnogo.injection.annotation.qualifier.ApplicationContext;
import cz.vutbr.fit.brnogo.injection.annotation.scope.PerScreen;
import cz.vutbr.fit.brnogo.interactors.base.BaseFlowableInteractor;
import io.reactivex.Flowable;

/**
 * Class used to return vehicle information every 10 seconds.
 */

@PerScreen
public class GetVehicleInteractor extends BaseFlowableInteractor<LiveVehicle> {

	private int lineCode;
	private int lineId;

	@Inject VehicleStore vehicleStore;
	@Inject @ApplicationContext Context context;

	@Inject
	public GetVehicleInteractor() {
	}

	public GetVehicleInteractor init(int lineCode, int lineId) {
		this.lineCode = lineCode;
		this.lineId = lineId;
		return this;
	}

	@Override
	protected Flowable<LiveVehicle> buildFlowable() {
		return Flowable.interval(10, TimeUnit.SECONDS).startWith((long) 0)
				.flatMap(__ -> vehicleStore.getVehicle(lineCode, lineId).toFlowable().onBackpressureLatest())
				.onBackpressureLatest();
	}
}
