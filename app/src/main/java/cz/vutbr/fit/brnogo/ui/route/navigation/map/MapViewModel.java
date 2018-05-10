package cz.vutbr.fit.brnogo.ui.route.navigation.map;

import android.location.Location;

import javax.inject.Inject;

import cz.vutbr.fit.brnogo.data.model.response.LiveVehicle;
import cz.vutbr.fit.brnogo.data.model.response.Route;
import cz.vutbr.fit.brnogo.data.model.store.Navigation;
import cz.vutbr.fit.brnogo.data.model.store.Search;
import cz.vutbr.fit.brnogo.injection.annotation.scope.PerScreen;
import cz.vutbr.fit.brnogo.interactors.GetFasterRouteInteractor;
import cz.vutbr.fit.brnogo.interactors.GetLocationInformationInteractor;
import cz.vutbr.fit.brnogo.interactors.GetNewRouteInteractor;
import cz.vutbr.fit.brnogo.interactors.GetVehicleInteractor;
import cz.vutbr.fit.brnogo.tools.datetime.DateTimeConverter;
import cz.vutbr.fit.brnogo.tools.livedata.SingleEventLiveData;
import cz.vutbr.fit.brnogo.ui.base.BaseViewModel;

@PerScreen
public class MapViewModel extends BaseViewModel {

	private GetLocationInformationInteractor getLocationInformationInteractor;
	private GetFasterRouteInteractor getFasterRouteInteractor;
	private GetNewRouteInteractor getNewRouteInteractor;
	private GetVehicleInteractor getVehicleInteractor;

	public Navigation navigationInfo;
	public Boolean initialized;
	public Boolean isFindingFasterRouteEnabled;
	public Boolean isFollowLocationEnabled;
	public Boolean isFindingNewRouteEnabled;
	public Boolean isDestinationReached;
	public Route currentRoute;

	@Inject
	Route route;

	@Inject
	Search search;

	private SingleEventLiveData<LiveVehicle> vehicleData = new SingleEventLiveData<>();
	private SingleEventLiveData<Location> locationData = new SingleEventLiveData<>();
	private SingleEventLiveData<Route> fasterRouteData = new SingleEventLiveData<>();
	private SingleEventLiveData<Route> newRouteData = new SingleEventLiveData<>();

	public SingleEventLiveData<Location> getLocationData() {
		return locationData;
	}

	public SingleEventLiveData<LiveVehicle> getVehicleData() {
		return vehicleData;
	}

	public SingleEventLiveData<Route> getFasterRouteData() {
		return fasterRouteData;
	}

	public SingleEventLiveData<Route> getNewRouteData() {
		return newRouteData;
	}

	@Inject
	public MapViewModel(GetVehicleInteractor getVehicleInteractor,
						GetLocationInformationInteractor getLocationInformationInteractor,
						GetFasterRouteInteractor getFasterRouteInteractor,
						GetNewRouteInteractor getNewRouteInteractor) {
		this.getLocationInformationInteractor = getLocationInformationInteractor;
		this.getVehicleInteractor = getVehicleInteractor;
		this.getFasterRouteInteractor = getFasterRouteInteractor;
		this.getNewRouteInteractor = getNewRouteInteractor;
		this.navigationInfo = new Navigation();
		this.initialized = false;
		this.isFindingFasterRouteEnabled = false;
		this.isFollowLocationEnabled = false;
		this.isFindingNewRouteEnabled = false;
		this.isDestinationReached = false;
	}

	public void getLocation() {
		getLocationInformationInteractor.execute(location -> {
			locationData.setValue(location);
		});
	}

	public void getVehicles() {
		getVehicleInteractor.init(navigationInfo.getLineCode(), navigationInfo.getLineId()).execute(liveVehicle -> vehicleData.setValue(liveVehicle));
	}

	public void getNewRoute() {

		isFindingNewRouteEnabled = true;
		getNewRouteInteractor
				.init(navigationInfo.getNextStationId(), currentRoute.getDestinationStationId(), DateTimeConverter.currentZonedDateTimeToEpochSec(), search.getTransferTime(), search.getTransfers())
				.execute(newRoute -> {
					if (!newRoute.getId().equals("")) {
						newRouteData.setValue(newRoute);
					}
				});
	}

	public void getFasterRoute() {

		int lastVehicleIndex = route.getVehicles().size() - 1;
		int lastNodeIndex = route.getVehicles().get(lastVehicleIndex).getPath().size() - 1;
		long endTime = route.getVehicles().get(lastVehicleIndex).getPath().get(lastNodeIndex).getTimeOfArrival();

		isFindingFasterRouteEnabled = true;
		getFasterRouteInteractor
				.init(navigationInfo.getNextStationId(), route.getDestinationStationId(), DateTimeConverter.currentZonedDateTimeToEpochSec(), search.getTransferTime(), search.getTransfers(), endTime)
				.execute(fasterRoute -> {
					if (!fasterRoute.getId().equals("")) {
						fasterRouteData.setValue(fasterRoute);
					}
				});
	}

	public void setLineIdAndLineCode(int lineId, int lineCode) {
		navigationInfo.setLineId(lineId);
		navigationInfo.setLineCode(lineCode);
		getVehicleInteractor.init(lineCode, lineId);
	}

	public void initNavigationData() {
		currentRoute = route;

		setLineIdAndLineCode(currentRoute.getVehicles().get(0).getLineId(), currentRoute.getVehicles().get(0).getLineCode());
		getVehicles();

		navigationInfo.setCurrentVehicle(currentRoute.getVehicles().get(0));
		navigationInfo.setCurrentVehicleIndex(0);
		navigationInfo.setCurrentNode(currentRoute.getVehicles().get(0).getPath().get(0));
		navigationInfo.setCurrentNodeIndex(0);
		navigationInfo.setCurrentNodeLocation(MapHelper.getCurrentNodeLocation(currentRoute.getVehicles().get(0).getPath().get(0)));
		navigationInfo.setNextStationId(navigationInfo.getCurrentNode().getStationId());
	}

	public void initNewNavigationData() {
		setLineIdAndLineCode(currentRoute.getVehicles().get(0).getLineId(), currentRoute.getVehicles().get(0).getLineCode());

		navigationInfo.setCurrentVehicle(currentRoute.getVehicles().get(0));
		navigationInfo.setCurrentVehicleIndex(0);
		navigationInfo.setCurrentNode(currentRoute.getVehicles().get(0).getPath().get(0));
		navigationInfo.setCurrentNodeIndex(0);
		navigationInfo.setCurrentNodeLocation(MapHelper.getCurrentNodeLocation(currentRoute.getVehicles().get(0).getPath().get(0)));
		navigationInfo.setNextStationId(navigationInfo.getCurrentNode().getStationId());
	}

	@Override
	protected void onCleared() {
		getLocationInformationInteractor.unsubscribe();
		getVehicleInteractor.unsubscribe();
		getFasterRouteInteractor.unsubscribe();
		getNewRouteInteractor.unsubscribe();
		super.onCleared();
	}
}
