package cz.vutbr.fit.brnogo.ui.route;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableBoolean;

import java.util.List;

import javax.inject.Inject;

import cz.vutbr.fit.brnogo.data.model.recyclerview.RouteItem;
import cz.vutbr.fit.brnogo.data.model.store.Search;
import cz.vutbr.fit.brnogo.injection.annotation.scope.PerScreen;
import cz.vutbr.fit.brnogo.interactors.GetRoutesInteractor;
import cz.vutbr.fit.brnogo.interactors.IsFavoriteSearchInteractor;
import cz.vutbr.fit.brnogo.interactors.SetFavoriteRouteSearchInteractor;
import cz.vutbr.fit.brnogo.tools.livedata.SingleEventLiveData;
import cz.vutbr.fit.brnogo.ui.base.BaseViewModel;
import timber.log.Timber;

@PerScreen
public class RoutesViewModel extends BaseViewModel {

	@Inject
	Search search;

	private GetRoutesInteractor getRoutesInteractor;
	private SetFavoriteRouteSearchInteractor setFavoriteRouteSearchInteractor;
	private IsFavoriteSearchInteractor isFavoriteSearchInteractor;

	public ObservableBoolean loadingVisibility = new ObservableBoolean(false);
	public ObservableBoolean offlineVisibility = new ObservableBoolean(false);
	public ObservableBoolean emptyViewVisibility = new ObservableBoolean(false);

	private MutableLiveData<List<RouteItem>> items = new MutableLiveData<>();
	public SingleEventLiveData<Boolean> setColor = new SingleEventLiveData<>();

	@Inject
	public RoutesViewModel(GetRoutesInteractor getRoutesInteractor,
						   SetFavoriteRouteSearchInteractor setFavoriteRouteSearchInteractor,
						   IsFavoriteSearchInteractor isFavoriteSearchInteractor) {
		this.getRoutesInteractor = getRoutesInteractor;
		this.setFavoriteRouteSearchInteractor = setFavoriteRouteSearchInteractor;
		this.isFavoriteSearchInteractor = isFavoriteSearchInteractor;
	}

	public void loadData() {
		loadingVisibility.set(true);
		offlineVisibility.set(false);

		getRoutesInteractor
				.init(Integer.parseInt(search.getStartStop().getId()), Integer.parseInt(search.getDestinationStop().getId()), search.getDateTime(), search.getTransferTime(), search.getTransfers())
				.execute(routes -> {
					loadingVisibility.set(false);
					items.setValue(routes);
					if (routes.isEmpty()) {
						emptyViewVisibility.set(true);
					} else {
						emptyViewVisibility.set(false);
					}
				}, throwable -> {
					Timber.e(throwable);
					loadingVisibility.set(false);
					emptyViewVisibility.set(false);
					offlineVisibility.set(true);
				});
	}

	public MutableLiveData<List<RouteItem>> getItems() {
		return items;
	}

	public void isFavorite() {
		isFavoriteSearchInteractor.init(search)
				.execute((favorite) -> {
					setColor.setValue(favorite);
				});
	}

	public void setFavoriteSearch() {
		setFavoriteRouteSearchInteractor.init(search)
				.execute(() -> {
					setColor.setValue(true);
				});
	}

	@Override
	protected void onCleared() {
		getRoutesInteractor.unsubscribe();
		setFavoriteRouteSearchInteractor.unsubscribe();
		isFavoriteSearchInteractor.unsubscribe();
		super.onCleared();
	}
}
