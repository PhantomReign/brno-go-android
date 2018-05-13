package cz.vutbr.fit.brnogo.ui.main.departures;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

import javax.inject.Inject;

import cz.vutbr.fit.brnogo.R;
import cz.vutbr.fit.brnogo.data.model.response.Stop;
import cz.vutbr.fit.brnogo.data.model.store.FavoriteStop;
import cz.vutbr.fit.brnogo.databinding.FragmentDeparturesBinding;
import cz.vutbr.fit.brnogo.tools.constant.Constant;
import cz.vutbr.fit.brnogo.ui.base.BaseFragment;
import cz.vutbr.fit.brnogo.ui.departures.DeparturesActivity;
import cz.vutbr.fit.brnogo.ui.stop.StopSearchActivity;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class DeparturesFragment extends BaseFragment<DeparturesViewModel, FragmentDeparturesBinding> implements DeparturesView {

	@Inject DeparturesViewModelFactory viewModelFactory;
	@Inject DeparturesAdapter departuresAdapter;

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
		binding.departuresRecyclerView.setAdapter(departuresAdapter);
		binding.departuresRecyclerView.setItemAnimator(new SlideInUpAnimator(new LinearInterpolator()));

		viewModel.getItems().observe(this, searches -> {
			departuresAdapter.updateData(searches);
			binding.favoriteDepartures.setVisibility(getFavoritesTextVisibility());
		});
	}

	private int getFavoritesTextVisibility() {
		return departuresAdapter.getItemCount() > 0 ? View.VISIBLE : View.INVISIBLE;
	}

	private void setCorrectEditTexts() {
		if (viewModel.getStartStop() == null) {
			binding.departuresTextInputFrom.setText("");
		} else {
			binding.departuresTextInputFrom.setText(viewModel.getStartStop().getName());
		}
	}

	@Override
	public void onResume() {
		setCorrectEditTexts();
		super.onResume();
	}

	@Override
	public void onFindDeparturesClick() {
		if (viewModel.getStartStop() != null) {
			startActivity(DeparturesActivity.getStartIntent(getContext(), viewModel.getStartStop()));
		} else {
			Toast.makeText(getContext(), getString(R.string.select_stop), Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onStartTextEditClick() {
		startActivityForResult(StopSearchActivity.getStartIntent(getContext()), Constant.RequestCode.STOP_FROM);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
			case Constant.RequestCode.STOP_FROM:
				if (resultCode == Activity.RESULT_OK) {
					Stop stop = data.getParcelableExtra(Constant.Bundle.KEY_STOP_OBJ);
					FavoriteStop favoriteStop = new FavoriteStop();
					favoriteStop.setId(stop.getId());
					favoriteStop.setName(stop.getName());
					favoriteStop.setZone(stop.getZone());
					viewModel.setStartStop(favoriteStop);
					binding.departuresTextInputFrom.setText(viewModel.getStartStop().getName());
				}
				break;
			default:
				super.onActivityResult(requestCode, resultCode, data);
				break;
		}
	}

	@Override
	public void onFavoriteButtonClick(FavoriteStop stop) {
		viewModel.setFavoriteStop(stop);
	}

	@Override
	public void onFavoriteItemClick(FavoriteStop stop) {
		startActivity(DeparturesActivity.getStartIntent(getContext(), stop));
	}
}
