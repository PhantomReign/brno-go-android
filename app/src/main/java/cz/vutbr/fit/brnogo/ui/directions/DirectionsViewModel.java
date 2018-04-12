package cz.vutbr.fit.brnogo.ui.directions;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableBoolean;
import android.support.annotation.Nullable;

import java.util.List;

import javax.inject.Inject;

import cz.vutbr.fit.brnogo.R;
import cz.vutbr.fit.brnogo.data.model.recyclerview.RouteItem;
import cz.vutbr.fit.brnogo.data.model.response.Route;
import cz.vutbr.fit.brnogo.data.model.store.Search;
import cz.vutbr.fit.brnogo.injection.annotation.scope.PerScreen;
import cz.vutbr.fit.brnogo.interactors.GetDirectionsInteractor;
import cz.vutbr.fit.brnogo.interactors.IsSavedRouteInteractor;
import cz.vutbr.fit.brnogo.interactors.SetSavedRouteInteractor;
import cz.vutbr.fit.brnogo.tools.livedata.SingleEventLiveData;
import cz.vutbr.fit.brnogo.ui.base.BaseViewModel;
import timber.log.Timber;

@PerScreen
public class DirectionsViewModel extends BaseViewModel {

	@Inject
	Search search;

	@Nullable
	@Inject
	Route route;

	private GetDirectionsInteractor getDirectionsInteractor;
	private SetSavedRouteInteractor setSavedRouteInteractor;
	private IsSavedRouteInteractor isSavedRouteInteractor;

	public ObservableBoolean loadingVisibility = new ObservableBoolean(false);
	public ObservableBoolean offlineVisibility = new ObservableBoolean(false);
	public ObservableBoolean emptyViewVisibility = new ObservableBoolean(false);
	public ObservableBoolean canSave = new ObservableBoolean(false);

	private MutableLiveData<List<RouteItem>> items = new MutableLiveData<>();
	public SingleEventLiveData<Boolean> setColor = new SingleEventLiveData<>();
	public SingleEventLiveData<Integer> showMessage = new SingleEventLiveData<>();

	@Inject
	public DirectionsViewModel(GetDirectionsInteractor getDirectionsInteractor,
							   SetSavedRouteInteractor setSavedRouteInteractor,
							   IsSavedRouteInteractor isSavedRouteInteractor) {
		this.getDirectionsInteractor = getDirectionsInteractor;
		this.setSavedRouteInteractor = setSavedRouteInteractor;
		this.isSavedRouteInteractor = isSavedRouteInteractor;
	}

	public void loadData() {
		loadingVisibility.set(true);
		offlineVisibility.set(false);

		getDirectionsInteractor
				.init(search, route)
				.execute(directions -> {
					loadingVisibility.set(false);
					items.setValue(directions);
					if (directions.isEmpty()) {
						emptyViewVisibility.set(true);
					} else {
						canSave.set(true);
						isSaved();
						emptyViewVisibility.set(false);
					}
				}, throwable -> {
					Timber.e(throwable);
					loadingVisibility.set(false);
					emptyViewVisibility.set(false);
					canSave.set(false);
					offlineVisibility.set(true);
				});
	}

	public MutableLiveData<List<RouteItem>> getItems() {
		return items;
	}

	public void isSaved() {
		isSavedRouteInteractor.init((Route) items.getValue().get(0))
				.execute((saved) -> {
					setColor.setValue(saved);
				});
	}

	public void setSavedRoute() {
		if (canSave.get()) {
			setSavedRouteInteractor.init((Route) items.getValue().get(0))
					.execute(() -> {
						setColor.setValue(true);
					});
		} else {
			showMessage.setValue(R.string.direction_save_unavailable);
		}
	}

	@Override
	protected void onCleared() {
		getDirectionsInteractor.unsubscribe();
		setSavedRouteInteractor.unsubscribe();
		isSavedRouteInteractor.unsubscribe();
		super.onCleared();
	}
}
