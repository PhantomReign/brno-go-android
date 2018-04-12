package cz.vutbr.fit.brnogo.data.model.response

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import cz.vutbr.fit.brnogo.data.model.recyclerview.RouteItem
import io.mironov.smuggler.AutoParcelable

@Entity(tableName = "route")
data class Route(
		@PrimaryKey
		@ColumnInfo(name = "id")
		val id: String,

		@ColumnInfo(name = "start_station_id")
		val startStationId: Int,
		@ColumnInfo(name = "destination_station_id")
		val destinationStationId: Int,
		@ColumnInfo(name = "start_station_name")
		val startStationName: String,
		@ColumnInfo(name = "destination_station_name")
		val destinationStationName: String,

		@ColumnInfo(name = "route_time")
		val routeTime: Int,
		@ColumnInfo(name = "min_time_to_move")
		val minTimeToMove: Int,
		@ColumnInfo(name = "vehicles")
		val vehicles: ArrayList<Vehicle>,
		@ColumnInfo(name = "saved")
		var saved: Boolean) : AutoParcelable, RouteItem {
	override fun getItemId(): String {
		return id
	}
}
