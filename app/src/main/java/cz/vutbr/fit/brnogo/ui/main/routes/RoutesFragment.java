package cz.vutbr.fit.brnogo.ui.main.routes;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.constraint.ConstraintSet;
import android.support.transition.ChangeBounds;
import android.support.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import org.threeten.bp.LocalDateTime;

import java.util.Calendar;

import javax.inject.Inject;

import cz.vutbr.fit.brnogo.R;
import cz.vutbr.fit.brnogo.data.model.response.Stop;
import cz.vutbr.fit.brnogo.data.model.store.Search;
import cz.vutbr.fit.brnogo.databinding.FragmentRoutesBinding;
import cz.vutbr.fit.brnogo.tools.constant.Constant;
import cz.vutbr.fit.brnogo.tools.datetime.DateTimeConverter;
import cz.vutbr.fit.brnogo.ui.base.BaseFragment;
import cz.vutbr.fit.brnogo.ui.main.routes.dialog.time.TransferTimePickerDialog;
import cz.vutbr.fit.brnogo.ui.main.routes.dialog.transfers.TransfersPickerDialog;
import cz.vutbr.fit.brnogo.ui.stop.StopSearchActivity;

public class RoutesFragment
		extends BaseFragment<RoutesViewModel, FragmentRoutesBinding>
		implements
		RoutesView,
		DatePickerDialog.OnDateSetListener,
		TimePickerDialog.OnTimeSetListener,
		TransfersPickerDialog.TransfersListener,
		TransferTimePickerDialog.TransferTimeListener {

	@Inject RoutesViewModelFactory viewModelFactory;

	private Search searchRequest;

	ConstraintSet setOne = new ConstraintSet();
	ConstraintSet setTwo = new ConstraintSet();

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
		searchRequest = new Search();
		setTwo.clone(this.getContext(), R.layout.fragment_routes_advanced);
		setOne.clone(binding.cardConstraintLayout);
	}

	@Override
	public void onFindRouteClick() {
		Toast.makeText(getContext(), "FIND", Toast.LENGTH_SHORT).show();

	}

	private void animate(ConstraintSet set) {
		set.applyTo(binding.cardConstraintLayout);
		ChangeBounds changeBounds = new ChangeBounds();
		changeBounds.setInterpolator(new OvershootInterpolator());
		TransitionManager.beginDelayedTransition(binding.cardConstraintLayout, changeBounds);
	}

	private void defaultAdvancedSettings() {
		searchRequest.setDate(Constant.SearchRequest.DEFAULT_DATE);
		searchRequest.setTime(Constant.SearchRequest.DEFAULT_TIME);
		searchRequest.setTransfers(Constant.TransfersDialog.DEFAULT);
		searchRequest.setTransferTime(Constant.TransferTimeDialog.DEFAULT);

		binding.buttonTime.setText(R.string.now);
		binding.buttonDate.setText(R.string.today);
		binding.buttonTransfers.setText(R.string.infinity);
		binding.buttonTransferTime.setText(getString(R.string.minutes, Constant.TransferTimeDialog.DEFAULT));
	}

	@Override
	public void onAdvancedClick() {
		animate(setTwo);
	}

	@Override
	public void onCloseClick() {
		animate(setOne);
		defaultAdvancedSettings();
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
		Stop tmpStop = searchRequest.getStartStop();
		searchRequest.setStartStop(searchRequest.getDestinationStop());
		searchRequest.setDestinationStop(tmpStop);

		if (searchRequest.getStartStop() == null) {
			binding.textInputFrom.setText("");
		} else {
			binding.textInputFrom.setText(searchRequest.getStartStop().getName());
		}

		if (searchRequest.getDestinationStop() == null) {
			binding.textInputTo.setText("");
		} else {
			binding.textInputTo.setText(searchRequest.getDestinationStop().getName());
		}
	}

	@Override
	public void onTimeButtonClick() {
		Calendar calendar = DateTimeConverter.getCalendar();
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		if (getContext() != null) {
			new TimePickerDialog(getContext(), this, hour, minute, true).show();
		}
	}

	@Override
	public void onDateButtonClick() {
		Calendar calendar = DateTimeConverter.getCalendar();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		if (getContext() != null) {
			new DatePickerDialog(getContext(), this, year, month, day).show();
		}
	}

	@Override
	public void onTransfersButtonClick() {
		TransfersPickerDialog dialog =  TransfersPickerDialog.newInstance();
		dialog.setTargetFragment(this, Constant.RequestCode.DIALOG_TRANSFERS);
		dialog.show(getFragmentManager(), Constant.Tag.DIALOG_TRANSFERS);
	}

	@Override
	public void onTransferTimeButtonClick() {
		TransferTimePickerDialog dialog =  TransferTimePickerDialog.newInstance();
		dialog.setTargetFragment(this, Constant.RequestCode.DIALOG_TRANSFER_TIME);
		dialog.show(getFragmentManager(), Constant.Tag.DIALOG_TRANSFER_TIME);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
			case Constant.RequestCode.STOP_FROM:
				if (resultCode == Activity.RESULT_OK) {
					searchRequest.setStartStop(data.getParcelableExtra(Constant.Bundle.KEY_STOP_OBJ));
					binding.textInputFrom.setText(((Stop) data.getParcelableExtra(Constant.Bundle.KEY_STOP_OBJ)).getName());
				}
				break;
			case Constant.RequestCode.STOP_TO:
				if (resultCode == Activity.RESULT_OK) {
					searchRequest.setDestinationStop(data.getParcelableExtra(Constant.Bundle.KEY_STOP_OBJ));
					binding.textInputTo.setText(((Stop) data.getParcelableExtra(Constant.Bundle.KEY_STOP_OBJ)).getName());
				}
				break;
			default:
				super.onActivityResult(requestCode, resultCode, data);
				break;
		}
	}

	@Override
	public void onDateSet(DatePicker datePicker, int y, int m, int d) {
		String date = DateTimeConverter
				.localDate(LocalDateTime.of(y, m, d, 0, 0).toString())
				.format(Constant.Formatter.DAY_MONTH_YEAR);
		binding.buttonDate.setText(date);
		searchRequest.setDate(date);
	}

	@Override
	public void onTimeSet(TimePicker timePicker, int h, int m) {
		String time = DateTimeConverter
				.localDate(LocalDateTime.of(1, 1, 1, h, m).toString())
				.format(Constant.Formatter.HOUR_MINUTE);
		binding.buttonTime.setText(time);
		searchRequest.setTime(time);
	}

	@Override
	public void onSetTransfers(int transfers) {
		binding.buttonTransfers.setText(String.valueOf(transfers));
		searchRequest.setTransfers(transfers);
	}

	@Override
	public void onSetTransferTime(int transferTime) {
		binding.buttonTransferTime.setText(getString(R.string.minutes, transferTime));
		searchRequest.setTransferTime(transferTime);
	}
}
