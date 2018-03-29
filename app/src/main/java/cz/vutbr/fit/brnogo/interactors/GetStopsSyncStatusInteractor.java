package cz.vutbr.fit.brnogo.interactors;

import javax.inject.Inject;

import cz.vutbr.fit.brnogo.data.store.StopStore;
import cz.vutbr.fit.brnogo.injection.annotation.scope.PerScreen;
import cz.vutbr.fit.brnogo.interactors.base.BaseFlowableInteractor;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;

@PerScreen
public class GetStopsSyncStatusInteractor extends BaseFlowableInteractor<Boolean> {

	@Inject StopStore stopStore;

	@Inject
	public GetStopsSyncStatusInteractor() {
	}

	@Override
	protected Flowable<Boolean> buildFlowable() {
		return stopStore.getStopsSyncStatus()
				.toFlowable(BackpressureStrategy.LATEST);
	}
}
