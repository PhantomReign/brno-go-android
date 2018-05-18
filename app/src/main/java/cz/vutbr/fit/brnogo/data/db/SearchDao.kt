package cz.vutbr.fit.brnogo.data.db

import android.arch.persistence.room.*
import cz.vutbr.fit.brnogo.data.model.store.Search
import io.reactivex.Flowable

/**
 * Interface containing specific methods for database manipulation.
 *
 */

@Dao
interface SearchDao {

	@Query("SELECT * FROM search ORDER BY id")
	fun getAll(): Flowable<List<Search>>

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun insertAll(search: List<Search>)

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun insert(search: Search)

	@Delete
	fun delete(search: Search)
}