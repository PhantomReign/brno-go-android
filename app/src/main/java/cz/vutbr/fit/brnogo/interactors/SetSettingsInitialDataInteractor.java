package cz.vutbr.fit.brnogo.interactors;

import javax.inject.Inject;

import cz.vutbr.fit.brnogo.data.persistance.SettingsInitialData;
import cz.vutbr.fit.brnogo.injection.annotation.scope.PerScreen;
import cz.vutbr.fit.brnogo.interactors.base.BaseSingleInteractor;
import io.reactivex.Single;

/**
 * Class used to determine if settings data were initialized.
 */

@PerScreen
public class SetSettingsInitialDataInteractor extends BaseSingleInteractor<Boolean> {

	@Inject SettingsInitialData settingsInitialData;

	@Inject
	public SetSettingsInitialDataInteractor() {
	}

	@Override
	protected Single<Boolean> buildSingle() {
		return Single.fromCallable(() -> {
			if (!settingsInitialData.isDataInitialized()) {
				settingsInitialData.setInitialData();
				return false;
			}
			return true;
		});
	}
}
