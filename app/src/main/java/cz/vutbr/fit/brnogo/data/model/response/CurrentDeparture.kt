package cz.vutbr.fit.brnogo.data.model.response

import cz.vutbr.fit.brnogo.data.model.recyclerview.AdapterItem
import io.mironov.smuggler.AutoParcelable

data class CurrentDeparture(
		val stationId: String,
		val direction: String,
		val vehicles: List<DepartureVehicle>) : AutoParcelable, AdapterItem {
	override fun getItemId(): String = stationId

}

