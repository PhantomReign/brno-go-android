package cz.vutbr.fit.brnogo.data.api

import cz.vutbr.fit.brnogo.data.model.response.CurrentDeparture
import cz.vutbr.fit.brnogo.data.model.response.LiveVehicle
import cz.vutbr.fit.brnogo.data.model.response.Route
import cz.vutbr.fit.brnogo.data.model.response.Stop
import cz.vutbr.fit.brnogo.data.util.ResultTransformer
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Manager Class with methods for getting information from server.
 *
 */

@Singleton
class ApiManager @Inject constructor(
		val apiService: ApiService,
		val resultTransformer: ResultTransformer) {

	fun getStops(): Single<List<Stop>> {
		return apiService.getStops().compose(resultTransformer.transformData())
	}

	fun getDepartures(stationId: Int): Single<List<CurrentDeparture>> {
		return apiService.getDepartures(stationId).compose(resultTransformer.transformData())
	}

	fun getRoutes(startStationId: Int,
				  destinationStationId: Int,
				  dateTime: Long,
				  minTimeToMove: Int,
				  maxTransfers: Int,
				  liveDataEnabled: Boolean,
				  routeLimit: Int): Single<List<Route>> {
		return apiService.getRoutes(startStationId, destinationStationId, dateTime, minTimeToMove, maxTransfers, liveDataEnabled, routeLimit).compose(resultTransformer.transformData())
	}

	fun getDirections(startStationId: Int,
					  destinationStationId: Int,
					  dateTime: Long,
					  minTimeToMove: Int,
					  maxTransfers: Int): Single<Route> {
		return apiService.getDirections(startStationId, destinationStationId, dateTime, minTimeToMove, maxTransfers).compose(resultTransformer.transformData())
	}

	fun getVehicle(lineCode: Int, lineId: Int): Single<LiveVehicle> {
		return apiService.getVehicle(lineCode, lineId).compose(resultTransformer.transformData())
	}
}
