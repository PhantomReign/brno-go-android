package cz.vutbr.fit.brnogo.ui.main.departures;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import java.util.Calendar;

import javax.inject.Inject;

import cz.vutbr.fit.brnogo.R;
import cz.vutbr.fit.brnogo.data.model.response.Stop;
import cz.vutbr.fit.brnogo.databinding.FragmentDeparturesBinding;
import cz.vutbr.fit.brnogo.tools.constant.Constant;
import cz.vutbr.fit.brnogo.tools.datetime.DateTimeConverter;
import cz.vutbr.fit.brnogo.ui.base.BaseFragment;
import cz.vutbr.fit.brnogo.ui.departures.DeparturesActivity;
import cz.vutbr.fit.brnogo.ui.main.routes.dialog.time.TransferTimePickerDialog;
import cz.vutbr.fit.brnogo.ui.main.routes.dialog.transfers.TransfersPickerDialog;
import cz.vutbr.fit.brnogo.ui.stop.StopSearchActivity;

public class DeparturesFragment extends BaseFragment<DeparturesViewModel, FragmentDeparturesBinding> implements DeparturesView {

	@Inject DeparturesViewModelFactory viewModelFactory;

	private Stop startStop = null;

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
		super.onViewCreated(view, savedInstanceState);
	}

	@Override
	public void onFindDeparturesClick() {
		if (startStop != null) {
			startActivity(DeparturesActivity.getStartIntent(getContext(), startStop));
		} else {
			Toast.makeText(getContext(), getResources().getString(R.string.select_stop), Toast.LENGTH_SHORT).show();
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
					startStop = (data.getParcelableExtra(Constant.Bundle.KEY_STOP_OBJ));
					binding.textInputFrom.setText(startStop.getName());
				}
				break;
			default:
				super.onActivityResult(requestCode, resultCode, data);
				break;
		}
	}
}
