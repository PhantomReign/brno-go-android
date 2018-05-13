package cz.vutbr.fit.brnogo.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import cz.vutbr.fit.brnogo.data.model.response.Route

@Database(entities = [(Route::class)], version = 5, exportSchema = false)
@TypeConverters(Converters::class)
abstract class RouteRoomDatabase : RoomDatabase() {

	companion object {
		const val DB_NAME = "route-database"
	}

	abstract fun routeDao(): RouteDao
}
