package cz.vutbr.fit.brnogo.data.store

import cz.vutbr.fit.brnogo.data.api.ApiManager
import cz.vutbr.fit.brnogo.data.model.response.CurrentDeparture
import cz.vutbr.fit.brnogo.data.model.response.Route
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rave on 26.03.2018.
 */

@Singleton
class RouteStore @Inject constructor(
		val apiManager: ApiManager) {

	fun getRoutes(startStationId: Int,
				  destinationStationId: Int,
				  userLatitude: Double,
				  userLongitude: Double,
				  dateTime: Long,
				  minTimeToMove: Int,
				  maxTransfers: Int,
				  liveDataEnabled: Boolean,
				  routeLimit: Int): Single<List<Route>> {
		return apiManager.getRoutes(startStationId, destinationStationId, userLatitude, userLongitude, dateTime, minTimeToMove, maxTransfers, liveDataEnabled, routeLimit)
	}
}
