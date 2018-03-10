package cz.vutbr.fit.brnogo.ui.routes.dialog.time;

import javax.inject.Inject;
import javax.inject.Provider;

import cz.vutbr.fit.brnogo.injection.annotation.scope.PerScreen;
import cz.vutbr.fit.brnogo.ui.base.BaseViewModelFactory;

@PerScreen
public class TransferTimePickerViewModelFactory extends BaseViewModelFactory<TransferTimePickerViewModel> {

	@Inject Provider<TransferTimePickerViewModel> viewModelProvider;

	@Inject
	public TransferTimePickerViewModelFactory() {
	}

	@Override
	protected TransferTimePickerViewModel createViewModel() {
		return viewModelProvider.get();
	}
}
