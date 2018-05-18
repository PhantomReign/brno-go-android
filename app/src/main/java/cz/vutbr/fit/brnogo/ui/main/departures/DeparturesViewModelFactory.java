package cz.vutbr.fit.brnogo.ui.main.departures;

import javax.inject.Inject;
import javax.inject.Provider;

import cz.vutbr.fit.brnogo.injection.annotation.scope.PerScreen;
import cz.vutbr.fit.brnogo.ui.base.BaseViewModelFactory;

@PerScreen
public class DeparturesViewModelFactory extends BaseViewModelFactory<DeparturesViewModel> {

	@Inject Provider<DeparturesViewModel> viewModelProvider;

	@Inject
	public DeparturesViewModelFactory() {
	}

	@Override
	protected DeparturesViewModel createViewModel() {
		return viewModelProvider.get();
	}
}
