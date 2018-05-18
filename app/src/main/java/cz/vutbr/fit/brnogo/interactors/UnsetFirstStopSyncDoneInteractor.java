package cz.vutbr.fit.brnogo.interactors;

import javax.inject.Inject;

import cz.vutbr.fit.brnogo.data.persistance.SettingsInitialData;
import cz.vutbr.fit.brnogo.injection.annotation.scope.PerScreen;
import cz.vutbr.fit.brnogo.interactors.base.BaseSingleInteractor;
import io.reactivex.Single;

/**
 * Class used to unset initial stop sync as done.
 */

@PerScreen
public class UnsetFirstStopSyncDoneInteractor extends BaseSingleInteractor<Boolean> {

	@Inject SettingsInitialData settingsInitialData;

	@Inject
	public UnsetFirstStopSyncDoneInteractor() {
	}

	@Override
	protected Single<Boolean> buildSingle() {
		return Single.fromCallable(() -> {
			settingsInitialData.unsetFirstStopSyncDone();
			return true;
		});
	}
}
