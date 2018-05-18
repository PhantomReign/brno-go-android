package cz.vutbr.fit.brnogo.ui.stop;

import android.app.SearchManager;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.LinearInterpolator;

import javax.inject.Inject;

import cz.vutbr.fit.brnogo.R;
import cz.vutbr.fit.brnogo.data.model.response.Stop;
import cz.vutbr.fit.brnogo.databinding.ActivitySearchBinding;
import cz.vutbr.fit.brnogo.tools.constant.Constant;
import cz.vutbr.fit.brnogo.ui.base.BaseActivity;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class StopSearchActivity extends BaseActivity<StopSearchViewModel, ActivitySearchBinding> implements StopSearchView {

	@Inject StopSearchViewModelFactory viewModelFactory;

	@Inject StopSearchAdapter stopSearchAdapter;

	public static Intent getStartIntent(Context context) {
		return new Intent(context, StopSearchActivity.class);
	}

	@Override
	protected StopSearchViewModel createViewModel() {
		return ViewModelProviders.of(this, viewModelFactory).get(StopSearchViewModel.class);
	}

	@Override
	protected ActivitySearchBinding inflateBindingLayout(LayoutInflater layoutInflater) {
		return ActivitySearchBinding.inflate(layoutInflater);
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

		binding.recyclerView.setAdapter(stopSearchAdapter);
		binding.recyclerView.setItemAnimator(new SlideInUpAnimator(new LinearInterpolator()));

		viewModel.getItems().observe(this, stops -> {
					stopSearchAdapter.updateData(stops);
				});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_search, menu);

		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

		SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
		searchView.setSearchableInfo(searchManager != null ? searchManager.getSearchableInfo(getComponentName()) : null);
		searchView.setMaxWidth(Integer.MAX_VALUE);

		searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			@Override
			public boolean onQueryTextSubmit(String query) {
				stopSearchAdapter.getFilter().filter(query);
				return false;
			}

			@Override
			public boolean onQueryTextChange(String query) {
				stopSearchAdapter.getFilter().filter(query);
				return false;
			}
		});
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem) {
		switch (menuItem.getItemId()) {
			case R.id.action_search:
				return true;
			case android.R.id.home:
				finish();
				return true;
		}
		return false;
	}

	@Override
	public void onStopClick(Stop stop) {
		Intent intent = new Intent();
		intent.putExtra(Constant.Bundle.KEY_STOP_OBJ, stop);
		setResult(RESULT_OK, intent);
		finish();
	}
}
