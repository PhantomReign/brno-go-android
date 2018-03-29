package cz.vutbr.fit.brnogo.data.model.response

import android.support.annotation.DrawableRes
import cz.vutbr.fit.brnogo.R
import cz.vutbr.fit.brnogo.data.model.recyclerview.RouteItem
import cz.vutbr.fit.brnogo.tools.constant.VehicleType
import io.mironov.smuggler.AutoParcelable

data class Vehicle(
		val lineCode: Int ,
		val lineId: Int,
		val type: Int,
		val delay: Int,
		val path: List<Node>) : AutoParcelable, RouteItem {
	override fun getItemId(): String {
		return lineCode.toString() + lineId.toString() + type.toString() + path.size.toString()
	}

	@DrawableRes
	fun getIcon(): Int = when (type) {
		VehicleType.TYPE_BUS -> R.drawable.ic_bus
		VehicleType.TYPE_TRAM -> R.drawable.ic_tram
		else -> R.drawable.ic_trolley
	}

	fun getColor(): Int = when (type) {
		VehicleType.TYPE_BUS -> R.color.green
		VehicleType.TYPE_TRAM -> R.color.blue
		else -> R.color.orange
	}

}
