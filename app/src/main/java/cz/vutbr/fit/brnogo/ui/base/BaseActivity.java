package cz.vutbr.fit.brnogo.ui.base;

import android.arch.lifecycle.ViewModel;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;

import cz.vutbr.fit.brnogo.BR;
import dagger.android.support.DaggerAppCompatActivity;

/**
 * Base Class representing Activity.
 */

public abstract class BaseActivity<T extends ViewModel, B extends ViewDataBinding> extends DaggerAppCompatActivity {

	protected T viewModel;

	protected B binding;

	protected abstract T createViewModel();

	protected abstract B inflateBindingLayout(LayoutInflater layoutInflater);

	@CallSuper
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		viewModel = createViewModel();

		binding = inflateBindingLayout(getLayoutInflater());
		binding.setVariable(BR.view, this);
		binding.setVariable(BR.viewModel, viewModel);
		setContentView(binding.getRoot());
	}
}
