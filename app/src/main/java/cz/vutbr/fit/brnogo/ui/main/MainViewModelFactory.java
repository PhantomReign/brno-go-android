package cz.vutbr.fit.brnogo.ui.main;

import javax.inject.Inject;
import javax.inject.Provider;

import cz.vutbr.fit.brnogo.injection.annotation.scope.PerScreen;
import cz.vutbr.fit.brnogo.ui.base.BaseViewModelFactory;

@PerScreen
public class MainViewModelFactory extends BaseViewModelFactory<MainViewModel> {

	@Inject Provider<MainViewModel> viewModelProvider;

	@Inject
	public MainViewModelFactory() {
	}

	@Override
	protected MainViewModel createViewModel() {
		return viewModelProvider.get();
	}
}
