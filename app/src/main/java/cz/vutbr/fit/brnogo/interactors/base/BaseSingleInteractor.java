package cz.vutbr.fit.brnogo.interactors.base;

import android.support.annotation.NonNull;

import io.reactivex.Single;
import io.reactivex.SingleTransformer;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import timber.log.Timber;

/**
 * Base Abstract Class representing structure of Single Interactor.
 */

public abstract class BaseSingleInteractor<T> extends BaseInteractor {

	protected abstract Single<T> buildSingle();

	public void execute(@NonNull Consumer<T> onSuccess) {
		execute(onSuccess, Timber::e);
	}

	public void execute(@NonNull Consumer<T> onSuccess, @NonNull final Consumer<Throwable> onError) {
		unsubscribe();
		subscription = buildSingle().compose(applySchedulers()).subscribe(onSuccess, throwable -> {
			Timber.e(throwable);
			onError.accept(throwable);
		});
	}

	public void execute(@NonNull Action onComplete, @NonNull final Consumer<Throwable> onError) {
		unsubscribe();

		subscription = buildSingle().compose(applySchedulers()).subscribe(t -> onComplete.run(), throwable -> {
			Timber.e(throwable);
			onError.accept(throwable);
		});
	}

	protected SingleTransformer<T, T> applySchedulers() {
		return upstream -> upstream.subscribeOn(getWorkScheduler()).observeOn(getResultScheduler());
	}
}
