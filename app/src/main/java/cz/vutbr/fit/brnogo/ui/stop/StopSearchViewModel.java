package cz.vutbr.fit.brnogo.ui.stop;

import android.arch.lifecycle.MutableLiveData;

import java.util.List;

import javax.inject.Inject;

import cz.vutbr.fit.brnogo.data.model.response.Stop;
import cz.vutbr.fit.brnogo.injection.annotation.scope.PerScreen;
import cz.vutbr.fit.brnogo.interactors.GetStopsInteractor;
import cz.vutbr.fit.brnogo.ui.base.BaseViewModel;
import timber.log.Timber;

@PerScreen
public class StopSearchViewModel extends BaseViewModel {

	private GetStopsInteractor getStopsInteractor;

	private MutableLiveData<List<Stop>> items = new MutableLiveData<>();

	@Inject
	public StopSearchViewModel(GetStopsInteractor getStopsInteractor) {
		this.getStopsInteractor = getStopsInteractor;

		loadData();
	}

	private void loadData() {
		getStopsInteractor.execute((infoItems) -> items.setValue(infoItems), Timber::e);
	}

	public MutableLiveData<List<Stop>> getItems() {
		return items;
	}

	@Override
	protected void onCleared() {
		getStopsInteractor.unsubscribe();
		super.onCleared();
	}
}
