package cz.vutbr.fit.brnogo.ui.departures;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.Bindable;
import android.databinding.ObservableBoolean;

import java.util.List;

import javax.inject.Inject;

import cz.vutbr.fit.brnogo.data.model.recyclerview.DepartureItem;
import cz.vutbr.fit.brnogo.data.model.response.CurrentDeparture;
import cz.vutbr.fit.brnogo.data.model.response.Stop;
import cz.vutbr.fit.brnogo.data.model.store.FavoriteStop;
import cz.vutbr.fit.brnogo.injection.annotation.scope.PerScreen;
import cz.vutbr.fit.brnogo.interactors.GetDeparturesInteractor;
import cz.vutbr.fit.brnogo.interactors.GetFavoriteStopInteractor;
import cz.vutbr.fit.brnogo.interactors.IsFavoriteSearchInteractor;
import cz.vutbr.fit.brnogo.interactors.IsFavoriteStopInteractor;
import cz.vutbr.fit.brnogo.interactors.SetFavoriteStopInteractor;
import cz.vutbr.fit.brnogo.tools.livedata.SingleEventLiveData;
import cz.vutbr.fit.brnogo.ui.base.BaseViewModel;
import timber.log.Timber;

@PerScreen
public class DeparturesViewModel extends BaseViewModel {

	@Inject
	FavoriteStop stop;

	private GetDeparturesInteractor getDeparturesInteractor;
	private GetFavoriteStopInteractor getFavoriteStopInteractor;
	private SetFavoriteStopInteractor setFavoriteStopInteractor;
	private IsFavoriteStopInteractor isFavoriteStopInteractor;

	public ObservableBoolean loadingVisibility = new ObservableBoolean(false);
	public ObservableBoolean offlineVisibility = new ObservableBoolean(false);
	public ObservableBoolean emptyViewVisibility = new ObservableBoolean(false);

	public SingleEventLiveData<Boolean> setColor = new SingleEventLiveData<>();

	private MutableLiveData<List<DepartureItem>> items = new MutableLiveData<>();

	@Inject
	public DeparturesViewModel(GetDeparturesInteractor getDeparturesInteractor,
							   GetFavoriteStopInteractor getFavoriteStopInteractor,
							   SetFavoriteStopInteractor setFavoriteStopInteractor,
							   IsFavoriteStopInteractor isFavoriteStopInteractor) {
		this.getDeparturesInteractor = getDeparturesInteractor;
		this.getFavoriteStopInteractor = getFavoriteStopInteractor;
		this.setFavoriteStopInteractor = setFavoriteStopInteractor;
		this.isFavoriteStopInteractor = isFavoriteStopInteractor;
	}

	public void loadData() {
		loadingVisibility.set(true);
		offlineVisibility.set(false);

		getDeparturesInteractor.init(Integer.valueOf(stop.getId()))
				.execute((departures) -> {
					loadingVisibility.set(false);
					items.setValue(departures);
					if (departures.isEmpty()) {
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

	public MutableLiveData<List<DepartureItem>> getItems() {
		return items;
	}

	public void isFavorite() {
		isFavoriteStopInteractor.init(stop)
				.execute((favorite) -> {
					setColor.setValue(favorite);
				});
	}

	public void setFavoriteStop() {
		setFavoriteStopInteractor.init(stop)
				.execute(() -> {
					setColor.setValue(true);
				});
	}

	@Override
	protected void onCleared() {
		getDeparturesInteractor.unsubscribe();
		getFavoriteStopInteractor.unsubscribe();
		setFavoriteStopInteractor.unsubscribe();
		isFavoriteStopInteractor.unsubscribe();
		super.onCleared();
	}

	@Bindable
	public String getStopName() {
		return stop.getName();
	}
}
