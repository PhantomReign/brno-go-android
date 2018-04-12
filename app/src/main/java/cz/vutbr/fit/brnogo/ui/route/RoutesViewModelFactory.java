package cz.vutbr.fit.brnogo.ui.route;

import javax.inject.Inject;
import javax.inject.Provider;

import cz.vutbr.fit.brnogo.injection.annotation.scope.PerScreen;
import cz.vutbr.fit.brnogo.ui.base.BaseViewModelFactory;

@PerScreen
public class RoutesViewModelFactory extends BaseViewModelFactory<RoutesViewModel> {

	@Inject Provider<RoutesViewModel> viewModelProvider;

	@Inject
	public RoutesViewModelFactory() {
	}

	@Override
	protected RoutesViewModel createViewModel() {
		return viewModelProvider.get();
	}
}
