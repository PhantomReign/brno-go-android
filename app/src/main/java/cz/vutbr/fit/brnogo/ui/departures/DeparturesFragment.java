package cz.vutbr.fit.brnogo.ui.departures;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;

import javax.inject.Inject;

import cz.vutbr.fit.brnogo.databinding.FragmentDeparturesBinding;
import cz.vutbr.fit.brnogo.ui.base.BaseFragment;

public class DeparturesFragment extends BaseFragment<DeparturesViewModel, FragmentDeparturesBinding> implements DeparturesView {

	@Inject DeparturesViewModelFactory viewModelFactory;

	public static DeparturesFragment newInstance() {
		return new DeparturesFragment();
	}

	@Override
	protected DeparturesViewModel createViewModel() {
		return ViewModelProviders.of(this, viewModelFactory).get(DeparturesViewModel.class);
	}

	@Override
	protected FragmentDeparturesBinding inflateBindingLayout(LayoutInflater layoutInflater) {
		return FragmentDeparturesBinding.inflate(layoutInflater);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
	}
}
