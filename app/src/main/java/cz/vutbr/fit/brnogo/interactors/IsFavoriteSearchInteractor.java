package cz.vutbr.fit.brnogo.interactors;

import android.content.Context;

import java.util.ArrayList;

import javax.inject.Inject;

import cz.vutbr.fit.brnogo.data.model.store.Search;
import cz.vutbr.fit.brnogo.data.persistance.Persistence;
import cz.vutbr.fit.brnogo.injection.annotation.qualifier.ApplicationContext;
import cz.vutbr.fit.brnogo.injection.annotation.scope.PerScreen;
import cz.vutbr.fit.brnogo.interactors.base.BaseSingleInteractor;
import cz.vutbr.fit.brnogo.tools.constant.Constant;
import io.reactivex.Single;

@PerScreen
public class IsFavoriteSearchInteractor extends BaseSingleInteractor<Boolean> {

	@Inject Persistence persistence;
	@Inject @ApplicationContext Context context;

	private Search search;

	@Inject
	public IsFavoriteSearchInteractor() {
	}

	public IsFavoriteSearchInteractor init(Search search) {
		this.search = search;
		return this;
	}

	@Override
	protected Single<Boolean> buildSingle() {
		return Single.fromCallable(() -> {
			ArrayList<String> list = persistence.get(Constant.Persistence.FAVORITE_ROUTE_KEYS, new ArrayList<String>());
			if (list.contains(search.getId())) {
				search.setFavorite(true);
				return true;
			} else {
				search.setFavorite(false);
				return false;
			}
		});
	}
}
