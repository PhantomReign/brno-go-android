package cz.vutbr.fit.brnogo.interactors;

import javax.inject.Inject;

import cz.vutbr.fit.brnogo.data.persistance.SettingsInitialData;
import cz.vutbr.fit.brnogo.injection.annotation.scope.PerScreen;
import cz.vutbr.fit.brnogo.interactors.base.BaseSingleInteractor;
import io.reactivex.Single;

@PerScreen
public class SetFirstStopSyncDoneInteractor extends BaseSingleInteractor<Boolean> {

	@Inject SettingsInitialData settingsInitialData;

	@Inject
	public SetFirstStopSyncDoneInteractor() {
	}

	@Override
	protected Single<Boolean> buildSingle() {
		return Single.fromCallable(() -> {
			if (!settingsInitialData.isFirstStopSyncDone()) {
				settingsInitialData.setFirstStopSyncDone();
				return false;
			}
			return true;
		});
	}
}
