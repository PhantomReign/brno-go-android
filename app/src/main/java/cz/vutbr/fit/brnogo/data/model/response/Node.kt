package cz.vutbr.fit.brnogo.data.model.response

import cz.vutbr.fit.brnogo.data.model.recyclerview.RouteItem
import io.mironov.smuggler.AutoParcelable

data class Node(

		val stationId: Int,
		val stationName: String,
		val stationZone: Int,
		val stopId: Int,
		val stopLongitude: Double,
		val stopLatitude: Double,
		val stopDescription: String,

		val timeOfArrival: Long,
		val timeOfDeparture: Long,
		val formattedTimeOfArrival: String,
		val formattedTimeOfDeparture: String) : AutoParcelable, RouteItem {
	override fun getItemId(): String {
		return stationName + stopDescription + formattedTimeOfArrival + formattedTimeOfDeparture + stopLongitude.toString() + stopLatitude.toString()
	}
}
