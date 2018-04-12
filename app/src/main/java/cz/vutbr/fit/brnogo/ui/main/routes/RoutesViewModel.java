package cz.vutbr.fit.brnogo.ui.main.routes;

import android.arch.lifecycle.MutableLiveData;

import java.util.List;

import javax.inject.Inject;

import cz.vutbr.fit.brnogo.data.model.store.Search;
import cz.vutbr.fit.brnogo.injection.annotation.scope.PerScreen;
import cz.vutbr.fit.brnogo.interactors.GetFavoriteRouteSearchInteractor;
import cz.vutbr.fit.brnogo.interactors.SetFavoriteRouteSearchInteractor;
import cz.vutbr.fit.brnogo.ui.base.BaseViewModel;
import timber.log.Timber;

@PerScreen
public class RoutesViewModel extends BaseViewModel {

	private GetFavoriteRouteSearchInteractor getFavoriteRouteSearchInteractor;
	private SetFavoriteRouteSearchInteractor setFavoriteRouteSearchInteractor;

	private Search searchRequest;

	private MutableLiveData<List<Search>> items = new MutableLiveData<>();

	@Inject
	public RoutesViewModel(GetFavoriteRouteSearchInteractor getFavoriteRouteSearchInteractor,
						   SetFavoriteRouteSearchInteractor setFavoriteRouteSearchInteractor) {
		this.getFavoriteRouteSearchInteractor = getFavoriteRouteSearchInteractor;
		this.setFavoriteRouteSearchInteractor = setFavoriteRouteSearchInteractor;
		searchRequest = new Search();
		loadData();
	}

	private void loadData() {
		getFavoriteRouteSearchInteractor.execute((searches) -> items.setValue(searches), Timber::e);
	}

	public void setFavoriteSearch(Search search) {
		setFavoriteRouteSearchInteractor.init(search)
				.execute(() -> {
				});
	}

	public MutableLiveData<List<Search>> getItems() {
		return items;
	}

	public Search getSearchRequest() {
		return searchRequest;
	}

	@Override
	protected void onCleared() {
		setFavoriteRouteSearchInteractor.unsubscribe();
		getFavoriteRouteSearchInteractor.unsubscribe();
		super.onCleared();
	}
}
