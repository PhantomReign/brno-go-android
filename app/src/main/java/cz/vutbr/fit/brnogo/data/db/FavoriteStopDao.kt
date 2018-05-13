package cz.vutbr.fit.brnogo.data.db

import android.arch.persistence.room.*
import cz.vutbr.fit.brnogo.data.model.store.FavoriteStop
import io.reactivex.Flowable

@Dao
interface FavoriteStopDao {

	@Query("SELECT * FROM favoritestop ORDER BY id")
	fun getAll(): Flowable<List<FavoriteStop>>

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun insertAll(favoriteStops: List<FavoriteStop>)

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun insert(favoriteStop: FavoriteStop)

	@Delete
	fun delete(favoriteStop: FavoriteStop)
}