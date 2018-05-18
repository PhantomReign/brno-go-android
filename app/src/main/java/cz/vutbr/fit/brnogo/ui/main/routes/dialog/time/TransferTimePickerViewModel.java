package cz.vutbr.fit.brnogo.ui.main.routes.dialog.time;

import javax.inject.Inject;

import cz.vutbr.fit.brnogo.injection.annotation.scope.PerScreen;
import cz.vutbr.fit.brnogo.ui.base.BaseViewModel;

@PerScreen
public class TransferTimePickerViewModel extends BaseViewModel {

	@Inject
	public TransferTimePickerViewModel() {
	}

	@Override
	protected void onCleared() {
		super.onCleared();
	}
}
