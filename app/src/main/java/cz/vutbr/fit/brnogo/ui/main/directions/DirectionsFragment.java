package cz.vutbr.fit.brnogo.ui.main.directions;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import org.threeten.bp.LocalDateTime;

import java.util.Calendar;

import javax.inject.Inject;

import cz.vutbr.fit.brnogo.R;
import cz.vutbr.fit.brnogo.data.model.response.Route;
import cz.vutbr.fit.brnogo.data.model.response.Stop;
import cz.vutbr.fit.brnogo.databinding.FragmentDirectionsBinding;
import cz.vutbr.fit.brnogo.tools.constant.Constant;
import cz.vutbr.fit.brnogo.tools.datetime.DateTimeConverter;
import cz.vutbr.fit.brnogo.ui.base.BaseFragment;
import cz.vutbr.fit.brnogo.ui.directions.DirectionsActivity;
import cz.vutbr.fit.brnogo.ui.stop.StopSearchActivity;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class DirectionsFragment
		extends BaseFragment<DirectionsViewModel, FragmentDirectionsBinding>
		implements
		DirectionsView,
		DatePickerDialog.OnDateSetListener,
		TimePickerDialog.OnTimeSetListener {

	@Inject DirectionsViewModelFactory viewModelFactory;
	@Inject DirectionsAdapter directionsAdapter;

	public static DirectionsFragment newInstance() {
		return new DirectionsFragment();
	}

	@Override
	protected DirectionsViewModel createViewModel() {
		return ViewModelProviders.of(this, viewModelFactory).get(DirectionsViewModel.class);
	}

	@Override
	protected FragmentDirectionsBinding inflateBindingLayout(LayoutInflater layoutInflater) {
		return FragmentDirectionsBinding.inflate(layoutInflater);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		binding.directionsRecyclerView.setAdapter(directionsAdapter);
		binding.directionsRecyclerView.setItemAnimator(new SlideInUpAnimator(new LinearInterpolator()));

		viewModel.getItems().observe(this, routes ->
				directionsAdapter.updateData(routes));
				binding.savedDirections.setVisibility(getSavedTextVisibility());
	}

	private int getSavedTextVisibility() {
		return directionsAdapter.getItemCount() > 0 ? View.VISIBLE : View.INVISIBLE;
	}

	private void setCorrectEditTexts() {
		if (viewModel.getSearchRequest().getStartStop() == null) {
			binding.directionsTextInputFrom.setText("");
		} else {
			binding.directionsTextInputFrom.setText(viewModel.getSearchRequest().getStartStop().getName());
		}

		if (viewModel.getSearchRequest().getDestinationStop() == null) {
			binding.directionsTextInputTo.setText("");
		} else {
			binding.directionsTextInputTo.setText(viewModel.getSearchRequest().getDestinationStop().getName());
		}
	}

	@Override
	public void onResume() {
		setCorrectEditTexts();
		super.onResume();
	}

	@Override
	public void onGetDirectionsClick() {
		if (viewModel.getSearchRequest() != null && viewModel.getSearchRequest().getStartStop() != null && viewModel.getSearchRequest().getDestinationStop() != null) {
			viewModel.getSearchRequest().setId(viewModel.getSearchRequest().getStartStop().getId() + viewModel.getSearchRequest().getDestinationStop().getId());
			viewModel.getSearchRequest().setDateTime(DateTimeConverter.zonedDateTimeToEpochSec(viewModel.getSearchRequest().getDate(), viewModel.getSearchRequest().getTime()));

			startActivity(DirectionsActivity.getStartIntent(getContext(), viewModel.getSearchRequest(), null));
		} else {
			Toast.makeText(getContext(), getString(R.string.select_stops), Toast.LENGTH_SHORT).show();
		}

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
		Stop tmpStop = viewModel.getSearchRequest().getStartStop();
		viewModel.getSearchRequest().setStartStop(viewModel.getSearchRequest().getDestinationStop());
		viewModel.getSearchRequest().setDestinationStop(tmpStop);

		setCorrectEditTexts();
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
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
			case Constant.RequestCode.STOP_FROM:
				if (resultCode == Activity.RESULT_OK) {
					viewModel.getSearchRequest().setStartStop(data.getParcelableExtra(Constant.Bundle.KEY_STOP_OBJ));
					binding.directionsTextInputFrom.setText(((Stop) data.getParcelableExtra(Constant.Bundle.KEY_STOP_OBJ)).getName());
				}
				break;
			case Constant.RequestCode.STOP_TO:
				if (resultCode == Activity.RESULT_OK) {
					viewModel.getSearchRequest().setDestinationStop(data.getParcelableExtra(Constant.Bundle.KEY_STOP_OBJ));
					binding.directionsTextInputTo.setText(((Stop) data.getParcelableExtra(Constant.Bundle.KEY_STOP_OBJ)).getName());
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
				.localDate(LocalDateTime.of(y, m + 1, d, 0, 0).toString())
				.format(Constant.Formatter.DAY_MONTH_YEAR);
		binding.buttonDate.setText(date);
		viewModel.getSearchRequest().setDate(date);
	}

	@Override
	public void onTimeSet(TimePicker timePicker, int h, int m) {
		String time = DateTimeConverter
				.localDate(LocalDateTime.of(1, 1, 1, h, m).toString())
				.format(Constant.Formatter.HOUR_MINUTE);
		binding.buttonTime.setText(time);
		viewModel.getSearchRequest().setTime(time);
	}

	@Override
	public void onSavedItemClick(Route route) {
		startActivity(DirectionsActivity.getStartIntent(getContext(), viewModel.getSearchRequest(), route));
	}

	@Override
	public void onSavedButtonClick(Route route) {
		viewModel.setSavedRoute(route);
	}
}

