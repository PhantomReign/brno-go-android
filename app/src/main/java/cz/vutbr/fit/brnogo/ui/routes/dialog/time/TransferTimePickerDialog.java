package cz.vutbr.fit.brnogo.ui.routes.dialog.time;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;

import javax.inject.Inject;

import cz.vutbr.fit.brnogo.R;
import cz.vutbr.fit.brnogo.databinding.DialogTransferTimePickerBinding;
import cz.vutbr.fit.brnogo.tools.constant.Constant;
import cz.vutbr.fit.brnogo.ui.base.BaseDialogFragment;

public class TransferTimePickerDialog extends BaseDialogFragment<TransferTimePickerViewModel, DialogTransferTimePickerBinding> implements TransferTimePickerView {

	@Inject TransferTimePickerViewModelFactory viewModelFactory;

	private TransferTimeListener transferTimeListener;

	public static TransferTimePickerDialog newInstance() {
		return new TransferTimePickerDialog();
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		try {
			transferTimeListener = (TransferTimeListener) getTargetFragment();
		} catch (ClassCastException e) {
			throw new ClassCastException("Activity/fragment must implement TransfersListener");
		}
	}

	@Override
	protected TransferTimePickerViewModel createViewModel() {
		return ViewModelProviders.of(this, viewModelFactory).get(TransferTimePickerViewModel.class);
	}

	@Override
	protected DialogTransferTimePickerBinding inflateBindingLayout(LayoutInflater layoutInflater) {
		return DialogTransferTimePickerBinding.inflate(layoutInflater, null, false);
	}

	@Override
	protected Integer getDialogTitle() {
		return R.string.transfer_time;
	}

	@Override
	protected void addDialogButtons(AlertDialog.Builder builder) {
		builder.setPositiveButton(R.string.set, null);
		builder.setNegativeButton(R.string.cancel, null);
	}

	@Override
	public void onResume() {
		super.onResume();
		AlertDialog dialog = (AlertDialog) getDialog();

		binding.numberPicker.setMaxValue(Constant.TransferTimeDialog.MAX);
		binding.numberPicker.setMinValue(Constant.TransferTimeDialog.MIN);
		binding.numberPicker.setValue(Constant.TransferTimeDialog.DEFAULT);

		dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(view -> {
			transferTimeListener.onSetTransferTime(binding.numberPicker.getValue());
			dismiss();
		});

		dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(view -> dismiss());
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	public interface TransferTimeListener {
		void onSetTransferTime(int transferTime);
	}
}
