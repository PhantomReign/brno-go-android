package cz.vutbr.fit.brnogo.interactors;

import java.util.List;

import javax.inject.Inject;

import cz.vutbr.fit.brnogo.data.model.store.FavoriteStop;
import cz.vutbr.fit.brnogo.data.store.FavoriteStopStore;
import cz.vutbr.fit.brnogo.injection.annotation.scope.PerScreen;
import cz.vutbr.fit.brnogo.interactors.base.BaseFlowableInteractor;
import io.reactivex.Flowable;

@PerScreen
public class GetFavoriteStopInteractor extends BaseFlowableInteractor<List<FavoriteStop>> {

	@Inject FavoriteStopStore favoriteStopStore;

	@Inject
	public GetFavoriteStopInteractor() {
	}

	@Override
	protected Flowable<List<FavoriteStop>> buildFlowable() {
		return favoriteStopStore.getFavoriteStops().onBackpressureLatest()
				.publish()
				.autoConnect();
	}
}
