package cz.vutbr.fit.brnogo.data.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import cz.vutbr.fit.brnogo.data.model.response.Stop
import io.reactivex.Flowable

/**
 * Interface containing specific methods for database manipulation.
 *
 */

@Dao
interface StopsDao {

	@Query("SELECT * FROM stop ORDER BY id")
	fun getAll(): Flowable<List<Stop>>

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun insertAll(stop: List<Stop>)

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun insert(stop: Stop)
}
