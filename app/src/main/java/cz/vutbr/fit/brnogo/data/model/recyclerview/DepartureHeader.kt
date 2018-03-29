package cz.vutbr.fit.brnogo.data.model.recyclerview

data class DepartureHeader(
		val stationId: String,
		val direction: String) : DepartureItem {

	override fun getItemId(): String = stationId + direction

}