package cz.vutbr.fit.brnogo.ui.base;

import android.arch.lifecycle.ViewModel;
import android.databinding.Bindable;
import android.databinding.Observable;
import android.databinding.PropertyChangeRegistry;

public abstract class BaseViewModel extends ViewModel implements Observable {

	private transient PropertyChangeRegistry callbacks;

	@Override
	public void addOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
		synchronized (this) {
			if (callbacks == null) {
				callbacks = new PropertyChangeRegistry();
			}
		}
		callbacks.add(callback);
	}

	@Override
	public void removeOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
		synchronized (this) {
			if (callbacks == null) {
				return;
			}
		}
		callbacks.remove(callback);
	}

	/**
	 * Notifies listeners that all properties of this instance have changed.
	 */
	public void notifyChange() {
		synchronized (this) {
			if (callbacks == null) {
				return;
			}
		}
		callbacks.notifyCallbacks(this, 0, null);
	}

	/**
	 * Notifies listeners that a specific property has changed. The getter for the property
	 * that changes should be marked with {@link Bindable} to generate a field in
	 * <code>BR</code> to be used as <code>fieldId</code>.
	 *
	 * @param fieldId The generated BR id for the Bindable field.
	 */
	public void notifyPropertyChanged(int fieldId) {
		synchronized (this) {
			if (callbacks == null) {
				return;
			}
		}
		callbacks.notifyCallbacks(this, fieldId, null);
	}
}
