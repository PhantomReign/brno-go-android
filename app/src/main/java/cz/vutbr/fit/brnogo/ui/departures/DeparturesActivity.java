package cz.vutbr.fit.brnogo.ui.departures;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.animation.LinearInterpolator;

import javax.inject.Inject;

import cz.vutbr.fit.brnogo.R;
import cz.vutbr.fit.brnogo.data.model.response.Stop;
import cz.vutbr.fit.brnogo.databinding.ActivityDeparturesBinding;
import cz.vutbr.fit.brnogo.tools.constant.Constant;
import cz.vutbr.fit.brnogo.ui.base.BaseActivity;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class DeparturesActivity extends BaseActivity<DeparturesViewModel, ActivityDeparturesBinding> implements DeparturesView {

	@Inject DeparturesViewModelFactory viewModelFactory;

	@Inject DeparturesAdapter departuresAdapter;

	public static Intent getStartIntent(Context context, Stop stop) {
		return new Intent(context, DeparturesActivity.class).putExtra(Constant.Bundle.KEY_STOP_TO_DEP_OBJ, stop);
	}

	@Override
	protected DeparturesViewModel createViewModel() {
		return ViewModelProviders.of(this, viewModelFactory).get(DeparturesViewModel.class);
	}

	@Override
	protected ActivityDeparturesBinding inflateBindingLayout(LayoutInflater layoutInflater) {
		return ActivityDeparturesBinding.inflate(layoutInflater);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setSupportActionBar(binding.toolbar);

		ActionBar actionBar = getSupportActionBar();
		if (actionBar != null) {
			actionBar.setDisplayHomeAsUpEnabled(true);
			actionBar.setTitle(R.string.stops);
		}

		binding.departureRecyclerView.setAdapter(departuresAdapter);
		binding.departureRecyclerView.setItemAnimator(new SlideInUpAnimator(new LinearInterpolator()));

		viewModel.getItems().observe(this, stops -> {
			departuresAdapter.updateData(stops);
				});

		if (savedInstanceState == null) {
			viewModel.loadData();
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				finish();
				return true;
		}
		return false;
	}
}
