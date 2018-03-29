package cz.vutbr.fit.brnogo.data.model.response

import cz.vutbr.fit.brnogo.data.model.recyclerview.RouteItem
import io.mironov.smuggler.AutoParcelable

data class Route(
		val startStationId: Int,
		val destinationStationId: Int,
		val startStationName: String,
		val destinationStationName: String,

		val routeTime: Int,
		val minTimeToMove: Int,
		val vehicles: ArrayList<Vehicle>) : AutoParcelable, RouteItem {
	override fun getItemId(): String {
		return startStationName + destinationStationName + routeTime.toString() + vehicles.size.toString()
	}
}
