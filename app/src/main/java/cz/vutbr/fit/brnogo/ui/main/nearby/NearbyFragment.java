package cz.vutbr.fit.brnogo.ui.main.nearby;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;

import javax.inject.Inject;

import cz.vutbr.fit.brnogo.databinding.FragmentNearbyBinding;
import cz.vutbr.fit.brnogo.ui.base.BaseFragment;

public class NearbyFragment extends BaseFragment<NearbyViewModel, FragmentNearbyBinding> implements NearbyView {

	@Inject NearbyViewModelFactory viewModelFactory;

	public static NearbyFragment newInstance() {
		return new NearbyFragment();
	}

	@Override
	protected NearbyViewModel createViewModel() {
		return ViewModelProviders.of(this, viewModelFactory).get(NearbyViewModel.class);
	}

	@Override
	protected FragmentNearbyBinding inflateBindingLayout(LayoutInflater layoutInflater) {
		return FragmentNearbyBinding.inflate(layoutInflater);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
	}
}
