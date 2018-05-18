package cz.vutbr.fit.brnogo.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import cz.vutbr.fit.brnogo.data.model.store.FavoriteStop

/**
 * Database class.
 *
 */

@Database(entities = [(FavoriteStop::class)], version = 5, exportSchema = false)
@TypeConverters(Converters::class)
abstract class FavoriteStopRoomDatabase : RoomDatabase() {

	companion object {
		const val DB_NAME = "favoritestop-database"
	}

	abstract fun favoriteStopDao(): FavoriteStopDao
}