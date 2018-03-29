package cz.vutbr.fit.brnogo.data.api

import cz.vutbr.fit.brnogo.data.model.response.CurrentDeparture
import cz.vutbr.fit.brnogo.data.model.response.Route
import cz.vutbr.fit.brnogo.data.model.response.Stop
import javax.inject.Inject
import javax.inject.Singleton

import cz.vutbr.fit.brnogo.data.util.ResultTransformer
import io.reactivex.Single

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
				  userLatitude: Double,
				  userLongitude: Double,
				  dateTime: Long,
				  minTimeToMove: Int,
				  maxTransfers: Int,
				  liveDataEnabled: Boolean,
				  routeLimit: Int): Single<List<Route>> {
		return apiService.getRoutes(startStationId, destinationStationId, userLatitude, userLongitude, dateTime, minTimeToMove, maxTransfers, liveDataEnabled, routeLimit).compose(resultTransformer.transformData())
	}
}
