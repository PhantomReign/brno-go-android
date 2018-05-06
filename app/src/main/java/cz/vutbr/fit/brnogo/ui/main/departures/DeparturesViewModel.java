package cz.vutbr.fit.brnogo.ui.main.departures;

import android.arch.lifecycle.MutableLiveData;

import java.util.List;

import javax.inject.Inject;

import cz.vutbr.fit.brnogo.data.model.response.Stop;
import cz.vutbr.fit.brnogo.data.model.store.FavoriteStop;
import cz.vutbr.fit.brnogo.data.model.store.Search;
import cz.vutbr.fit.brnogo.injection.annotation.scope.PerScreen;
import cz.vutbr.fit.brnogo.interactors.GetFavoriteRouteSearchInteractor;
import cz.vutbr.fit.brnogo.interactors.GetFavoriteStopInteractor;
import cz.vutbr.fit.brnogo.interactors.SetFavoriteRouteSearchInteractor;
import cz.vutbr.fit.brnogo.interactors.SetFavoriteStopInteractor;
import cz.vutbr.fit.brnogo.ui.base.BaseViewModel;
import timber.log.Timber;

@PerScreen
public class DeparturesViewModel extends BaseViewModel {

	private GetFavoriteStopInteractor getFavoriteStopInteractor;
	private SetFavoriteStopInteractor setFavoriteStopInteractor;

	private FavoriteStop startStop;

	private MutableLiveData<List<FavoriteStop>> items = new MutableLiveData<>();

	@Inject
	public DeparturesViewModel(GetFavoriteStopInteractor getFavoriteStopInteractor,
							   SetFavoriteStopInteractor setFavoriteStopInteractor) {
		this.getFavoriteStopInteractor = getFavoriteStopInteractor;
		this.setFavoriteStopInteractor = setFavoriteStopInteractor;
		this.startStop = null;
		loadData();
	}

	private void loadData() {
		getFavoriteStopInteractor.execute((favoriteStops) -> items.setValue(favoriteStops), Timber::e);
	}

	public void setFavoriteStop(FavoriteStop stop) {
		setFavoriteStopInteractor.init(stop)
				.execute(() -> {
				});
	}

	public MutableLiveData<List<FavoriteStop>> getItems() {
		return items;
	}

	@Override
	protected void onCleared() {
		getFavoriteStopInteractor.unsubscribe();
		setFavoriteStopInteractor.unsubscribe();
		super.onCleared();
	}

	public FavoriteStop getStartStop() {
		return startStop;
	}

	public void setStartStop(FavoriteStop startStop) {
		this.startStop = startStop;
	}
}
