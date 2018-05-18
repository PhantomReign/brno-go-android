package cz.vutbr.fit.brnogo.data.store

import cz.vutbr.fit.brnogo.data.db.AppDatabase
import cz.vutbr.fit.brnogo.data.model.response.Route
import cz.vutbr.fit.brnogo.data.persistance.Persistence
import cz.vutbr.fit.brnogo.tools.constant.Constant
import io.reactivex.Flowable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Store Class containing method for getting stored routes from database.
 *
 */

@Singleton
class SavedRouteStore @Inject constructor(
		val persistence: Persistence,
		val database: AppDatabase) {

	fun getRoutes(): Flowable<List<Route>> {
		return database.getAllRoutes()
	}

	fun setRouteAsSaved(route: Route) {
		val savedRoutes: MutableList<String> =  persistence.get(Constant.Persistence.SAVED_ROUTE_KEYS, ArrayList())
		savedRoutes.add(route.id)
		persistence.put(Constant.Persistence.SAVED_ROUTE_KEYS, savedRoutes)
		route.saved = true
		database.insertRoute(route)
	}

	fun unsetRouteAsSaved(route: Route) {
		val savedRoutes: MutableList<String> =  persistence.get(Constant.Persistence.SAVED_ROUTE_KEYS, ArrayList())
		savedRoutes.remove(route.id)
		persistence.put(Constant.Persistence.SAVED_ROUTE_KEYS, savedRoutes)
		route.saved = false
		database.deleteRoute(route)
	}
}
