package cz.vutbr.fit.brnogo.interactors;

import java.util.List;

import javax.inject.Inject;
import cz.vutbr.fit.brnogo.data.model.store.Search;
import cz.vutbr.fit.brnogo.data.store.SearchStore;
import cz.vutbr.fit.brnogo.injection.annotation.scope.PerScreen;
import cz.vutbr.fit.brnogo.interactors.base.BaseFlowableInteractor;
import io.reactivex.Flowable;

@PerScreen
public class GetFavoriteRouteSearchInteractor extends BaseFlowableInteractor<List<Search>> {

	@Inject SearchStore searchStore;

	@Inject
	public GetFavoriteRouteSearchInteractor() {
	}

	@Override
	protected Flowable<List<Search>> buildFlowable() {
		return searchStore.getSearches().onBackpressureLatest()
				.publish()
				.autoConnect();
	}
}
