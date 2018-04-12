package cz.vutbr.fit.brnogo.ui.route;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

import javax.inject.Inject;

import cz.vutbr.fit.brnogo.R;
import cz.vutbr.fit.brnogo.data.model.response.Route;
import cz.vutbr.fit.brnogo.data.model.store.Search;
import cz.vutbr.fit.brnogo.databinding.ActivityRoutesBinding;
import cz.vutbr.fit.brnogo.tools.constant.Constant;
import cz.vutbr.fit.brnogo.ui.base.BaseActivity;
import cz.vutbr.fit.brnogo.ui.route.detail.RouteDetailActivity;
import cz.vutbr.fit.brnogo.ui.route.navigation.RouteNavigationActivity;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;
import timber.log.Timber;

public class RoutesActivity extends BaseActivity<RoutesViewModel, ActivityRoutesBinding> implements RoutesView {

	@Inject RoutesViewModelFactory viewModelFactory;

	@Inject RoutesAdapter routesAdapter;

	public static Intent getStartIntent(Context context, Search searchInfo) {
		return new Intent(context, RoutesActivity.class).putExtra(Constant.Bundle.KEY_SEARCH_OBJ, searchInfo);
	}

	@Override
	protected RoutesViewModel createViewModel() {
		return ViewModelProviders.of(this, viewModelFactory).get(RoutesViewModel.class);
	}

	@Override
	protected ActivityRoutesBinding inflateBindingLayout(LayoutInflater layoutInflater) {
		return ActivityRoutesBinding.inflate(layoutInflater);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setSupportActionBar(binding.toolbar);

		ActionBar actionBar = getSupportActionBar();
		if (actionBar != null) {
			actionBar.setDisplayHomeAsUpEnabled(true);
			actionBar.setTitle(R.string.available_routes);
		}

		binding.routeRecyclerView.setAdapter(routesAdapter);
		binding.routeRecyclerView.setItemAnimator(new SlideInUpAnimator(new LinearInterpolator()));

		viewModel.getItems().observe(this, routes -> {
			routesAdapter.updateData(routes);
				});

		viewModel.setColor.observe(this, bool -> setFavoriteColor());

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
		getMenuInflater().inflate(R.menu.menu_favorite, menu);
		viewModel.isFavorite();
		return true;
	}

	private void setFavoriteColor() {
		if (viewModel.search.getFavorite()) {
			binding.toolbar.getMenu().findItem(R.id.action_favorite).setIcon(R.drawable.ic_favorite);
			binding.toolbar.getMenu().findItem(R.id.action_favorite).getIcon().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.white), PorterDuff.Mode.SRC_ATOP);
		} else {
			binding.toolbar.getMenu().findItem(R.id.action_favorite).setIcon(R.drawable.ic_favorite_border);
			binding.toolbar.getMenu().findItem(R.id.action_favorite).getIcon().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.white), PorterDuff.Mode.SRC_ATOP);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_favorite:
				viewModel.setFavoriteSearch();
				return true;
			case android.R.id.home:
				finish();
				return true;
		}
		return false;
	}

	@Override
	public void onInformationClick(Route route) {
		startActivity(RouteDetailActivity.getStartIntent(getApplicationContext(), route));

	}

	@Override
	public void onNavigationClick(Route route) {
		startActivity(RouteNavigationActivity.getStartIntent(getApplicationContext(), route));
	}
}
