package cz.vutbr.fit.brnogo.data.store

import cz.vutbr.fit.brnogo.data.api.ApiManager
import cz.vutbr.fit.brnogo.data.model.response.Route
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Store Class containing method for getting directions from API ENDPOINT.
 *
 */

@Singleton
class DirectionStore @Inject constructor(
		val apiManager: ApiManager) {

	fun getDirections(startStationId: Int,
				  destinationStationId: Int,
				  dateTime: Long,
				  minTimeToMove: Int,
				  maxTransfers: Int): Single<Route> {
		return apiManager.getDirections(startStationId, destinationStationId, dateTime, minTimeToMove, maxTransfers)
	}
}

