package cz.vutbr.fit.brnogo.ui.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;

import cz.vutbr.fit.brnogo.BR;
import cz.vutbr.fit.brnogo.R;
import dagger.android.support.AndroidSupportInjection;

/**
 * Base Class representing Dialog Fragment.
 */

public abstract class BaseDialogFragment<T extends BaseViewModel, B extends ViewDataBinding> extends DialogFragment {

	protected T viewModel;

	protected B binding;

	protected abstract T createViewModel();

	protected abstract B inflateBindingLayout(LayoutInflater layoutInflater);

	@StringRes
	@Nullable
	protected abstract Integer getDialogTitle();

	protected abstract void addDialogButtons(AlertDialog.Builder builder);

	@NonNull
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AndroidSupportInjection.inject(this);

		viewModel = createViewModel();

		binding = inflateBindingLayout(LayoutInflater.from(getContext()));
		binding.setVariable(BR.view, this);
		binding.setVariable(BR.viewModel, viewModel);

		AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.DialogStyle)
				.setView(binding.getRoot())
				.setCancelable(false);

		if (getDialogTitle() != null) {
			builder.setTitle(getDialogTitle());
		}

		addDialogButtons(builder);

		return builder.create();
	}

	@Override
	public void onDismiss(DialogInterface dialog) {
		super.onDismiss(dialog);
		Activity activity = getActivity();
		if (activity instanceof DialogInterface.OnDismissListener) {
			((DialogInterface.OnDismissListener) activity).onDismiss(dialog);
		}
	}
}
