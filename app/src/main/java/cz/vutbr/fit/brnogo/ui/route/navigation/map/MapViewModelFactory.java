package cz.vutbr.fit.brnogo.ui.route.navigation.map;

import javax.inject.Inject;
import javax.inject.Provider;

import cz.vutbr.fit.brnogo.injection.annotation.scope.PerScreen;
import cz.vutbr.fit.brnogo.ui.base.BaseViewModelFactory;

@PerScreen
public class MapViewModelFactory extends BaseViewModelFactory<MapViewModel> {

	@Inject Provider<MapViewModel> viewModelProvider;

	@Inject
	public MapViewModelFactory() {
	}

	@Override
	protected MapViewModel createViewModel() {
		return viewModelProvider.get();
	}
}
