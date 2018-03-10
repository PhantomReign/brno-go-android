package cz.vutbr.fit.brnogo.ui.main;

import javax.inject.Inject;

import cz.vutbr.fit.brnogo.injection.annotation.scope.PerScreen;
import cz.vutbr.fit.brnogo.interactors.SetStopsSyncStatusInteractor;
import cz.vutbr.fit.brnogo.interactors.SyncStopsInteractor;
import cz.vutbr.fit.brnogo.ui.base.BaseViewModel;

@PerScreen
public class MainViewModel extends BaseViewModel {

	private SyncStopsInteractor syncStopsInteractor;
	private SetStopsSyncStatusInteractor setStopsSyncStatusInteractor;

	@Inject
	public MainViewModel(SyncStopsInteractor syncStopsInteractor,
						 SetStopsSyncStatusInteractor setStopsSyncStatusInteractor) {
		this.syncStopsInteractor = syncStopsInteractor;
		this.setStopsSyncStatusInteractor = setStopsSyncStatusInteractor;

		loadData();
	}

	private void loadData() {
		setStopsSyncStatusInteractor.execute(() -> {
		});
		syncStopsInteractor.execute(() -> {
		});
	}

	@Override
	protected void onCleared() {
		syncStopsInteractor.unsubscribe();
		setStopsSyncStatusInteractor.unsubscribe();
		super.onCleared();
	}
}
