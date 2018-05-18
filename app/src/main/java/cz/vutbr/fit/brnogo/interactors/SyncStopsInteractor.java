package cz.vutbr.fit.brnogo.interactors;

import javax.inject.Inject;

import cz.vutbr.fit.brnogo.data.store.StopStore;
import cz.vutbr.fit.brnogo.injection.annotation.scope.PerScreen;
import cz.vutbr.fit.brnogo.interactors.base.BaseCompletableInteractor;
import io.reactivex.Completable;

/**
 * Class used to sync stops.
 */

@PerScreen
public class SyncStopsInteractor extends BaseCompletableInteractor {

	@Inject StopStore stopStore;

	@Inject
	public SyncStopsInteractor() {
	}

	@Override
	protected Completable buildCompletable() {
		return stopStore.syncStops();
	}
}
