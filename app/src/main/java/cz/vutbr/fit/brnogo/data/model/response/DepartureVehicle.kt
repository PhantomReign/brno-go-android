package cz.vutbr.fit.brnogo.data.model.response

import android.view.View
import cz.vutbr.fit.brnogo.data.model.recyclerview.DepartureItem
import io.mironov.smuggler.AutoParcelable

data class DepartureVehicle(
		val lineName: String,
		val finalStationName: String,
		val barrierLess: Boolean,
		val timeMark: String) : AutoParcelable, DepartureItem {
	override fun getItemId(): String = lineName + timeMark

	fun getBarrierLessVisibility(): Int {
		return if (barrierLess) {
			View.VISIBLE
		} else {
			View.INVISIBLE
		}
	}

}

