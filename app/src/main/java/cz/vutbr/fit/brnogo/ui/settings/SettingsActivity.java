package cz.vutbr.fit.brnogo.ui.settings;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.MenuItem;

import javax.inject.Inject;

import cz.vutbr.fit.brnogo.R;
import cz.vutbr.fit.brnogo.databinding.ActivitySettingsBinding;
import cz.vutbr.fit.brnogo.tools.constant.Constant;
import cz.vutbr.fit.brnogo.ui.base.BaseActivity;

public class SettingsActivity extends BaseActivity<SettingsViewModel, ActivitySettingsBinding> implements SettingsView {

	@Inject
	SettingsViewModelFactory viewModelFactory;

	public static Intent getStartIntent(Context context) {
		return new Intent(context, SettingsActivity.class);
	}

	@Override
	protected SettingsViewModel createViewModel() {
		return ViewModelProviders.of(this, viewModelFactory).get(SettingsViewModel.class);
	}

	@Override
	protected ActivitySettingsBinding inflateBindingLayout(LayoutInflater layoutInflater) {
		return ActivitySettingsBinding.inflate(layoutInflater);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setSupportActionBar(binding.toolbar);

		ActionBar actionBar = getSupportActionBar();
		if (actionBar != null) {
			actionBar.setDisplayHomeAsUpEnabled(true);
		}

		if (savedInstanceState == null) {
			getSupportFragmentManager()
					.beginTransaction()
					.replace(R.id.preference_content, new SettingsFragment())
					.commit();
		}

		viewModel.msgType.observe(this, syncStatus -> {
			if (syncStatus != null) {
				Snackbar snackbar = Snackbar.make(binding.preferenceContent, "", Snackbar.LENGTH_SHORT);

				switch (syncStatus) {
					case Constant.SyncStatus.DONE:
						snackbar.getView().setBackgroundColor(ContextCompat.getColor(binding.getRoot().getContext(), R.color.green));
						snackbar.setDuration(Snackbar.LENGTH_SHORT);
						snackbar.setText(getString(R.string.sync_success));
						break;
					case Constant.SyncStatus.ERROR:
						snackbar.getView().setBackgroundColor(ContextCompat.getColor(binding.getRoot().getContext(), R.color.red_500));
						snackbar.setDuration(Snackbar.LENGTH_SHORT);
						snackbar.setText(getString(R.string.sync_failed));
						break;
					default:
						snackbar.getView().setBackgroundColor(ContextCompat.getColor(binding.getRoot().getContext(), R.color.colorDark));
						snackbar.setDuration(Snackbar.LENGTH_INDEFINITE);
						snackbar.setText(getString(R.string.sync_in_progress));
						break;
				}
				snackbar.show();
			}
		});
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

	protected void sync() {
		viewModel.sync();
	}
}
