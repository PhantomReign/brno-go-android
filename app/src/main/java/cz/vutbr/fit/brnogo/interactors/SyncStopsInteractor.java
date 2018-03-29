package cz.vutbr.fit.brnogo.interactors;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import cz.vutbr.fit.brnogo.data.store.StopStore;
import cz.vutbr.fit.brnogo.injection.annotation.scope.PerScreen;
import cz.vutbr.fit.brnogo.interactors.base.BaseCompletableInteractor;
import io.reactivex.Completable;
import timber.log.Timber;

@PerScreen
public class SyncStopsInteractor extends BaseCompletableInteractor {

	@Inject StopStore stopStore;

	@Inject
	public SyncStopsInteractor() {
	}

	@Override
	protected Completable buildCompletable() {
		return stopStore.getStopsSyncStatus()
				.filter(canSync -> canSync)
				.flatMap(__ -> stopStore.syncStops().toObservable().delay(100, TimeUnit.MILLISECONDS))
				.doOnEach(__ -> stopStore.setStopsSyncStatus(false))
				.toList()
				.toCompletable();
	}
}
