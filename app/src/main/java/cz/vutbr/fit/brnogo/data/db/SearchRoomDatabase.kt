package cz.vutbr.fit.brnogo.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import cz.vutbr.fit.brnogo.data.model.store.Search

/**
 * Database class.
 *
 */

@Database(entities = [(Search::class)], version = 5, exportSchema = false)
@TypeConverters(Converters::class)
abstract class SearchRoomDatabase : RoomDatabase() {

	companion object {
		const val DB_NAME = "search-database"
	}

	abstract fun searchDao(): SearchDao
}