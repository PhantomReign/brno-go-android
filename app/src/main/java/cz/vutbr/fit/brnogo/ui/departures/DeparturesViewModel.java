package cz.vutbr.fit.brnogo.ui.departures;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.Bindable;
import android.databinding.ObservableBoolean;

import java.util.List;

import javax.inject.Inject;

import cz.vutbr.fit.brnogo.data.model.recyclerview.DepartureItem;
import cz.vutbr.fit.brnogo.data.model.response.CurrentDeparture;
import cz.vutbr.fit.brnogo.data.model.response.Stop;
import cz.vutbr.fit.brnogo.injection.annotation.scope.PerScreen;
import cz.vutbr.fit.brnogo.interactors.GetDeparturesInteractor;
import cz.vutbr.fit.brnogo.ui.base.BaseViewModel;
import timber.log.Timber;

@PerScreen
public class DeparturesViewModel extends BaseViewModel {

	@Inject
	Stop stop;

	private GetDeparturesInteractor getDeparturesInteractor;

	public ObservableBoolean loadingVisibility = new ObservableBoolean(false);
	public ObservableBoolean offlineVisibility = new ObservableBoolean(false);
	public ObservableBoolean emptyViewVisibility = new ObservableBoolean(false);

	private MutableLiveData<List<DepartureItem>> items = new MutableLiveData<>();

	@Inject
	public DeparturesViewModel(GetDeparturesInteractor getDeparturesInteractor) {
		this.getDeparturesInteractor = getDeparturesInteractor;
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

	@Override
	protected void onCleared() {
		getDeparturesInteractor.unsubscribe();
		super.onCleared();
	}

	@Bindable
	public String getStopName() {
		return stop.getName();
	}
}
