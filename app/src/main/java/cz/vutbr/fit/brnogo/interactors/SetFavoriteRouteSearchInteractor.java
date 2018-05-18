package cz.vutbr.fit.brnogo.interactors;

import javax.inject.Inject;

import cz.vutbr.fit.brnogo.data.model.store.Search;
import cz.vutbr.fit.brnogo.data.store.SearchStore;
import cz.vutbr.fit.brnogo.injection.annotation.scope.PerScreen;
import cz.vutbr.fit.brnogo.interactors.base.BaseCompletableInteractor;
import io.reactivex.Completable;

/**
 * Class used to set or unset favorite search.
 */

@PerScreen
public class SetFavoriteRouteSearchInteractor extends BaseCompletableInteractor {

	@Inject SearchStore searchStore;

	private Search search;

	@Inject
	public SetFavoriteRouteSearchInteractor() {
	}

	public SetFavoriteRouteSearchInteractor init(Search search) {
		this.search = search;

		return this;
	}

	@Override
	protected Completable buildCompletable() {
		return Completable.fromAction(() -> {
			if (!search.getFavorite()) {
				searchStore.setSearchAsFavorite(search);
			} else {
				searchStore.unsetSearchAsFavorite(search);
			}
		});
	}
}
