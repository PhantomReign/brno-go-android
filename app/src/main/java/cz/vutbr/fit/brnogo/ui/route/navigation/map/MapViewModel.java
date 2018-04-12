package cz.vutbr.fit.brnogo.ui.route.navigation.map;

import android.arch.lifecycle.MutableLiveData;

import java.util.List;

import javax.inject.Inject;

import cz.vutbr.fit.brnogo.data.model.recyclerview.RouteItem;
import cz.vutbr.fit.brnogo.injection.annotation.scope.PerScreen;
import cz.vutbr.fit.brnogo.interactors.GetRouteInfoInteractor;
import cz.vutbr.fit.brnogo.ui.base.BaseViewModel;
import timber.log.Timber;

@PerScreen
public class MapViewModel extends BaseViewModel {

	private GetRouteInfoInteractor getRouteInfoInteractor;

	private MutableLiveData<List<RouteItem>> items = new MutableLiveData<>();

	@Inject
	public MapViewModel(GetRouteInfoInteractor getRouteInfoInteractor) {

		this.getRouteInfoInteractor = getRouteInfoInteractor;
		loadData();
	}

	public void loadData() {

		getRouteInfoInteractor.execute((routeItems) -> items.setValue(routeItems), Timber::e);
	}

	public MutableLiveData<List<RouteItem>> getItems() {
		return items;
	}

	@Override
	protected void onCleared() {
		getRouteInfoInteractor.unsubscribe();
		super.onCleared();
	}
}
