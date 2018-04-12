package cz.vutbr.fit.brnogo.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.MenuItem;

import javax.inject.Inject;

import cz.vutbr.fit.brnogo.R;
import cz.vutbr.fit.brnogo.databinding.ActivityMainBinding;
import cz.vutbr.fit.brnogo.tools.constant.StartScreenType;
import cz.vutbr.fit.brnogo.ui.base.BaseActivity;
import cz.vutbr.fit.brnogo.ui.base.BaseFragment;
import cz.vutbr.fit.brnogo.ui.main.departures.DeparturesFragment;
import cz.vutbr.fit.brnogo.ui.main.nearby.NearbyFragment;
import cz.vutbr.fit.brnogo.ui.main.routes.RoutesFragment;
import cz.vutbr.fit.brnogo.ui.settings.SettingsActivity;
import timber.log.Timber;

public class MainActivity extends BaseActivity<MainViewModel, ActivityMainBinding> implements MainView {

	@Inject MainViewModelFactory viewModelFactory;

	private RoutesFragment routesFragment = RoutesFragment.newInstance();
	private DeparturesFragment departuresFragment = DeparturesFragment.newInstance();
	private NearbyFragment nearbyFragment = NearbyFragment.newInstance();

	public static Intent getStartIntent(Context context) {
		return new Intent(context, MainActivity.class);
	}

	@Override
	protected MainViewModel createViewModel() {
		return ViewModelProviders.of(this, viewModelFactory).get(MainViewModel.class);
	}

	@Override
	protected ActivityMainBinding inflateBindingLayout(LayoutInflater layoutInflater) {
		return ActivityMainBinding.inflate(layoutInflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				startActivity(SettingsActivity.getStartIntent(this));
				return true;
		}
		return false;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setSupportActionBar(binding.toolbar);

		ActionBar actionBar = getSupportActionBar();
		if (actionBar != null) {
			actionBar.setDisplayHomeAsUpEnabled(true);
		}

		if (savedInstanceState == null) {
			viewModel.getStartScreenLiveData().observe(this, startScreen -> {
				if (startScreen != null) {
					setStartScreen(startScreen);
				}
			});
		}
	}

	@Override
	public boolean onBottomNavigationItemClick(MenuItem menuItem) {
		switch (menuItem.getItemId()) {
			case R.id.menu_bottom_navigation_routes:
				selectBottomNavigationItem(menuItem, routesFragment);
				return true;
			case R.id.menu_bottom_navigation_departures:
				selectBottomNavigationItem(menuItem, departuresFragment);
				return true;
			case R.id.menu_bottom_navigation_nearby:
				selectBottomNavigationItem(menuItem, nearbyFragment);
				return true;
		}
		return false;
	}

	private void setStartScreen(String startScreen) {
		switch (startScreen) {
			case StartScreenType.TYPE_NEARBY:
				replaceFragment(nearbyFragment);
				binding.mainBottomNavigationView.setSelectedItemId(R.id.menu_bottom_navigation_nearby);
				break;
			case StartScreenType.TYPE_DEPARTURES:
				replaceFragment(departuresFragment);
				binding.mainBottomNavigationView.setSelectedItemId(R.id.menu_bottom_navigation_departures);
				break;
			default:
				replaceFragment(routesFragment);
				binding.mainBottomNavigationView.setSelectedItemId(R.id.menu_bottom_navigation_routes);
				break;
		}
	}

	private void selectBottomNavigationItem(MenuItem menuItem, BaseFragment fragment) {
		if (!menuItem.isChecked()) {
			replaceFragment(fragment);
		}
	}

	private void replaceFragment(BaseFragment fragment) {
		getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.main_content, fragment)
				.commit();
	}
}
