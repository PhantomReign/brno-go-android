package cz.vutbr.fit.brnogo.ui.main.directions;

import android.arch.lifecycle.MutableLiveData;

import java.util.List;

import javax.inject.Inject;

import cz.vutbr.fit.brnogo.data.model.response.Route;
import cz.vutbr.fit.brnogo.data.model.store.Search;
import cz.vutbr.fit.brnogo.injection.annotation.scope.PerScreen;
import cz.vutbr.fit.brnogo.interactors.GetSavedRouteInteractor;
import cz.vutbr.fit.brnogo.interactors.SetSavedRouteInteractor;
import cz.vutbr.fit.brnogo.ui.base.BaseViewModel;
import timber.log.Timber;

@PerScreen
public class DirectionsViewModel extends BaseViewModel {

	private GetSavedRouteInteractor getSavedRouteInteractor;
	private SetSavedRouteInteractor setSavedRouteInteractor;

	private Search searchRequest;

	private MutableLiveData<List<Route>> items = new MutableLiveData<>();

	@Inject
	public DirectionsViewModel(GetSavedRouteInteractor getSavedRouteInteractor,
							   SetSavedRouteInteractor setSavedRouteInteractor) {
		this.getSavedRouteInteractor = getSavedRouteInteractor;
		this.setSavedRouteInteractor = setSavedRouteInteractor;
		searchRequest = new Search();
		loadData();
	}

	private void loadData() {
		getSavedRouteInteractor.execute((routes) -> items.setValue(routes), Timber::e);
	}

	public void setSavedRoute(Route route) {
		setSavedRouteInteractor.init(route)
				.execute(() -> {
				});
	}

	public MutableLiveData<List<Route>> getItems() {
		return items;
	}

	public Search getSearchRequest() {
		return searchRequest;
	}

	@Override
	protected void onCleared() {
		setSavedRouteInteractor.unsubscribe();
		getSavedRouteInteractor.unsubscribe();
		super.onCleared();
	}
}
