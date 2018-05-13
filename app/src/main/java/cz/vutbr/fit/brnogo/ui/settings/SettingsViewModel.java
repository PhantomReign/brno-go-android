package cz.vutbr.fit.brnogo.ui.settings;

import javax.inject.Inject;

import cz.vutbr.fit.brnogo.injection.annotation.scope.PerScreen;
import cz.vutbr.fit.brnogo.interactors.SetStopsSyncStatusInteractor;
import cz.vutbr.fit.brnogo.interactors.SyncStopsInteractor;
import cz.vutbr.fit.brnogo.tools.constant.Constant;
import cz.vutbr.fit.brnogo.tools.livedata.SingleEventLiveData;
import cz.vutbr.fit.brnogo.ui.base.BaseViewModel;

@PerScreen
public class SettingsViewModel extends BaseViewModel {

	private SyncStopsInteractor syncStopsInteractor;
	private SetStopsSyncStatusInteractor setStopsSyncStatusInteractor;

	public SingleEventLiveData<Integer> msgType = new SingleEventLiveData<>();

	@Inject
	public SettingsViewModel(SyncStopsInteractor syncStopsInteractor,
							 SetStopsSyncStatusInteractor setStopsSyncStatusInteractor) {
		this.setStopsSyncStatusInteractor = setStopsSyncStatusInteractor;
		this.syncStopsInteractor = syncStopsInteractor;
	}

	protected void sync() {
		msgType.setValue(Constant.SyncStatus.SYNC);
		setStopsSyncStatusInteractor.execute(() -> {
		});
		syncStopsInteractor.execute(() -> msgType.setValue(Constant.SyncStatus.DONE),
				(throwable) -> msgType.setValue(Constant.SyncStatus.ERROR));
	}

	@Override
	protected void onCleared() {
		syncStopsInteractor.unsubscribe();
		setStopsSyncStatusInteractor.unsubscribe();
		super.onCleared();
	}
}
