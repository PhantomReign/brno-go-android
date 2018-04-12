package cz.vutbr.fit.brnogo.data.api

import cz.vutbr.fit.brnogo.data.model.response.CurrentDeparture
import cz.vutbr.fit.brnogo.data.model.response.Route
import cz.vutbr.fit.brnogo.data.model.response.Stop
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
	@GET("stations")
	fun getStops(): Single<Response<List<Stop>>>

	@GET("departures")
	fun getDepartures(@Query("sId") id: Int): Single<Response<List<CurrentDeparture>>>

	@GET("routes")
	fun getRoutes(@Query("sId") startStationId: Int,
				  @Query("dId") destinationStationId: Int,
				  @Query("uLat") userLatitude: Double,
				  @Query("uLon") userLongitude: Double,
				  @Query("dTime") dateTime: Long,
				  @Query("mTime") minTimeToMove: Int,
				  @Query("mTrans") maxTransfers: Int,
				  @Query("live") liveDataEnabled: Boolean,
				  @Query("lim") routeLimit: Int): Single<Response<List<Route>>>
}
