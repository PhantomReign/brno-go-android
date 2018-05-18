package cz.vutbr.fit.brnogo.ui.base;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

/**
 * Base Class representing ViewModel Factory.
 */

public abstract class BaseViewModelFactory<VM extends BaseViewModel> implements ViewModelProvider.Factory {

	protected abstract VM createViewModel();

	@Override
	@SuppressWarnings("unchecked")
	public <T extends ViewModel> T create(Class<T> modelClass) {
		return (T) createViewModel();
	}
}
