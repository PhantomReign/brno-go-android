package cz.vutbr.fit.brnogo.ui.routes;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;

import javax.inject.Inject;

import cz.vutbr.fit.brnogo.databinding.FragmentRoutesBinding;
import cz.vutbr.fit.brnogo.ui.base.BaseFragment;

public class RoutesFragment extends BaseFragment<RoutesViewModel, FragmentRoutesBinding> implements RoutesView {

	@Inject RoutesViewModelFactory viewModelFactory;

	public static RoutesFragment newInstance() {
		return new RoutesFragment();
	}

	@Override
	protected RoutesViewModel createViewModel() {
		return ViewModelProviders.of(this, viewModelFactory).get(RoutesViewModel.class);
	}

	@Override
	protected FragmentRoutesBinding inflateBindingLayout(LayoutInflater layoutInflater) {
		return FragmentRoutesBinding.inflate(layoutInflater);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
	}
}
