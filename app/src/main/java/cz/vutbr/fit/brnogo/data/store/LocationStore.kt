package cz.vutbr.fit.brnogo.data.store

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.google.android.gms.location.LocationRequest
import cz.vutbr.fit.brnogo.R
import cz.vutbr.fit.brnogo.injection.annotation.qualifier.ApplicationContext
import cz.vutbr.fit.brnogo.tools.PermissionChecker
import io.reactivex.Maybe
import pl.charmas.android.reactivelocation2.ReactiveLocationProvider
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationStore @Inject constructor(
		@ApplicationContext val context: Context,
		val reactiveLocationProvider: ReactiveLocationProvider,
		val permissionChecker: PermissionChecker) {

	@SuppressLint("MissingPermission")
	fun getLastKnowLocationObservable(): Maybe<Location> {
		return if (permissionChecker.hasLocation()) {
			reactiveLocationProvider.lastKnownLocation.singleElement()
		} else Maybe.empty()
	}

	fun getDistanceToLocationObservable(finalLocation: Location): Maybe<String> {
		return getLastKnowLocationObservable()
				.map { currentLocation -> getDistance(currentLocation, finalLocation) }
	}

	@SuppressLint("MissingPermission")
	fun getLocationObservable(): Maybe<Location> {
		return if (permissionChecker.hasLocation()) {
			//val locationRequest = LocationRequest.create().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
			//reactiveLocationProvider.getUpdatedLocation(locationRequest).firstElement()
			reactiveLocationProvider.lastKnownLocation.singleElement()
		} else Maybe.empty()
	}

	/**
	 * Returns string in format:
	 * %s m - for distances < 1000m
	 * %s km - for distances > 1000m
	 * null - if current location is not instantly available
	 */
	fun getDistance(from: Location, to: Location): String {
		var distance = from.distanceTo(to)
		return if (distance > 1000) {
			distance /= 1000
			context.getString(R.string.km, Math.round(distance))
		} else {
			context.getString(R.string.m, Math.round(distance))
		}
	}
}

