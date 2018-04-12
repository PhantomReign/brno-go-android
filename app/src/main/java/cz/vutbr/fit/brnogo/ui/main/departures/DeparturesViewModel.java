package cz.vutbr.fit.brnogo.ui.main.departures;

import javax.inject.Inject;

import cz.vutbr.fit.brnogo.data.model.response.Stop;
import cz.vutbr.fit.brnogo.injection.annotation.scope.PerScreen;
import cz.vutbr.fit.brnogo.ui.base.BaseViewModel;

@PerScreen
public class DeparturesViewModel extends BaseViewModel {

	private Stop startStop;

	@Inject
	public DeparturesViewModel() {
		this.startStop = null;
	}

	@Override
	protected void onCleared() {
		super.onCleared();
	}

	public Stop getStartStop() {
		return startStop;
	}

	public void setStartStop(Stop startStop) {
		this.startStop = startStop;
	}
}
