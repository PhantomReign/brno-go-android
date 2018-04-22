package cz.vutbr.fit.brnogo.ui.route.navigation.map;

import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.DirectionsApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.TravelMode;
import com.sdoward.rxgooglemap.MapObservableProvider;

import org.joda.time.DateTime;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import cz.vutbr.fit.brnogo.R;
import cz.vutbr.fit.brnogo.data.model.response.Node;
import cz.vutbr.fit.brnogo.databinding.FragmentMapBinding;
import cz.vutbr.fit.brnogo.tools.PermissionChecker;
import cz.vutbr.fit.brnogo.tools.constant.Constant;
import cz.vutbr.fit.brnogo.tools.constant.QuantityType;
import cz.vutbr.fit.brnogo.tools.constant.UserActionType;
import cz.vutbr.fit.brnogo.tools.datetime.DateTimeConverter;
import cz.vutbr.fit.brnogo.tools.location.LocationConverter;
import cz.vutbr.fit.brnogo.ui.base.BaseFragment;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;
import pl.charmas.android.reactivelocation2.ReactiveLocationProvider;
import timber.log.Timber;

@RuntimePermissions
public class MapFragment extends BaseFragment<MapViewModel, FragmentMapBinding> implements MapView {

	@Inject MapViewModelFactory viewModelFactory;
	@Inject ReactiveLocationProvider reactiveLocationProvider;
	@Inject PermissionChecker permissionChecker;

	private MapObservableProvider mapProvider;
	private Location defaultLocation = MapHelper.getDefaultLocation();
	private Disposable mapSubscription = DisposableHelper.DISPOSED;
	private GoogleMap map;
	private boolean isPathToStopSet;

	public static MapFragment newInstance() {
		return new MapFragment();
	}

	private DirectionsResult getDirectionsDetails(String origin, String destination, TravelMode mode) {
		DateTime now = new DateTime();
		try {
			return DirectionsApi.newRequest(MapHelper.getGeoContext(getString(R.string.google_maps_api_key)))
					.mode(mode)
					.origin(origin)
					.destination(destination)
					.departureTime(now)
					.await();
		} catch (ApiException | InterruptedException | IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	protected MapViewModel createViewModel() {
		return ViewModelProviders.of(this, viewModelFactory).get(MapViewModel.class);
	}

	@Override
	protected FragmentMapBinding inflateBindingLayout(LayoutInflater layoutInflater) {
		return FragmentMapBinding.inflate(layoutInflater);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fragment_map);
		mapProvider = new MapObservableProvider(mapFragment);

		MapFragmentPermissionsDispatcher.afterLocationGrantedWithPermissionCheck(this);

		isPathToStopSet = false;

		viewModel.initNavigationData();
		viewModel.getLocation();

		viewModel.getLocationData().observe(this, location -> {
			if (viewModel.initialized) {
				if (binding.mapInfoLayout.getVisibility() != View.VISIBLE) {
					binding.mapInfoLayout.setVisibility(View.VISIBLE);
				}
				onLocationChanged(location);
			}
		});

		viewModel.getVehicleData().observe(this, liveVehicle -> {
			viewModel.initialized = true;
			if (liveVehicle.getRouteId() != -1) {
				viewModel.navigationInfo.setCurrentLiveVehicle(liveVehicle);
			}
		});

		viewModel.getFasterRouteData().observe(this, fasterRoute -> {
			viewModel.navigationInfo.setFasterRoute(fasterRoute);
			binding.fasterRouteCard.setVisibility(View.VISIBLE);
		});

		viewModel.getNewRouteData().observe(this, route -> {
			viewModel.currentRoute = route;
			viewModel.initNewNavigationData();
			viewModel.isFindingNewRouteEnabled = false;
		});

	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		MapFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
	}

	@NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
	public void afterLocationGranted() {
		if (GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(getContext()) == ConnectionResult.SUCCESS) {
			initMap();
		}
	}

	@OnPermissionDenied(Manifest.permission.ACCESS_FINE_LOCATION)
	void onPermissionDenied() {
		initMapWithoutLocation();
	}

	@OnNeverAskAgain(Manifest.permission.ACCESS_FINE_LOCATION)
	void onNeverAskAgain() {
		initMapWithoutLocation();
	}

	@Override
	public void onExitVehicleClick() {
		viewModel.navigationInfo.setInVehicle(false);
		binding.exitButton.setVisibility(View.GONE);
	}

	@Override
	public void onTrackClick() {
		int tintColor = ContextCompat.getColor(binding.getRoot().getContext(), viewModel.isFollowLocationEnabled ? R.color.grey_600 : R.color.colorPrimary);
		binding.locationTracker.setColorFilter(tintColor);
		viewModel.isFollowLocationEnabled = !viewModel.isFollowLocationEnabled;
	}

	@Override
	public void onReplaceClick() {
		viewModel.currentRoute = viewModel.navigationInfo.getFasterRoute();
		viewModel.navigationInfo.setFasterRoute(null);
		viewModel.isFindingFasterRouteEnabled = false;
		viewModel.initNewNavigationData();

		binding.fasterRouteCard.setVisibility(View.GONE);
	}

	@Override
	public void onKeepClick() {
		viewModel.isFindingFasterRouteEnabled = false;
		binding.fasterRouteCard.setVisibility(View.GONE);
	}

	@Override
	public void onEnterVehicleClick() {
		viewModel.navigationInfo.setInVehicle(true);
		binding.enterButton.setVisibility(View.GONE);
		binding.exitButton.setVisibility(View.VISIBLE);
	}

	@SuppressWarnings("MissingPermission")
	private void initMap() {
		Observable<Location> locationObservable = reactiveLocationProvider
				.getLastKnownLocation()
				.defaultIfEmpty(defaultLocation)
				.onErrorReturn(throwable -> defaultLocation);

		Maybe<GoogleMap> mapObservable = mapProvider
				.getMapReadyObservable()
				.firstElement();

		mapSubscription = Observable.zip(locationObservable, mapObservable.toObservable(), Pair::create)
				.doOnNext(locationGoogleMapPair -> {
					Location location = locationGoogleMapPair.first;
					map = locationGoogleMapPair.second;

					LatLng convertedLocation = new LatLng(location.getLatitude(), location.getLongitude());
					map.moveCamera(CameraUpdateFactory.newLatLngZoom(convertedLocation, 16));

					setMap(map);
				})
				.map(locationGoogleMapPair -> locationGoogleMapPair.second)
				.flatMap(__ -> mapProvider.getCameraMoveStartedObservable())
				.filter(reason -> reason == GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE)
				.subscribe();
	}

	private void initMapWithoutLocation() {
		mapSubscription = mapProvider.getMapReadyObservable()
				.doOnNext(this::setMap)
				.subscribe();
	}

	@SuppressWarnings("MissingPermission")
	private void setMap(GoogleMap map) {
		map.getUiSettings().setRotateGesturesEnabled(false);
		map.getUiSettings().setTiltGesturesEnabled(false);
		map.getUiSettings().setZoomControlsEnabled(false);
		map.getUiSettings().setMyLocationButtonEnabled(false);

		if (permissionChecker.hasLocation()) {
			map.setMyLocationEnabled(true);
		}

	}

	private void addPathDirectionsToMap(String origin, String destination, TravelMode mode) {
		DirectionsResult results = getDirectionsDetails(origin, destination, mode);
		if (results != null) {
			MapHelper.addPolylineToMap(results, map, binding.getRoot().getContext());
			MapHelper.addMarkersToMap(results, map);
		}
	}

	@Override
	public void onDestroyView() {
		mapSubscription.dispose();
		super.onDestroyView();
	}

	public void onLocationChanged(Location location) {
		if (location != null && viewModel.navigationInfo.getCurrentNode() != null) {
			viewModel.navigationInfo.setCurrentUserLocation(location);
			if (viewModel.isFollowLocationEnabled) {
				LatLng mapLocation = new LatLng(location.getLatitude(), location.getLongitude());
				map.animateCamera(CameraUpdateFactory.newLatLngZoom(mapLocation, 16), 800, null);
			}

			// Calculate distance to current node
			double distance = LocationConverter.getDistance(location,
					viewModel.navigationInfo.getCurrentNode().getStopLongitude(),
					viewModel.navigationInfo.getCurrentNode().getStopLatitude());

			Timber.w("CEI: " + viewModel.navigationInfo.getCurrentNodeIndex());
			boolean isFirstNode = viewModel.navigationInfo.getCurrentNodeIndex() == 0;

			Node previous = viewModel.navigationInfo.getCurrentNode();
			if (!viewModel.isDestinationReached) {
				if (updateUserStatus(distance, isFirstNode)) {
					long currentTime = DateTimeConverter.currentZonedDateTimeToEpochSec();
					long timeToDeparture = Math.abs((viewModel.navigationInfo.getCurrentNode().getTimeOfDeparture() - currentTime));

					long timeToDepartureWithDelay;

					if (viewModel.navigationInfo.getCurrentLiveVehicle() != null
							&& viewModel.navigationInfo.getCurrentLiveVehicle().getRouteId() != -1) {
						timeToDepartureWithDelay = timeToDeparture + viewModel.navigationInfo.getCurrentLiveVehicle().getDelay() * 60;
					} else {
						timeToDepartureWithDelay = timeToDeparture + viewModel.currentRoute.getVehicles().get(viewModel.navigationInfo.getCurrentVehicleIndex()).getDelay();
					}

					if (!previous.equals(viewModel.navigationInfo.getCurrentNode())) {
						distance = LocationConverter.getDistance(location,
								viewModel.navigationInfo.getCurrentNode().getStopLongitude(),
								viewModel.navigationInfo.getCurrentNode().getStopLatitude());
					}

					binding.stopName.setText(viewModel.navigationInfo.getCurrentNode().getStationName());
					binding.currentAction.setText(getCurrentAction());
					binding.information.setText(getInformationToCurrentAction());
					binding.distanceTime.setText(getQuantityText(getQuantity(distance, timeToDepartureWithDelay), getQuantityType()));

				} else {
					viewModel.isDestinationReached = true;
					// TODO is in vehicle and away from stop - new route
				}
			} else {

				if (viewModel.navigationInfo.isInVehicle() && distance > Constant.Navigation.STOP_EXIT_PERIMETER) {
					onFinishReachedFailed();
				} else {
					onFinishReached();
				}
			}
		}
	}

	public String getQuantityText(Object value, int type) {
		if (type == QuantityType.TYPE_DISTANCE) {
			return getString(R.string.m, (int) value);
		} else if (type == QuantityType.TYPE_TIME) {
			long secondsRemaining = (long) value;

			if (viewModel.navigationInfo.getCurrentUserState() == UserActionType.TYPE_BOARD) {
				secondsRemaining += Constant.Navigation.ENTER_VEHICLE_TIME_OFFSET_AFTER;
			}

			long min = TimeUnit.SECONDS.toMinutes(secondsRemaining) % 60;
			long sec = TimeUnit.SECONDS.toSeconds(secondsRemaining) % 60;

			return getString(R.string.minutes_seconds, (int) min, (int) sec);
		} else {
			return "";
		}
	}

	private String getCurrentAction() {
		switch (viewModel.navigationInfo.getCurrentUserState()) {
			case UserActionType.TYPE_EXIT:
				return getString(R.string.nav_exit);
			case UserActionType.TYPE_WAIT_FOR_NEW_ROUTE:
				return getString(R.string.nav_finding_new);
			case UserActionType.TYPE_WAIT_FOR_VEHICLE_TO_LEAVE:
				return getString(R.string.nav_stay_in);
			case UserActionType.TYPE_BOARD:
				return getString(R.string.nav_board);
			case UserActionType.TYPE_RIDE:
				return getString(R.string.nav_ride);
			case UserActionType.TYPE_WALK:
				return getString(R.string.nav_walk);
			case UserActionType.TYPE_WAIT:
				return getString(R.string.nav_wait);
			default:
				return "";
		}
	}

	private String getInformationToCurrentAction() {
		switch (viewModel.navigationInfo.getCurrentUserState()) {
			case UserActionType.TYPE_EXIT:
				return getString(R.string.nav_exit_info);
			case UserActionType.TYPE_WAIT_FOR_NEW_ROUTE:
				return getString(R.string.nav_finding_new_info);
			case UserActionType.TYPE_WAIT_FOR_VEHICLE_TO_LEAVE:
				return getString(R.string.nav_stay_in_info);
			case UserActionType.TYPE_BOARD:
				return getString(R.string.nav_board_info, viewModel.navigationInfo.getCurrentVehicle().getLineId());
			case UserActionType.TYPE_RIDE:
				return getString(R.string.nav_ride_info, viewModel.navigationInfo.getCurrentVehicle().getLineId());
			case UserActionType.TYPE_WALK:
				return getString(R.string.nav_walk_info, viewModel.navigationInfo.getCurrentNode().getStationName());
			case UserActionType.TYPE_WAIT:
				return getString(R.string.nav_wait_info,
						viewModel.navigationInfo.getCurrentVehicle().getLineId(),
						getFormattedTimeOfDepartureWithDelay());
			default:
				return "";
		}
	}

	private String getFormattedTimeOfDepartureWithDelay() {
				viewModel.navigationInfo.getCurrentNode().getFormattedTimeOfDeparture();
		long timeWithDelay = viewModel.navigationInfo.getCurrentVehicle().getDelay()
				+ viewModel.navigationInfo.getCurrentNode().getTimeOfDeparture();
		return DateTimeConverter.epochSecToZonedHourMinute(timeWithDelay);
	}

	private Object getQuantity(double distance, long timeToDeparture) {
		switch (viewModel.navigationInfo.getCurrentUserState()) {
			case UserActionType.TYPE_WALK:
			case UserActionType.TYPE_RIDE:
				return (int) distance;
			case UserActionType.TYPE_WAIT:
			case UserActionType.TYPE_BOARD:
				return timeToDeparture;
			default:
				return null;
		}
	}

	private int getQuantityType() {
		switch (viewModel.navigationInfo.getCurrentUserState()) {
			case UserActionType.TYPE_WALK:
			case UserActionType.TYPE_RIDE:
				return QuantityType.TYPE_DISTANCE;
			case UserActionType.TYPE_WAIT:
			case UserActionType.TYPE_BOARD:
				return QuantityType.TYPE_TIME;
			default:
				return QuantityType.TYPE_NONE;
		}
	}

	private boolean updateUserStatus(double distance, boolean isFirstNode) {
		boolean isTransfer = viewModel.navigationInfo.getCurrentNodeIndex() == (viewModel.navigationInfo.getCurrentVehicle().getPath().size() - 1);
		/* Calculate whether current stop was reached or not */
		if (!viewModel.navigationInfo.isCurrentNodeReached()) {
			if (!isPathToStopSet && !viewModel.navigationInfo.isInVehicle()) {
				isPathToStopSet = true;
				String origin = String.valueOf(viewModel.navigationInfo.getCurrentUserLocation().getLatitude())
						+ ", " + String.valueOf(viewModel.navigationInfo.getCurrentUserLocation().getLongitude());
				String destination = String.valueOf(viewModel.navigationInfo.getCurrentNodeLocation().getLatitude())
						+ ", " + String.valueOf(viewModel.navigationInfo.getCurrentNodeLocation().getLongitude());
				addPathDirectionsToMap(origin, destination, TravelMode.WALKING);
			}

			if (distance < Constant.Navigation.ON_STOP_DISTANCE_THRESHOLD) {
				Timber.w("REACHED");
				isPathToStopSet = false;
				map.clear();
				viewModel.navigationInfo.setCurrentNodeReached(true);
			} else {
				viewModel.navigationInfo.setCurrentNodeReached(false);
			}
		}

		long departureWithDelay;

		if (viewModel.navigationInfo.getCurrentLiveVehicle() != null
				&& viewModel.navigationInfo.getCurrentLiveVehicle().getRouteId() != -1) {
			departureWithDelay = viewModel.currentRoute.getVehicles().get(viewModel.navigationInfo.getCurrentVehicleIndex())
					.getPath().get(viewModel.navigationInfo.getCurrentNodeIndex())
					.getTimeOfDeparture() + viewModel.navigationInfo.getCurrentLiveVehicle().getDelay() * 60;
		} else {
			departureWithDelay = viewModel.currentRoute.getVehicles().get(viewModel.navigationInfo.getCurrentVehicleIndex())
					.getPath().get(viewModel.navigationInfo.getCurrentNodeIndex())
					.getTimeOfDeparture() + viewModel.currentRoute.getVehicles().get(viewModel.navigationInfo.getCurrentVehicleIndex()).getDelay();
		}

		if (isFirstNode) {
			// TODO - add find faster

			if (viewModel.navigationInfo.isCurrentNodeReached()) {

				if (departureWithDelay + Constant.Navigation.ENTER_VEHICLE_TIME_OFFSET_AFTER > DateTimeConverter.currentZonedDateTimeToEpochSec()
						&& DateTimeConverter.currentZonedDateTimeToEpochSec() > departureWithDelay - Constant.Navigation.ENTER_VEHICLE_TIME_OFFSET_BEFORE) {
					if (binding.exitButton.getVisibility() != View.VISIBLE) {
						binding.enterButton.setVisibility(View.VISIBLE);
					}
				} else {
					binding.enterButton.setVisibility(View.GONE);
				}

				if (viewModel.navigationInfo.isInVehicle()) {
					if (distance > Constant.Navigation.STOP_EXIT_PERIMETER) {
						Timber.e("RIDE: " + distance);
						viewModel.navigationInfo.setCurrentUserState(UserActionType.TYPE_RIDE);
						return moveToNextStation(false);
					} else {
						viewModel.navigationInfo.setCurrentUserState(UserActionType.TYPE_WAIT_FOR_VEHICLE_TO_LEAVE);
					}
				} else {
					if (distance > Constant.Navigation.ON_STOP_DISTANCE_THRESHOLD && departureWithDelay >= DateTimeConverter.currentZonedDateTimeToEpochSec()) {
						viewModel.navigationInfo.setCurrentNodeReached(false);
						Timber.e("WALK AGAin: " + distance);
						viewModel.navigationInfo.setCurrentUserState(UserActionType.TYPE_WALK);
					} else if (distance <= Constant.Navigation.ON_STOP_DISTANCE_THRESHOLD
							&& departureWithDelay + Constant.Navigation.ENTER_VEHICLE_TIME_OFFSET_AFTER < DateTimeConverter.currentZonedDateTimeToEpochSec()) {
						//viewModel.navigationInfo.setCurrentNodeReached(false);
						Timber.e("NEED TO FIND NEW ROUTE");
						if (!viewModel.isFindingNewRouteEnabled) {
							viewModel.getNewRoute();
						}
						viewModel.navigationInfo.setCurrentUserState(UserActionType.TYPE_WAIT_FOR_NEW_ROUTE);
					} else if (distance <= Constant.Navigation.ON_STOP_DISTANCE_THRESHOLD
							&& departureWithDelay + Constant.Navigation.ENTER_VEHICLE_TIME_OFFSET_AFTER > DateTimeConverter.currentZonedDateTimeToEpochSec()
							&& DateTimeConverter.currentZonedDateTimeToEpochSec() > departureWithDelay - Constant.Navigation.ENTER_VEHICLE_TIME_OFFSET_BEFORE) {
						//viewModel.navigationInfo.setCurrentNodeReached(false);
						Timber.e("BOARD");
						viewModel.navigationInfo.setCurrentUserState(UserActionType.TYPE_BOARD);
					} else {
						Timber.e("WAIT: " + distance);
						viewModel.navigationInfo.setCurrentUserState(UserActionType.TYPE_WAIT);
					}
				}
			} else {

				if (departureWithDelay + Constant.Navigation.ENTER_VEHICLE_TIME_OFFSET_AFTER < DateTimeConverter.currentZonedDateTimeToEpochSec()) {
					Timber.e("NEED TO FIND NEW ROUTE");
					if (!viewModel.isFindingNewRouteEnabled) {
						viewModel.getNewRoute();
					}
					// todo - add if
					viewModel.navigationInfo.setCurrentUserState(UserActionType.TYPE_WAIT_FOR_NEW_ROUTE);
				} else {
					Timber.e("WALK: " + distance);
					viewModel.navigationInfo.setCurrentUserState(UserActionType.TYPE_WALK);
				}
			}
		} else {
			// Check whether station has been reached
			if (viewModel.navigationInfo.isCurrentNodeReached()) {
				Timber.e("NOT FIRST ENTRY: " + distance);

				if (departureWithDelay + Constant.Navigation.ENTER_VEHICLE_TIME_OFFSET_AFTER > DateTimeConverter.currentZonedDateTimeToEpochSec()
						&& DateTimeConverter.currentZonedDateTimeToEpochSec() > departureWithDelay - Constant.Navigation.ENTER_VEHICLE_TIME_OFFSET_BEFORE) {
					if (binding.exitButton.getVisibility() != View.VISIBLE) {
						binding.enterButton.setVisibility(View.VISIBLE);
					}
				} else {
					binding.enterButton.setVisibility(View.GONE);
				}

				if (viewModel.navigationInfo.isInVehicle()) {
					if (distance > Constant.Navigation.STOP_EXIT_PERIMETER) {
						if (isTransfer) {
							viewModel.isDestinationReached = true;
						}

						viewModel.navigationInfo.setCurrentUserState(UserActionType.TYPE_RIDE);
						boolean canMove = moveToNextStation(false);
						viewModel.navigationInfo.setNextStationId(viewModel.navigationInfo.getCurrentNode().getStationId());
						if (!viewModel.isFindingFasterRouteEnabled && canMove) {
							viewModel.getFasterRoute();
						}
						return canMove;
					} else {
						if (isTransfer) {
							viewModel.navigationInfo.setCurrentUserState(UserActionType.TYPE_EXIT);
						} else {
							viewModel.navigationInfo.setCurrentUserState(UserActionType.TYPE_WAIT_FOR_VEHICLE_TO_LEAVE);
						}
					}
				} else {
					//TODO CHECK FINAL

					if (isTransfer) {
						return moveToNextStation(true);
					}

					if (viewModel.currentRoute.getDestinationStationId() != viewModel.navigationInfo.getCurrentNode().getStationId()) {
						Timber.e("EXIT EARLY");
					}

					if (distance > Constant.Navigation.ON_STOP_DISTANCE_THRESHOLD && departureWithDelay >= DateTimeConverter.currentZonedDateTimeToEpochSec()) {
						viewModel.navigationInfo.setCurrentNodeReached(false);
						Timber.e("WALK AGAin: " + distance);
						viewModel.navigationInfo.setCurrentUserState(UserActionType.TYPE_WALK);
					} else if (distance <= Constant.Navigation.ON_STOP_DISTANCE_THRESHOLD
							&& departureWithDelay + Constant.Navigation.ENTER_VEHICLE_TIME_OFFSET_AFTER < DateTimeConverter.currentZonedDateTimeToEpochSec()) {
						//viewModel.navigationInfo.setCurrentNodeReached(false);
						Timber.e("NEED TO FIND NEW ROUTE");
						if (!viewModel.isFindingNewRouteEnabled) {
							viewModel.getNewRoute();
						}
						viewModel.navigationInfo.setCurrentUserState(UserActionType.TYPE_WAIT_FOR_NEW_ROUTE);
					} else if (distance <= Constant.Navigation.ON_STOP_DISTANCE_THRESHOLD
							&& departureWithDelay + Constant.Navigation.ENTER_VEHICLE_TIME_OFFSET_AFTER > DateTimeConverter.currentZonedDateTimeToEpochSec()
							&& DateTimeConverter.currentZonedDateTimeToEpochSec() > departureWithDelay - Constant.Navigation.ENTER_VEHICLE_TIME_OFFSET_BEFORE) {
						//viewModel.navigationInfo.setCurrentNodeReached(false);
						Timber.e("BOARD");
						viewModel.navigationInfo.setCurrentUserState(UserActionType.TYPE_BOARD);
					} else {
						Timber.e("WAIT: " + distance);
						viewModel.navigationInfo.setCurrentUserState(UserActionType.TYPE_WAIT);
					}
				}
			} else {

				if (viewModel.navigationInfo.isInVehicle()) {
					// Keep traveling
					Timber.w("TRAVELLING");
					viewModel.navigationInfo.setCurrentUserState(UserActionType.TYPE_RIDE);
				} else {
					if (departureWithDelay + Constant.Navigation.ENTER_VEHICLE_TIME_OFFSET_AFTER < DateTimeConverter.currentZonedDateTimeToEpochSec()) {
						Timber.e("NEED TO FIND NEW ROUTE");
						if (!viewModel.isFindingNewRouteEnabled) {
							viewModel.getNewRoute();
						}
						// todo - add if
						viewModel.navigationInfo.setCurrentUserState(UserActionType.TYPE_WAIT_FOR_NEW_ROUTE);
					} else {
						Timber.e("WALK: " + distance);
						viewModel.navigationInfo.setCurrentUserState(UserActionType.TYPE_WALK);
					}
				}
			}
		}
		return true;
	}

	private boolean moveToNextStation(boolean isTransfer) {
		viewModel.navigationInfo.setCurrentNodeReached(false);
		if (isTransfer) {
			int vehicleIndex = viewModel.navigationInfo.getCurrentVehicleIndex();
			if (vehicleIndex < viewModel.currentRoute.getVehicles().size() - 1) {
				viewModel.navigationInfo.setCurrentVehicleIndex(vehicleIndex + 1);
				viewModel.navigationInfo.setCurrentVehicle(viewModel.currentRoute.getVehicles().get(vehicleIndex + 1));
				viewModel.navigationInfo.setCurrentNodeIndex(-1);
				viewModel.setLineIdAndLineCode(viewModel.navigationInfo.getCurrentVehicle().getLineId(), viewModel.navigationInfo.getCurrentVehicle().getLineCode());
			}
		}

		int nodeIndex = viewModel.navigationInfo.getCurrentNodeIndex();

		if (nodeIndex + 1 < viewModel.navigationInfo.getCurrentVehicle().getPath().size()) {
			viewModel.navigationInfo.setCurrentNodeIndex(nodeIndex + 1);
			viewModel.navigationInfo.setCurrentNode(viewModel.navigationInfo.getCurrentVehicle().getPath().get(nodeIndex + 1));

			if (viewModel.navigationInfo.getCurrentVehicle() != null) {
				setNextStationNames(getNextNodes(viewModel.navigationInfo.getCurrentNodeIndex()));
			}

			viewModel.navigationInfo.setCurrentNodeLocation(MapHelper.getCurrentNodeLocation(viewModel.navigationInfo.getCurrentNode()));
			binding.stopName.setText(viewModel.navigationInfo.getCurrentNode().getStationName());
			return true;
		} else {
			return false;
		}
	}

	public void setNextStationNames(List<Node> next) {
		switch (next.size()) {
			case 2:
				binding.nextFarStop.setVisibility(View.VISIBLE);
				binding.nextStop.setVisibility(View.VISIBLE);
				binding.nextFarStop.setText(next.get(1).getStationName());
				binding.nextStop.setText(next.get(0).getStationName());
				break;
			case 1:
				binding.nextFarStop.setVisibility(View.GONE);
				binding.nextStop.setVisibility(View.VISIBLE);
				binding.nextFarStop.setText("");
				binding.nextStop.setText(next.get(0).getStationName());
				break;
			default:
				binding.nextFarStop.setVisibility(View.GONE);
				binding.nextStop.setVisibility(View.GONE);
				binding.nextFarStop.setText("");
				binding.nextStop.setText("");
		}
	}

	private List<Node> getNextNodes(int currentNodeIndex) {
		return findNextNodes(currentNodeIndex, viewModel.navigationInfo.getCurrentVehicleIndex(), Constant.Navigation.NUMBER_OF_NEXT_STATIONS);
	}

	private List<Node> findNextNodes(int currentNodeIndex, int currentVehicleIndex, int numberOfRemaining) {
		int maxVehicleIndex = viewModel.currentRoute.getVehicles().size() - 1;
		if (currentVehicleIndex > maxVehicleIndex) {
			return new ArrayList<>();
		}

		List<Node> pathNodes = viewModel.currentRoute.getVehicles().get(currentVehicleIndex).getPath();
		List<Node> nextNodes = new ArrayList<>();

		int nodesAvailableInCurrentVehicle = (pathNodes.size() - currentNodeIndex) - 1;
		if (numberOfRemaining <= nodesAvailableInCurrentVehicle) {
			return pathNodes.subList(currentNodeIndex + 1, currentNodeIndex + numberOfRemaining + 1);
		} else {
			if (nodesAvailableInCurrentVehicle > 0) {
				nextNodes.addAll(pathNodes.subList(currentNodeIndex + 1, currentNodeIndex + nodesAvailableInCurrentVehicle + 1));
			}

			int remainingNodes = numberOfRemaining - nextNodes.size();
			if (remainingNodes > 0) {
				nextNodes.addAll(findNextNodes(0, currentVehicleIndex + 1, remainingNodes));
			}
			return nextNodes;
		}
	}

	public void onFinishReached() {
		binding.currentAction.setText(getString(R.string.nav_finish));
		binding.information.setText(getString(R.string.nav_finish_info));
		binding.distanceTime.setText(getString(R.string.nav_congratulations));
	}

	public void onFinishReachedFailed() {
		binding.currentAction.setText(getString(R.string.nav_finish_failed));
		binding.information.setText(getString(R.string.nav_finish_failed_info));
		binding.distanceTime.setText(getString(R.string.nav_sorry));
	}
}
