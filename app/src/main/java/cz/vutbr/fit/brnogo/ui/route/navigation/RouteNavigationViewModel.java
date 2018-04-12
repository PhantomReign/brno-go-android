package cz.vutbr.fit.brnogo.ui.route.navigation;

import android.arch.lifecycle.MutableLiveData;
import android.location.Location;

import java.util.List;

import javax.inject.Inject;

import cz.vutbr.fit.brnogo.data.model.recyclerview.RouteItem;
import cz.vutbr.fit.brnogo.data.model.response.LiveVehicle;
import cz.vutbr.fit.brnogo.data.model.response.Route;
import cz.vutbr.fit.brnogo.injection.annotation.scope.PerScreen;
import cz.vutbr.fit.brnogo.interactors.GetNavigationInformationInteractor;
import cz.vutbr.fit.brnogo.interactors.GetRouteInfoInteractor;
import cz.vutbr.fit.brnogo.interactors.GetVehicleInteractor;
import cz.vutbr.fit.brnogo.tools.livedata.SingleEventLiveData;
import cz.vutbr.fit.brnogo.ui.base.BaseViewModel;
import timber.log.Timber;

@PerScreen
public class RouteNavigationViewModel extends BaseViewModel {

	@Inject
	Route route;

	private int lineCode;
	private int lineId;

	private GetRouteInfoInteractor getRouteInfoInteractor;
	private GetNavigationInformationInteractor getNavigationInformationInteractor;
	private GetVehicleInteractor getVehicleInteractor;

	private MutableLiveData<List<RouteItem>> items = new MutableLiveData<>();
	private SingleEventLiveData<Location> locationData = new SingleEventLiveData<>();
	private SingleEventLiveData<LiveVehicle> vehicleData = new SingleEventLiveData<>();

	@Inject
	public RouteNavigationViewModel(GetRouteInfoInteractor getRouteInfoInteractor,
									GetNavigationInformationInteractor getNavigationInformationInteractor,
									GetVehicleInteractor getVehicleInteractor) {
		this.getRouteInfoInteractor = getRouteInfoInteractor;
		this.getNavigationInformationInteractor = getNavigationInformationInteractor;
		this.getVehicleInteractor = getVehicleInteractor;
		nav();
	}

	public void nav() {
		getNavigationInformationInteractor.init(route).execute(location -> {
			locationData.setValue(location);
		});
	}

	public void loadData() {
		Timber.e("CODE " + lineCode);
		getVehicleInteractor.init(lineCode, lineId).execute(liveVehicle -> vehicleData.setValue(liveVehicle));
	}

	public SingleEventLiveData<Location> getLocationData() {
		return locationData;
	}

	public SingleEventLiveData<LiveVehicle> getVehicleData() {
		return vehicleData;
	}

	public MutableLiveData<List<RouteItem>> getItems() {
		return items;
	}

	@Override
	protected void onCleared() {
		getNavigationInformationInteractor.unsubscribe();
		getRouteInfoInteractor.unsubscribe();
		getVehicleInteractor.unsubscribe();
		super.onCleared();
	}

	public void setLineCode(int lineCode) {
		this.lineCode = lineCode;
	}

	public void setLineId(int lineId) {
		getVehicleInteractor.init(lineCode, lineId);
		this.lineId = lineId;
	}

}
