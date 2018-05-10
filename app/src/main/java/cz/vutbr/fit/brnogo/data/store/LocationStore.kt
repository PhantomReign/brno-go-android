package cz.vutbr.fit.brnogo.data.store

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.google.android.gms.location.LocationRequest
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

	var locationRequest = LocationRequest.create().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY).setFastestInterval(1000).setInterval(1000)

	@SuppressLint("MissingPermission")
	fun getLocationObservable(): Maybe<Location> {
		return if (permissionChecker.hasLocation()) {
			reactiveLocationProvider.getUpdatedLocation(locationRequest).firstElement()
		} else Maybe.empty()
	}
}

