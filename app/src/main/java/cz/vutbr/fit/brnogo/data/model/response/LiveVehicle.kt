package cz.vutbr.fit.brnogo.data.model.response

data class LiveVehicle(
		val routeId: Int,
		val carNum: Int,
		val lineId: Int,
		val lastPlatformId: Int,
		val isBarrierLess: Boolean,
		val longitude: Float,
		val latitude: Float,
		val delay: Short) {
}