package cz.vutbr.fit.brnogo.ui.main.departures;

import javax.inject.Inject;

import cz.vutbr.fit.brnogo.injection.annotation.scope.PerScreen;
import cz.vutbr.fit.brnogo.ui.base.BaseViewModel;

@PerScreen
public class DeparturesViewModel extends BaseViewModel {

	@Inject
	public DeparturesViewModel() {
	}

	@Override
	protected void onCleared() {
		super.onCleared();
	}
}
