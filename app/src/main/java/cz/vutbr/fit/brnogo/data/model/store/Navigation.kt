package cz.vutbr.fit.brnogo.data.model.store

import android.location.Location
import cz.vutbr.fit.brnogo.data.model.response.LiveVehicle
import cz.vutbr.fit.brnogo.data.model.response.Node
import cz.vutbr.fit.brnogo.data.model.response.Route
import cz.vutbr.fit.brnogo.data.model.response.Vehicle
import cz.vutbr.fit.brnogo.tools.constant.UserActionType

data class Navigation(
		var fasterRoute: Route? = null,

		var isInVehicle: Boolean = false,
		var nextStationId: Int = -1,

		var currentVehicle: Vehicle? = null,
		var currentVehicleIndex: Int = -1,
		var currentNode: Node? = null,
		var currentNodeIndex: Int = -1,
		var currentNodeLocation: Location? = null,
		var isCurrentNodeReached: Boolean = false,

		var currentUserLocation: Location? = null,
		var currentUserState: Int = UserActionType.TYPE_WALK,

		var lineId: Int = -1,
		var lineCode: Int = -1,
		var currentLiveVehicle: LiveVehicle? = null,
		var nextLiveVehicle: LiveVehicle? = null) {
}
