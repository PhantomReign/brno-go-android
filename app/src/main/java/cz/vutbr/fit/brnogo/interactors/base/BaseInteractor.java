package cz.vutbr.fit.brnogo.interactors.base;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.schedulers.Schedulers;

public abstract class BaseInteractor {
	protected Disposable subscription = DisposableHelper.DISPOSED;

	protected Scheduler getWorkScheduler() {
		return Schedulers.io();
	}

	protected Scheduler getResultScheduler() {
		return AndroidSchedulers.mainThread();
	}

	public void unsubscribe() {
		if (!subscription.isDisposed()) {
			subscription.dispose();
		}
	}
}
