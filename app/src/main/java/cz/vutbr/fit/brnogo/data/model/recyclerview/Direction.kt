package cz.vutbr.fit.brnogo.data.model.recyclerview

import android.support.annotation.DrawableRes
import cz.vutbr.fit.brnogo.R
import cz.vutbr.fit.brnogo.tools.constant.DirectionType

data class Direction(
		val direction: String,
		val type: Int) : RouteItem {

	override fun getItemId(): String = direction + type.toString()

	@DrawableRes
	fun getIcon(): Int = when (type) {
		DirectionType.TYPE_WALK -> R.drawable.ic_directions_walk
		DirectionType.TYPE_BOARD_BUS -> R.drawable.ic_bus
		DirectionType.TYPE_BOARD_TRAM -> R.drawable.ic_tram
		DirectionType.TYPE_BOARD_TROLLEY -> R.drawable.ic_trolley
		DirectionType.TYPE_STAY -> R.drawable.ic_time
		DirectionType.TYPE_EXIT_BUS,
		DirectionType.TYPE_EXIT_TRAM,
		DirectionType.TYPE_EXIT_TROLLEY -> R.drawable.ic_exit
		else -> R.drawable.ic_check
	}

	fun getColor(): Int = when (type) {
		DirectionType.TYPE_BOARD_BUS -> R.color.green
		DirectionType.TYPE_BOARD_TRAM -> R.color.blue
		DirectionType.TYPE_BOARD_TROLLEY -> R.color.orange
		DirectionType.TYPE_EXIT_BUS -> R.color.green
		DirectionType.TYPE_EXIT_TRAM -> R.color.blue
		DirectionType.TYPE_EXIT_TROLLEY -> R.color.orange
		else -> R.color.colorPrimary
	}

}