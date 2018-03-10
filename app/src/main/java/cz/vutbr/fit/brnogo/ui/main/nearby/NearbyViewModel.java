package cz.vutbr.fit.brnogo.ui.main.nearby;

import javax.inject.Inject;

import cz.vutbr.fit.brnogo.injection.annotation.scope.PerScreen;
import cz.vutbr.fit.brnogo.ui.base.BaseViewModel;

@PerScreen
public class NearbyViewModel extends BaseViewModel {

	@Inject
	public NearbyViewModel() {
	}

	@Override
	protected void onCleared() {
		super.onCleared();
	}
}
