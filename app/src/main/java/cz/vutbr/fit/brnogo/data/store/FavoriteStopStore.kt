package cz.vutbr.fit.brnogo.data.store

import cz.vutbr.fit.brnogo.data.db.AppDatabase
import cz.vutbr.fit.brnogo.data.model.store.FavoriteStop
import cz.vutbr.fit.brnogo.data.persistance.Persistence
import cz.vutbr.fit.brnogo.tools.constant.Constant
import io.reactivex.Flowable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Store Class containing methods for getting favorite stops from database.
 *
 */

@Singleton
class FavoriteStopStore @Inject constructor(
		val persistence: Persistence,
		val database: AppDatabase) {

	fun getFavoriteStops(): Flowable<List<FavoriteStop>> {
		return database.getAllFavoriteStops()
	}

	fun setFavoriteStopAsFavorite(stop: FavoriteStop) {
		val favoriteStops: MutableList<String> =  persistence.get(Constant.Persistence.FAVORITE_STOP_KEYS, ArrayList())
		favoriteStops.add(stop.id)
		persistence.put(Constant.Persistence.FAVORITE_STOP_KEYS, favoriteStops)
		stop.favorite = true
		database.insertFavoriteStop(stop)
	}

	fun unsetFavoriteStopAsFavorite(stop: FavoriteStop) {
		val favoriteStops: MutableList<String> =  persistence.get(Constant.Persistence.FAVORITE_STOP_KEYS, ArrayList())
		favoriteStops.remove(stop.id)
		persistence.put(Constant.Persistence.FAVORITE_STOP_KEYS, favoriteStops)
		stop.favorite = false
		database.deleteFavoriteStop(stop)
	}
}