package cz.vutbr.fit.brnogo.ui.main;

import javax.inject.Inject;

import cz.vutbr.fit.brnogo.injection.annotation.scope.PerScreen;
import cz.vutbr.fit.brnogo.ui.base.BaseViewModel;

@PerScreen
public class MainViewModel extends BaseViewModel {

	@Inject
	public MainViewModel() {

	}

	@Override
	protected void onCleared() {
		super.onCleared();
	}
}
