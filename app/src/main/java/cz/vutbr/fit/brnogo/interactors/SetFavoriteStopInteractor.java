package cz.vutbr.fit.brnogo.interactors;

import javax.inject.Inject;

import cz.vutbr.fit.brnogo.data.model.store.FavoriteStop;
import cz.vutbr.fit.brnogo.data.store.FavoriteStopStore;
import cz.vutbr.fit.brnogo.injection.annotation.scope.PerScreen;
import cz.vutbr.fit.brnogo.interactors.base.BaseCompletableInteractor;
import io.reactivex.Completable;

/**
 * Class used to set or unset favorite stop.
 */

@PerScreen
public class SetFavoriteStopInteractor extends BaseCompletableInteractor {

	@Inject FavoriteStopStore favoriteStopStore;

	private FavoriteStop stop;

	@Inject
	public SetFavoriteStopInteractor() {
	}

	public SetFavoriteStopInteractor init(FavoriteStop stop) {
		this.stop = stop;

		return this;
	}

	@Override
	protected Completable buildCompletable() {
		return Completable.fromAction(() -> {
			if (!stop.getFavorite()) {
				favoriteStopStore.setFavoriteStopAsFavorite(stop);
			} else {
				favoriteStopStore.unsetFavoriteStopAsFavorite(stop);
			}
		});
	}
}
