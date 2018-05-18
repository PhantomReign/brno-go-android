package cz.vutbr.fit.brnogo.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import cz.vutbr.fit.brnogo.data.model.response.Stop

/**
 * Database class.
 *
 */

@Database(entities = [(Stop::class)], version = 5, exportSchema = false)
abstract class StopsRoomDatabase : RoomDatabase() {

	companion object {
		const val DB_NAME = "stops-database"
	}

	abstract fun stopsDao(): StopsDao
}