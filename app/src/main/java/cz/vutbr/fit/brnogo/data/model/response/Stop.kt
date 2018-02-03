package cz.vutbr.fit.brnogo.data.model.response

import cz.vutbr.fit.brnogo.R
import cz.vutbr.fit.brnogo.data.model.recyclerview.AdapterItem
import cz.vutbr.fit.brnogo.tools.constant.StopType
import io.mironov.smuggler.AutoParcelable


data class Stop(
		private val id: String,
		val name: String,
		val type: String) : AutoParcelable, AdapterItem {
	override fun getId(): String = id

	fun getIcon(): Pair<Int, Int> = when (type) {
		StopType.TYPE_TRAM -> Pair(R.drawable.ic_tram, R.drawable.shape_oval_red)
		else -> Pair(R.drawable.ic_bus, R.drawable.shape_oval_blue)
	}
}
