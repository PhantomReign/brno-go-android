package cz.vutbr.fit.brnogo.ui.directions;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.LinearInterpolator;

import javax.inject.Inject;

import cz.vutbr.fit.brnogo.R;
import cz.vutbr.fit.brnogo.data.model.response.Route;
import cz.vutbr.fit.brnogo.data.model.store.Search;
import cz.vutbr.fit.brnogo.databinding.ActivityDirectionsBinding;
import cz.vutbr.fit.brnogo.tools.constant.Constant;
import cz.vutbr.fit.brnogo.ui.base.BaseActivity;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class DirectionsActivity extends BaseActivity<DirectionsViewModel, ActivityDirectionsBinding> implements DirectionsView {

	@Inject DirectionsViewModelFactory viewModelFactory;

	@Inject DirectionsAdapter routesAdapter;

	public static Intent getStartIntent(Context context, Search searchInfo, Route route) {
		Bundle bundle = new Bundle();
		bundle.putParcelable(Constant.Bundle.KEY_DIRECTIONS_SEARCH_OBJ, searchInfo);
		bundle.putParcelable(Constant.Bundle.KEY_DIRECTIONS_ROUTE_OBJ, route);
		return new Intent(context, DirectionsActivity.class).putExtras(bundle);
	}

	@Override
	protected DirectionsViewModel createViewModel() {
		return ViewModelProviders.of(this, viewModelFactory).get(DirectionsViewModel.class);
	}

	@Override
	protected ActivityDirectionsBinding inflateBindingLayout(LayoutInflater layoutInflater) {
		return ActivityDirectionsBinding.inflate(layoutInflater);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setSupportActionBar(binding.toolbar);

		ActionBar actionBar = getSupportActionBar();
		if (actionBar != null) {
			actionBar.setDisplayHomeAsUpEnabled(true);
			actionBar.setTitle(getString(R.string.directions));
		}

		binding.directionsRecyclerView.setAdapter(routesAdapter);
		binding.directionsRecyclerView.setItemAnimator(new SlideInUpAnimator(new LinearInterpolator()));

		viewModel.getItems().observe(this, routes -> {
			routesAdapter.updateData(routes);
				});

		viewModel.setColor.observe(this, bool -> setSaveColor());

		viewModel.showMessage.observe(this, integer -> {
					Snackbar snackbar =	Snackbar.make(binding.directionsSwipeRefreshLayout, getString(integer), Snackbar.LENGTH_SHORT);
					snackbar.getView().setBackgroundColor(ContextCompat.getColor(binding.getRoot().getContext(), R.color.red_500));
					snackbar.show();
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
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_save, menu);
		//viewModel.isSaved();
		return true;
	}

	private void setSaveColor() {
		Route route = ((Route) viewModel.getItems().getValue().get(0));

		if (route != null && route.getSaved()) {
			binding.toolbar.getMenu().findItem(R.id.action_save).setIcon(R.drawable.ic_bookmark);
		} else {
			binding.toolbar.getMenu().findItem(R.id.action_save).setIcon(R.drawable.ic_bookmark_border);
		}
		binding.toolbar.getMenu().findItem(R.id.action_save).getIcon().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.white), PorterDuff.Mode.SRC_ATOP);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_save:
				viewModel.setSavedRoute();
				return true;
			case android.R.id.home:
				finish();
				return true;
		}
		return false;
	}
}
