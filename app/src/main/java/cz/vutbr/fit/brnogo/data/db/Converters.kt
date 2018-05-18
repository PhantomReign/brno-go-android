package cz.vutbr.fit.brnogo.data.db

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import cz.vutbr.fit.brnogo.data.model.response.Node
import cz.vutbr.fit.brnogo.data.model.response.Stop
import cz.vutbr.fit.brnogo.data.model.response.Vehicle

/**
 * Class containing methods for converting object to json.
 *
 */

class Converters {

	@TypeConverter
	fun stringToStop(data: String): Stop {
		return Gson().fromJson(data, object : TypeToken<Stop>() {
		}.type)
	}

	@TypeConverter
	fun stopToString(stop: Stop): String {
		val gson = Gson()
		return gson.toJson(stop)
	}

	@TypeConverter
	fun stringToVehicles(data: String): ArrayList<Vehicle> {
		return Gson().fromJson(data, object : TypeToken<ArrayList<Vehicle>>() {
		}.type)
	}

	@TypeConverter
	fun vehiclesToString(vehicles: ArrayList<Vehicle>): String {
		val gson = Gson()
		return gson.toJson(vehicles)
	}

	@TypeConverter
	fun stringToNodes(data: String): List<Node> {
		return Gson().fromJson(data, object : TypeToken<List<Node>>() {
		}.type)
	}

	@TypeConverter
	fun nodesToString(nodes: List<Node>): String {
		val gson = Gson()
		return gson.toJson(nodes)
	}
}
