package cz.vutbr.fit.brnogo.ui.route.navigation;

import android.arch.lifecycle.MutableLiveData;

import java.util.List;

import javax.inject.Inject;

import cz.vutbr.fit.brnogo.data.model.recyclerview.RouteItem;
import cz.vutbr.fit.brnogo.data.model.response.Route;
import cz.vutbr.fit.brnogo.injection.annotation.scope.PerScreen;
import cz.vutbr.fit.brnogo.interactors.GetRouteInfoInteractor;
import cz.vutbr.fit.brnogo.ui.base.BaseViewModel;

@PerScreen
public class RouteNavigationViewModel extends BaseViewModel {

	@Inject
	Route route;

	private GetRouteInfoInteractor getRouteInfoInteractor;

	private MutableLiveData<List<RouteItem>> items = new MutableLiveData<>();

	@Inject
	public RouteNavigationViewModel(GetRouteInfoInteractor getRouteInfoInteractor) {
		this.getRouteInfoInteractor = getRouteInfoInteractor;
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
