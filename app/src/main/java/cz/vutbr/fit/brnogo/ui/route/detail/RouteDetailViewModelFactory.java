package cz.vutbr.fit.brnogo.ui.route.detail;

import javax.inject.Inject;
import javax.inject.Provider;

import cz.vutbr.fit.brnogo.injection.annotation.scope.PerScreen;
import cz.vutbr.fit.brnogo.ui.base.BaseViewModelFactory;

@PerScreen
public class RouteDetailViewModelFactory extends BaseViewModelFactory<RouteDetailViewModel> {

	@Inject Provider<RouteDetailViewModel> viewModelProvider;

	@Inject
	public RouteDetailViewModelFactory() {
	}

	@Override
	protected RouteDetailViewModel createViewModel() {
		return viewModelProvider.get();
	}
}
