package cz.vutbr.fit.brnogo.interactors;

import javax.inject.Inject;

import cz.vutbr.fit.brnogo.data.store.StopStore;
import cz.vutbr.fit.brnogo.injection.annotation.scope.PerScreen;
import cz.vutbr.fit.brnogo.interactors.base.BaseCompletableInteractor;
import io.reactivex.Completable;

/**
 * Class used to set stops sync status.
 */

@PerScreen
public class SetStopsSyncStatusInteractor extends BaseCompletableInteractor {

	@Inject StopStore stopStore;

	@Inject
	public SetStopsSyncStatusInteractor() {
	}

	@Override
	protected Completable buildCompletable() {
		return Completable.fromAction(() -> stopStore.setStopsSyncStatus(true));
	}
}
