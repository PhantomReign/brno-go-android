package cz.vutbr.fit.brnogo.data.db

import android.arch.persistence.room.*
import cz.vutbr.fit.brnogo.data.model.response.Route
import io.reactivex.Flowable

@Dao
interface RouteDao {

	@Query("SELECT * FROM route ORDER BY id")
	fun getAll(): Flowable<List<Route>>

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun insertAll(routes: List<Route>)

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun insert(route: Route)

	@Delete
	fun delete(route: Route)
}