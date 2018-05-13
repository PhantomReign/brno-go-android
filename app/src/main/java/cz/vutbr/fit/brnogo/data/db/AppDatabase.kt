package cz.vutbr.fit.brnogo.data.db

import android.arch.persistence.room.Room
import android.content.Context
import cz.vutbr.fit.brnogo.data.model.response.Route
import cz.vutbr.fit.brnogo.data.model.response.Stop
import cz.vutbr.fit.brnogo.data.model.store.FavoriteStop
import cz.vutbr.fit.brnogo.data.model.store.Search
import cz.vutbr.fit.brnogo.injection.annotation.qualifier.ApplicationContext
import io.reactivex.Flowable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppDatabase @Inject constructor(
		@ApplicationContext context: Context) {

	private val dataBaseStop: StopsRoomDatabase =
			Room.databaseBuilder(context,
					StopsRoomDatabase::class.java,
					StopsRoomDatabase.DB_NAME)
					.fallbackToDestructiveMigration().build()

	private val dataBaseSearch: SearchRoomDatabase =
			Room.databaseBuilder(context,
					SearchRoomDatabase::class.java,
					SearchRoomDatabase.DB_NAME)
					.fallbackToDestructiveMigration().build()

	private val dataBaseRoute: RouteRoomDatabase =
			Room.databaseBuilder(context,
					RouteRoomDatabase::class.java,
					RouteRoomDatabase.DB_NAME)
					.fallbackToDestructiveMigration().build()

	private val dataBaseFavoriteStop: FavoriteStopRoomDatabase =
			Room.databaseBuilder(context,
					FavoriteStopRoomDatabase::class.java,
					FavoriteStopRoomDatabase.DB_NAME)
					.fallbackToDestructiveMigration().build()

	fun getAllStops(): Flowable<List<Stop>> {
		return dataBaseStop.stopsDao().getAll()
	}

	fun insertStops(stop: List<Stop>) {
		dataBaseStop.stopsDao().insertAll(stop)
	}

	fun insertStop(stop: Stop) {
		dataBaseStop.stopsDao().insert(stop)
	}

	fun getAllSearches(): Flowable<List<Search>> {
		return dataBaseSearch.searchDao().getAll()
	}

	fun insertSearches(search: List<Search>) {
		dataBaseSearch.searchDao().insertAll(search)
	}

	fun insertSearch(search: Search) {
		dataBaseSearch.searchDao().insert(search)
	}

	fun deleteSearch(search: Search) {
		dataBaseSearch.searchDao().delete(search)
	}

	fun getAllRoutes(): Flowable<List<Route>> {
		return dataBaseRoute.routeDao().getAll()
	}

	fun insertRoutes(routes: List<Route>) {
		dataBaseRoute.routeDao().insertAll(routes)
	}

	fun insertRoute(route: Route) {
		dataBaseRoute.routeDao().insert(route)
	}

	fun deleteRoute(route: Route) {
		dataBaseRoute.routeDao().delete(route)
	}

	fun getAllFavoriteStops(): Flowable<List<FavoriteStop>> {
		return dataBaseFavoriteStop.favoriteStopDao().getAll()
	}

	fun insertFavoriteStops(favoriteStops: List<FavoriteStop>) {
		dataBaseFavoriteStop.favoriteStopDao().insertAll(favoriteStops)
	}

	fun insertFavoriteStop(favoriteStop: FavoriteStop) {
		dataBaseFavoriteStop.favoriteStopDao().insert(favoriteStop)
	}

	fun deleteFavoriteStop(favoriteStop: FavoriteStop) {
		dataBaseFavoriteStop.favoriteStopDao().delete(favoriteStop)
	}
}