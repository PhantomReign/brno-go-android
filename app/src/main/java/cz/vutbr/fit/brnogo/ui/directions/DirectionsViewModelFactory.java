package cz.vutbr.fit.brnogo.ui.directions;

import javax.inject.Inject;
import javax.inject.Provider;

import cz.vutbr.fit.brnogo.injection.annotation.scope.PerScreen;
import cz.vutbr.fit.brnogo.ui.base.BaseViewModelFactory;

@PerScreen
public class DirectionsViewModelFactory extends BaseViewModelFactory<DirectionsViewModel> {

	@Inject Provider<DirectionsViewModel> viewModelProvider;

	@Inject
	public DirectionsViewModelFactory() {
	}

	@Override
	protected DirectionsViewModel createViewModel() {
		return viewModelProvider.get();
	}
}
