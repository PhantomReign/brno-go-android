package cz.vutbr.fit.brnogo.interactors.base;

import android.support.annotation.NonNull;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import timber.log.Timber;

/**
 * Base Abstract Class representing structure of Flowable Interactor.
 */

public abstract class BaseFlowableInteractor<T> extends BaseInteractor {

	protected abstract Flowable<T> buildFlowable();

	public void execute(@NonNull Consumer<T> onNext) {
		execute(onNext, Timber::e);
	}

	public void execute(@NonNull Consumer<T> onNext, @NonNull final Consumer<Throwable> onError) {
		unsubscribe();
		subscription = buildFlowable().compose(applySchedulers()).subscribe(onNext, throwable -> {
			Timber.e(throwable);
			onError.accept(throwable);
		});
	}

	public void execute(@NonNull Action onComplete, @NonNull final Consumer<Throwable> onError) {
		unsubscribe();
		subscription = buildFlowable().compose(applySchedulers()).subscribe(t -> {
		}, throwable -> {
			Timber.e(throwable);
			onError.accept(throwable);
		}, onComplete);
	}

	protected FlowableTransformer<T, T> applySchedulers() {
		return upstream -> upstream.subscribeOn(getWorkScheduler()).observeOn(getResultScheduler());
	}
}
