package cz.vutbr.fit.brnogo.interactors.base;

import android.support.annotation.NonNull;

import io.reactivex.Completable;
import io.reactivex.CompletableTransformer;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import timber.log.Timber;

/**
 * Base Abstract Class representing structure of Completable Interactor.
 */

public abstract class BaseCompletableInteractor extends BaseInteractor {

	protected abstract Completable buildCompletable();

	public void execute(@NonNull Action onComplete) {
		execute(onComplete, Timber::e);
	}

	public void execute(@NonNull Action onComplete, @NonNull final Consumer<Throwable> onError) {
		unsubscribe();

		subscription = buildCompletable().compose(applySchedulers()).subscribe(onComplete, throwable -> {
			Timber.e(throwable);
			onError.accept(throwable);
		});
	}

	protected CompletableTransformer applySchedulers() {
		return upstream -> upstream.subscribeOn(getWorkScheduler()).observeOn(getResultScheduler());
	}
}
