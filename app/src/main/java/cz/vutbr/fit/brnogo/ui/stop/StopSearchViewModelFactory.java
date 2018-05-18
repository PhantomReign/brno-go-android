package cz.vutbr.fit.brnogo.ui.stop;

import javax.inject.Inject;
import javax.inject.Provider;

import cz.vutbr.fit.brnogo.injection.annotation.scope.PerScreen;
import cz.vutbr.fit.brnogo.ui.base.BaseViewModelFactory;

@PerScreen
public class StopSearchViewModelFactory extends BaseViewModelFactory<StopSearchViewModel> {

	@Inject Provider<StopSearchViewModel> viewModelProvider;

	@Inject
	public StopSearchViewModelFactory() {
	}

	@Override
	protected StopSearchViewModel createViewModel() {
		return viewModelProvider.get();
	}
}
