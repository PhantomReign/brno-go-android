package cz.vutbr.fit.brnogo.ui.routes;

import javax.inject.Inject;

import cz.vutbr.fit.brnogo.injection.annotation.scope.PerScreen;
import cz.vutbr.fit.brnogo.ui.base.BaseViewModel;

@PerScreen
public class RoutesViewModel extends BaseViewModel {

	@Inject
	public RoutesViewModel() {
	}

	@Override
	protected void onCleared() {
		super.onCleared();
	}
}
