package cz.vutbr.fit.brnogo.ui.route.navigation.map;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.arch.lifecycle.ViewModelProviders;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.sdoward.rxgooglemap.MapObservableProvider;

import javax.inject.Inject;

import cz.vutbr.fit.brnogo.R;
import cz.vutbr.fit.brnogo.databinding.FragmentMapBinding;
import cz.vutbr.fit.brnogo.tools.PermissionChecker;
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

@RuntimePermissions
public class MapFragment extends BaseFragment<MapViewModel, FragmentMapBinding> implements MapView {

	@Inject MapViewModelFactory viewModelFactory;
	@Inject ReactiveLocationProvider reactiveLocationProvider;
	@Inject PermissionChecker permissionChecker;

	private MapObservableProvider mapProvider;
	private Location defaultLocation = getDefaultLocation();
	private Disposable mapSubscription = DisposableHelper.DISPOSED;
	private boolean canHideFab = true;

	private BottomSheetBehavior bottomSheetBehavior;

	public static MapFragment newInstance() {
		return new MapFragment();
	}

	public static Location getDefaultLocation() {
		Location location = new Location("default");
		location.setLatitude(49.195);
		location.setLongitude(16.608);
		return location;
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

		bottomSheetBehavior = BottomSheetBehavior.from(binding.mapBottomSheet);
		bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
		bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
			@Override
			public void onStateChanged(@NonNull View bottomSheet, int newState) {
				switch (newState) {
					case BottomSheetBehavior.STATE_EXPANDED:
						showFab();
						break;
					case BottomSheetBehavior.STATE_SETTLING:
						if (binding.mapBottomSheetNavigationButton.isShown() && canHideFab) {
							hideFab();
							canHideFab = false;
						}
						break;
					case BottomSheetBehavior.STATE_HIDDEN:
						if (binding.mapBottomSheetNavigationButton.isShown()) {
							hideFab();
						}
						canHideFab = true;
						break;
				}
			}

			@Override
			public void onSlide(@NonNull View bottomSheet, float slideOffset) {
			}
		});

		MapFragmentPermissionsDispatcher.afterLocationGrantedWithPermissionCheck(this);
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
	public void showNavigation() {
		//Canteen canteen = viewModel.getCanteen();
		//Uri uri = Uri.parse(String.format(Locale.ENGLISH, "geo:0,0?q=%s+%s+%s", canteen.getStreet(), canteen.getCity(), canteen.getZip()));
		//startActivity(new Intent(Intent.ACTION_VIEW, uri));
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
					GoogleMap map = locationGoogleMapPair.second;

					LatLng convertedLocation = new LatLng(location.getLatitude(), location.getLongitude());
					map.moveCamera(CameraUpdateFactory.newLatLngZoom(convertedLocation, 14));

					setMap(map);
				})
				.map(locationGoogleMapPair -> locationGoogleMapPair.second)
				.doOnNext(__ -> observeForCanteens())
				.flatMap(__ -> mapProvider.getCameraMoveStartedObservable())
				.filter(reason -> reason == GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE)
				.subscribe();
	}

	private void initMapWithoutLocation() {
		mapSubscription = mapProvider.getMapReadyObservable()
				.doOnNext(this::setMap)
				.doOnNext(__ -> observeForCanteens())
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

	private void observeForCanteens() {
		viewModel.getItems().observe(this, canteens -> {
			if (canteens == null) {
				return;
			}
		});
	}

	private void hideFab() {
		binding.mapBottomSheetNavigationButton.animate()
				.scaleX(0f)
				.scaleY(0f)
				.alpha(0f)
				.setDuration(200)
				.setInterpolator(new FastOutLinearInInterpolator())
				.setListener(new AnimatorListenerAdapter() {
					@Override
					public void onAnimationEnd(Animator animation) {
						binding.mapBottomSheetNavigationButton.setVisibility(View.INVISIBLE);
					}
				});
	}

	private void showFab() {
		if (binding.mapBottomSheetNavigationButton.getVisibility() != View.VISIBLE) {
			// If the view isn't visible currently, we'll animate it from a single pixel
			binding.mapBottomSheetNavigationButton.setAlpha(0f);
			binding.mapBottomSheetNavigationButton.setScaleY(0f);
			binding.mapBottomSheetNavigationButton.setScaleX(0f);
		}

		binding.mapBottomSheetNavigationButton.animate()
				.scaleX(1f)
				.scaleY(1f)
				.alpha(1f)
				.setDuration(200)
				.setInterpolator(new LinearOutSlowInInterpolator())
				.setListener(new AnimatorListenerAdapter() {
					@Override
					public void onAnimationStart(Animator animation) {
						binding.mapBottomSheetNavigationButton.setVisibility(View.VISIBLE);
					}
				});
	}

	@Override
	public void onDestroyView() {
		mapSubscription.dispose();
		super.onDestroyView();
	}
}
