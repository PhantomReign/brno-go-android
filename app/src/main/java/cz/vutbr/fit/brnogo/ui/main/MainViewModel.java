package cz.vutbr.fit.brnogo.ui.main;

import javax.inject.Inject;

import cz.vutbr.fit.brnogo.injection.annotation.scope.PerScreen;
import cz.vutbr.fit.brnogo.interactors.GetStartScreenInteractor;
import cz.vutbr.fit.brnogo.interactors.SetFirstStopSyncDoneInteractor;
import cz.vutbr.fit.brnogo.interactors.SetSettingsInitialDataInteractor;
import cz.vutbr.fit.brnogo.interactors.SetStopsSyncStatusInteractor;
import cz.vutbr.fit.brnogo.interactors.SyncStopsInteractor;
import cz.vutbr.fit.brnogo.interactors.UnsetFirstStopSyncDoneInteractor;
import cz.vutbr.fit.brnogo.tools.livedata.SingleEventLiveData;
import cz.vutbr.fit.brnogo.ui.base.BaseViewModel;

@PerScreen
public class MainViewModel extends BaseViewModel {

	private SyncStopsInteractor syncStopsInteractor;
	private SetStopsSyncStatusInteractor setStopsSyncStatusInteractor;
	private SetSettingsInitialDataInteractor setSettingsInitialDataInteractor;
	private SetFirstStopSyncDoneInteractor setFirstStopSyncDoneInteractor;
	private GetStartScreenInteractor getStartScreenInteractor;
	private UnsetFirstStopSyncDoneInteractor unsetFirstStopSyncDoneInteractor;

	private SingleEventLiveData<String> startScreenLiveData = new SingleEventLiveData<>();

	@Inject
	public MainViewModel(SyncStopsInteractor syncStopsInteractor,
						 SetStopsSyncStatusInteractor setStopsSyncStatusInteractor,
						 SetSettingsInitialDataInteractor setSettingsInitialDataInteractor,
						 SetFirstStopSyncDoneInteractor setFirstStopSyncDoneInteractor,
						 GetStartScreenInteractor getStartScreenInteractor,
						 UnsetFirstStopSyncDoneInteractor unsetFirstStopSyncDoneInteractor) {
		this.syncStopsInteractor = syncStopsInteractor;
		this.setStopsSyncStatusInteractor = setStopsSyncStatusInteractor;
		this.setSettingsInitialDataInteractor = setSettingsInitialDataInteractor;
		this.getStartScreenInteractor = getStartScreenInteractor;
		this.setFirstStopSyncDoneInteractor = setFirstStopSyncDoneInteractor;
		this.unsetFirstStopSyncDoneInteractor = unsetFirstStopSyncDoneInteractor;

		getStartScreen();

		loadData();
	}

	private void getStartScreen() {
		getStartScreenInteractor.execute(startScreen ->
				startScreenLiveData.setValue(startScreen));
	}

	private void loadData() {

		setSettingsInitialDataInteractor.execute((available) -> {
		});

		setFirstStopSyncDoneInteractor.execute((synced) -> {
			if (!synced) {
				setStopsSyncStatusInteractor.execute(() -> {
				});
				syncStopsInteractor.execute(() -> {
				}, (throwable) -> unsetFirstStopSyncDoneInteractor.execute((available) -> {
				}));
			}
		});
	}

	public SingleEventLiveData<String> getStartScreenLiveData() {
		return startScreenLiveData;
	}

	@Override
	protected void onCleared() {
		syncStopsInteractor.unsubscribe();
		setStopsSyncStatusInteractor.unsubscribe();
		setSettingsInitialDataInteractor.unsubscribe();
		setFirstStopSyncDoneInteractor.unsubscribe();
		getStartScreenInteractor.unsubscribe();
		super.onCleared();
	}
}
