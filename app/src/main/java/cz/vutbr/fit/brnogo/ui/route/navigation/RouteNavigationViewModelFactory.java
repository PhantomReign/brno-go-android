package cz.vutbr.fit.brnogo.ui.route.navigation;

import javax.inject.Inject;
import javax.inject.Provider;

import cz.vutbr.fit.brnogo.injection.annotation.scope.PerScreen;
import cz.vutbr.fit.brnogo.ui.base.BaseViewModelFactory;

@PerScreen
public class RouteNavigationViewModelFactory extends BaseViewModelFactory<RouteNavigationViewModel> {

	@Inject Provider<RouteNavigationViewModel> viewModelProvider;

	@Inject
	public RouteNavigationViewModelFactory() {
	}

	@Override
	protected RouteNavigationViewModel createViewModel() {
		return viewModelProvider.get();
	}
}
