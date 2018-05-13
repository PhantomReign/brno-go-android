package cz.vutbr.fit.brnogo.data.store

import cz.vutbr.fit.brnogo.data.api.ApiManager
import cz.vutbr.fit.brnogo.data.model.response.LiveVehicle
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VehicleStore @Inject constructor(
		val apiManager: ApiManager) {

	fun getVehicle(lineCode: Int, lineId: Int): Single<LiveVehicle> {
		return apiManager.getVehicle(lineCode, lineId)
	}
}