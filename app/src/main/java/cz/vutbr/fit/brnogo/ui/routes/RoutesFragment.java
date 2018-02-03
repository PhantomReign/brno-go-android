package cz.vutbr.fit.brnogo.ui.routes;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;

import javax.inject.Inject;

import cz.vutbr.fit.brnogo.data.model.response.Stop;
import cz.vutbr.fit.brnogo.databinding.FragmentRoutesBinding;
import cz.vutbr.fit.brnogo.tools.constant.Constant;
import cz.vutbr.fit.brnogo.ui.base.BaseFragment;
import cz.vutbr.fit.brnogo.ui.stop.StopSearchActivity;
import timber.log.Timber;

public class RoutesFragment extends BaseFragment<RoutesViewModel, FragmentRoutesBinding> implements RoutesView {

	@Inject RoutesViewModelFactory viewModelFactory;

	private Stop start;
	private Stop destination;

	public static RoutesFragment newInstance() {
		return new RoutesFragment();
	}

	@Override
	protected RoutesViewModel createViewModel() {
		return ViewModelProviders.of(this, viewModelFactory).get(RoutesViewModel.class);
	}

	@Override
	protected FragmentRoutesBinding inflateBindingLayout(LayoutInflater layoutInflater) {
		return FragmentRoutesBinding.inflate(layoutInflater);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		start = null;
		destination = null;
	}

	@Override
	public void onFindRouteClick() {
		Timber.i("FIND ROUTE");
	}

	@Override
	public void onStartTextEditClick() {
		startActivityForResult(StopSearchActivity.getStartIntent(getContext()), Constant.RequestCode.STOP_FROM);
	}

	@Override
	public void onDestinationTextEditClick() {
		startActivityForResult(StopSearchActivity.getStartIntent(getContext()), Constant.RequestCode.STOP_TO);
	}

	@Override
	public void onSwitchClick() {
		Stop tmpStop = start;
		start = destination;
		destination = tmpStop;

		if (start == null) {
			binding.textInputFrom.setText("");
		} else {
			binding.textInputFrom.setText(start.getName());
		}

		if (destination == null) {
			binding.textInputTo.setText("");
		} else {
			binding.textInputTo.setText(destination.getName());
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
			case Constant.RequestCode.STOP_FROM:
				if (resultCode == Activity.RESULT_OK) {
					start = data.getParcelableExtra(Constant.Bundle.KEY_STOP_OBJ);
					binding.textInputFrom.setText(start.getName());
				}
				break;
			case Constant.RequestCode.STOP_TO:
				if (resultCode == Activity.RESULT_OK) {
					destination = data.getParcelableExtra(Constant.Bundle.KEY_STOP_OBJ);
					binding.textInputTo.setText(destination.getName());
				}
				break;
			default:
				super.onActivityResult(requestCode, resultCode, data);
				break;
		}
	}
}
