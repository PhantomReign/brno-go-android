package cz.vutbr.fit.brnogo.ui.nearby;

import javax.inject.Inject;
import javax.inject.Provider;

import cz.vutbr.fit.brnogo.injection.annotation.scope.PerScreen;
import cz.vutbr.fit.brnogo.ui.base.BaseViewModelFactory;

@PerScreen
public class NearbyViewModelFactory extends BaseViewModelFactory<NearbyViewModel> {

	@Inject Provider<NearbyViewModel> viewModelProvider;

	@Inject
	public NearbyViewModelFactory() {
	}

	@Override
	protected NearbyViewModel createViewModel() {
		return viewModelProvider.get();
	}
}