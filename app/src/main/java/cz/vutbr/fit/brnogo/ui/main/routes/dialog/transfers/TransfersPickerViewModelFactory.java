package cz.vutbr.fit.brnogo.ui.main.routes.dialog.transfers;

import javax.inject.Inject;
import javax.inject.Provider;

import cz.vutbr.fit.brnogo.injection.annotation.scope.PerScreen;
import cz.vutbr.fit.brnogo.ui.base.BaseViewModelFactory;

@PerScreen
public class TransfersPickerViewModelFactory extends BaseViewModelFactory<TransfersPickerViewModel> {

	@Inject Provider<TransfersPickerViewModel> viewModelProvider;

	@Inject
	public TransfersPickerViewModelFactory() {
	}

	@Override
	protected TransfersPickerViewModel createViewModel() {
		return viewModelProvider.get();
	}
}
