package cz.vutbr.fit.brnogo.ui.routes.dialog.transfers;

import javax.inject.Inject;

import cz.vutbr.fit.brnogo.injection.annotation.scope.PerScreen;
import cz.vutbr.fit.brnogo.ui.base.BaseViewModel;

@PerScreen
public class TransfersPickerViewModel extends BaseViewModel {

	@Inject
	public TransfersPickerViewModel() {
	}

	@Override
	protected void onCleared() {
		super.onCleared();
	}
}
