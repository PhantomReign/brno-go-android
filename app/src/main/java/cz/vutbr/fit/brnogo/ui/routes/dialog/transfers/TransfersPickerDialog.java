package cz.vutbr.fit.brnogo.ui.routes.dialog.transfers;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;

import javax.inject.Inject;

import cz.vutbr.fit.brnogo.R;
import cz.vutbr.fit.brnogo.databinding.DialogTransfersPickerBinding;
import cz.vutbr.fit.brnogo.tools.constant.Constant;
import cz.vutbr.fit.brnogo.ui.base.BaseDialogFragment;

public class TransfersPickerDialog extends BaseDialogFragment<TransfersPickerViewModel, DialogTransfersPickerBinding> implements TransfersPickerView {

	@Inject TransfersPickerViewModelFactory viewModelFactory;

	private TransfersListener transfersListener;

	public static TransfersPickerDialog newInstance() {
		return new TransfersPickerDialog();
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		try {
			transfersListener = (TransfersListener) getTargetFragment();
		} catch (ClassCastException e) {
			throw new ClassCastException("Activity/fragment must implement TransfersListener");
		}
	}

	@Override
	protected TransfersPickerViewModel createViewModel() {
		return ViewModelProviders.of(this, viewModelFactory).get(TransfersPickerViewModel.class);
	}

	@Override
	protected DialogTransfersPickerBinding inflateBindingLayout(LayoutInflater layoutInflater) {
		return DialogTransfersPickerBinding.inflate(layoutInflater, null, false);
	}

	@Override
	protected Integer getDialogTitle() {
		return R.string.transfers;
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

		binding.numberPicker.setMaxValue(Constant.TransfersDialog.MAX);
		binding.numberPicker.setMinValue(Constant.TransfersDialog.MIN);
		binding.numberPicker.setValue(Constant.TransfersDialog.DEFAULT);

		dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(view -> {
			transfersListener.onSetTransfers(binding.numberPicker.getValue());
			dismiss();
		});

		dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(view -> dismiss());
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	public interface TransfersListener {
		void onSetTransfers(int transfers);
	}
}
