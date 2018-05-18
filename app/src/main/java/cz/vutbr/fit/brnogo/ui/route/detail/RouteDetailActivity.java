package cz.vutbr.fit.brnogo.ui.route.detail;

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
import cz.vutbr.fit.brnogo.data.model.response.Route;
import cz.vutbr.fit.brnogo.databinding.ActivityRouteDetailBinding;
import cz.vutbr.fit.brnogo.tools.constant.Constant;
import cz.vutbr.fit.brnogo.ui.base.BaseActivity;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class RouteDetailActivity extends BaseActivity<RouteDetailViewModel, ActivityRouteDetailBinding> implements RouteDetailView {

	@Inject RouteDetailViewModelFactory viewModelFactory;

	@Inject RouteDetailAdapter routesAdapter;

	public static Intent getStartIntent(Context context, Route route) {
		return new Intent(context, RouteDetailActivity.class).putExtra(Constant.Bundle.KEY_ROUTE_OBJ, route);
	}

	@Override
	protected RouteDetailViewModel createViewModel() {
		return ViewModelProviders.of(this, viewModelFactory).get(RouteDetailViewModel.class);
	}

	@Override
	protected ActivityRouteDetailBinding inflateBindingLayout(LayoutInflater layoutInflater) {
		return ActivityRouteDetailBinding.inflate(layoutInflater);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setSupportActionBar(binding.toolbar);

		ActionBar actionBar = getSupportActionBar();
		if (actionBar != null) {
			actionBar.setDisplayHomeAsUpEnabled(true);
			actionBar.setTitle(getString(R.string.from_stop, viewModel.route.getStartStationName()));
			actionBar.setSubtitle(getString(R.string.to_stop, viewModel.route.getDestinationStationName()));
		}

		binding.routeRecyclerView.setAdapter(routesAdapter);
		binding.routeRecyclerView.setItemAnimator(new SlideInUpAnimator(new LinearInterpolator()));

		viewModel.getItems().observe(this, routes -> {
			routesAdapter.updateData(routes);
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
