package cz.vutbr.fit.brnogo.data.api

import cz.vutbr.fit.brnogo.data.model.response.CurrentDeparture
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
}
