package cz.vutbr.fit.brnogo.ui.stop;

import android.arch.lifecycle.MutableLiveData;

import java.util.List;

import javax.inject.Inject;

import cz.vutbr.fit.brnogo.data.model.response.Stop;
import cz.vutbr.fit.brnogo.injection.annotation.scope.PerScreen;
import cz.vutbr.fit.brnogo.interactors.GetStopsInteractor;
import cz.vutbr.fit.brnogo.interactors.GetStopsSyncStatusInteractor;
import cz.vutbr.fit.brnogo.interactors.SetStopsSyncStatusInteractor;
import cz.vutbr.fit.brnogo.ui.base.BaseViewModel;
import timber.log.Timber;

@PerScreen
public class StopSearchViewModel extends BaseViewModel {

	private GetStopsInteractor getStopsInteractor;
	private SetStopsSyncStatusInteractor setStopsSyncStatusInteractor;
	private GetStopsSyncStatusInteractor getStopsSyncStatusInteractor;

	private MutableLiveData<List<Stop>> items = new MutableLiveData<>();

	@Inject
	public StopSearchViewModel(GetStopsInteractor getStopsInteractor,
							   SetStopsSyncStatusInteractor setStopsSyncStatusInteractor,
							   GetStopsSyncStatusInteractor getStopsSyncStatusInteractor) {
		this.getStopsInteractor = getStopsInteractor;
		this.getStopsSyncStatusInteractor = getStopsSyncStatusInteractor;
		this.setStopsSyncStatusInteractor = setStopsSyncStatusInteractor;

		listenForData();
	}

	public MutableLiveData<List<Stop>> getItems() {
		return items;
	}

	private void listenForData() {
		getStopsInteractor.execute((sync) -> {

		});
		getStopsInteractor.execute((infoItems) -> items.setValue(infoItems), Timber::e);
	}

	public void loadData() {
		setStopsSyncStatusInteractor.execute(() -> {
		});
	}

	@Override
	protected void onCleared() {
		getStopsInteractor.unsubscribe();
		getStopsSyncStatusInteractor.unsubscribe();
		setStopsSyncStatusInteractor.unsubscribe();
		super.onCleared();
	}
}
