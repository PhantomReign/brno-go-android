package cz.vutbr.fit.brnogo.ui.base;

import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.annotation.CallSuper;

import dagger.android.support.AndroidSupportInjection;

public abstract class BaseFragment<T extends ViewModel, B extends ViewDataBinding> extends BaseInjectableFragment<T, B> {

	@Override
	@CallSuper
	public void onAttach(Context context) {
		super.onAttach(context);
		AndroidSupportInjection.inject(this);
	}
}
