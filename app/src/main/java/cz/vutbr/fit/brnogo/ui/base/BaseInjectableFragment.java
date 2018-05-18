package cz.vutbr.fit.brnogo.ui.base;

import android.arch.lifecycle.ViewModel;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cz.vutbr.fit.brnogo.BR;

/**
 * Base Class representing Injectable Fragment.
 */

public abstract class BaseInjectableFragment<T extends ViewModel, B extends ViewDataBinding> extends Fragment implements BaseView {

	protected T viewModel;

	protected B binding;

	protected abstract T createViewModel();

	protected abstract B inflateBindingLayout(LayoutInflater layoutInflater);

	@Nullable
	@Override
	@CallSuper
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

		viewModel = createViewModel();

		binding = inflateBindingLayout(getLayoutInflater());
		binding.setVariable(BR.view, this);
		binding.setVariable(BR.viewModel, viewModel);

		return binding.getRoot();
	}
}
