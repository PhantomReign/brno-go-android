package cz.vutbr.fit.brnogo.interactors;

import android.content.Context;

import java.util.ArrayList;

import javax.inject.Inject;

import cz.vutbr.fit.brnogo.data.model.store.FavoriteStop;
import cz.vutbr.fit.brnogo.data.persistance.Persistence;
import cz.vutbr.fit.brnogo.injection.annotation.qualifier.ApplicationContext;
import cz.vutbr.fit.brnogo.injection.annotation.scope.PerScreen;
import cz.vutbr.fit.brnogo.interactors.base.BaseSingleInteractor;
import cz.vutbr.fit.brnogo.tools.constant.Constant;
import io.reactivex.Single;

@PerScreen
public class IsFavoriteStopInteractor extends BaseSingleInteractor<Boolean> {

	@Inject Persistence persistence;
	@Inject @ApplicationContext Context context;

	private FavoriteStop stop;

	@Inject
	public IsFavoriteStopInteractor() {
	}

	public IsFavoriteStopInteractor init(FavoriteStop stop) {
		this.stop = stop;
		return this;
	}

	@Override
	protected Single<Boolean> buildSingle() {
		return Single.fromCallable(() -> {
			ArrayList<String> list = persistence.get(Constant.Persistence.FAVORITE_STOP_KEYS, new ArrayList<String>());
			if (list.contains(stop.getId())) {
				stop.setFavorite(true);
				return true;
			} else {
				stop.setFavorite(false);
				return false;
			}
		});
	}
}
