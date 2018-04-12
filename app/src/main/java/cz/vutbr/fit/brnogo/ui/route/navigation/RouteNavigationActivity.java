package cz.vutbr.fit.brnogo.ui.route.navigation;

import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.Toast;

import javax.inject.Inject;

import cz.vutbr.fit.brnogo.R;
import cz.vutbr.fit.brnogo.data.model.response.Route;
import cz.vutbr.fit.brnogo.databinding.ActivityRouteNavigationBinding;
import cz.vutbr.fit.brnogo.tools.constant.Constant;
import cz.vutbr.fit.brnogo.ui.base.BaseActivity;
import cz.vutbr.fit.brnogo.ui.base.BaseFragment;
import cz.vutbr.fit.brnogo.ui.route.navigation.map.MapFragment;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;
import timber.log.Timber;

@RuntimePermissions
public class RouteNavigationActivity extends BaseActivity<RouteNavigationViewModel, ActivityRouteNavigationBinding>
		implements RouteNavigationView {

	@Inject RouteNavigationViewModelFactory viewModelFactory;

	public static Intent getStartIntent(Context context, Route route) {
		return new Intent(context, RouteNavigationActivity.class).putExtra(Constant.Bundle.KEY_ROUTE_OBJ, route);
	}

	@Override
	protected RouteNavigationViewModel createViewModel() {
		return ViewModelProviders.of(this, viewModelFactory).get(RouteNavigationViewModel.class);
	}

	@Override
	protected ActivityRouteNavigationBinding inflateBindingLayout(LayoutInflater layoutInflater) {
		return ActivityRouteNavigationBinding.inflate(layoutInflater);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setSupportActionBar(binding.toolbar);

		ActionBar actionBar = getSupportActionBar();
		if (actionBar != null) {
			actionBar.setDisplayHomeAsUpEnabled(true);
			actionBar.setTitle(getString(R.string.nav_to_stop, viewModel.route.getDestinationStationName()));
		}

		replaceFragment(MapFragment.newInstance());

		viewModel.getLocationData().observe(this, location -> {
			Timber.d("LC: " + location.toString());
		});

		viewModel.loadData();
	}

	@NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)

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

	@Override
	public void onShowFasterRoute(Route route) {
		Toast.makeText(getApplicationContext(), "FASTER", Toast.LENGTH_SHORT).show();

	}

	private void replaceFragment(BaseFragment fragment) {
		getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.map_content, fragment)
				.commit();
	}
}
