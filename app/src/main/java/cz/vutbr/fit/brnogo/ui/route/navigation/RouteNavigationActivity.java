package cz.vutbr.fit.brnogo.ui.route.navigation;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.MenuItem;

import javax.inject.Inject;

import cz.vutbr.fit.brnogo.R;
import cz.vutbr.fit.brnogo.data.model.response.Route;
import cz.vutbr.fit.brnogo.data.model.store.Search;
import cz.vutbr.fit.brnogo.databinding.ActivityRouteNavigationBinding;
import cz.vutbr.fit.brnogo.tools.constant.Constant;
import cz.vutbr.fit.brnogo.ui.base.BaseActivity;
import cz.vutbr.fit.brnogo.ui.base.BaseFragment;
import cz.vutbr.fit.brnogo.ui.route.navigation.map.MapFragment;

public class RouteNavigationActivity extends BaseActivity<RouteNavigationViewModel, ActivityRouteNavigationBinding>
		implements RouteNavigationView {

	@Inject RouteNavigationViewModelFactory viewModelFactory;

	public static Intent getStartIntent(Context context, Route route, Search search) {
		Bundle bundle = new Bundle();
		bundle.putParcelable(Constant.Bundle.KEY_SEARCH_OBJ, search);
		bundle.putParcelable(Constant.Bundle.KEY_ROUTE_OBJ, route);
		return new Intent(context, RouteNavigationActivity.class).putExtras(bundle);
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
			actionBar.setTitle(getString(R.string.nav_to_stop));
			actionBar.setSubtitle(viewModel.route.getDestinationStationName());
		}

		replaceFragment(MapFragment.newInstance());
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

	private void replaceFragment(BaseFragment fragment) {
		getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.map_content, fragment)
				.commit();
	}
}
