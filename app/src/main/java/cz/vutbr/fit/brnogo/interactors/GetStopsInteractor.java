package cz.vutbr.fit.brnogo.interactors;

import java.util.List;

import javax.inject.Inject;

import cz.vutbr.fit.brnogo.data.model.response.Stop;
import cz.vutbr.fit.brnogo.data.store.StopStore;
import cz.vutbr.fit.brnogo.injection.annotation.scope.PerScreen;
import cz.vutbr.fit.brnogo.interactors.base.BaseFlowableInteractor;
import io.reactivex.Flowable;

/**
 * Class used to return stops.
 */

@PerScreen
public class GetStopsInteractor extends BaseFlowableInteractor<List<Stop>> {

	@Inject StopStore stopStore;

	@Inject
	public GetStopsInteractor() {
	}

	@Override
	protected Flowable<List<Stop>> buildFlowable() {
		return stopStore.getStops().onBackpressureLatest()
				.publish()
				.autoConnect();
	}
}
