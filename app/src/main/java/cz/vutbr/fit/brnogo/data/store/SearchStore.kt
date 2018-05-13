package cz.vutbr.fit.brnogo.data.store

import cz.vutbr.fit.brnogo.data.db.AppDatabase
import cz.vutbr.fit.brnogo.data.model.store.Search
import cz.vutbr.fit.brnogo.data.persistance.Persistence
import cz.vutbr.fit.brnogo.tools.constant.Constant
import io.reactivex.Flowable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchStore @Inject constructor(
		val persistence: Persistence,
		val database: AppDatabase) {

	fun getSearches(): Flowable<List<Search>> {
		return database.getAllSearches()
	}

	fun setSearchAsFavorite(search: Search) {
		val favoriteSearches: MutableList<String> =  persistence.get(Constant.Persistence.FAVORITE_ROUTE_KEYS, ArrayList())
		favoriteSearches.add(search.id)
		persistence.put(Constant.Persistence.FAVORITE_ROUTE_KEYS, favoriteSearches)
		search.favorite = true
		database.insertSearch(search)
	}

	fun unsetSearchAsFavorite(search: Search) {
		val favoriteSearches: MutableList<String> =  persistence.get(Constant.Persistence.FAVORITE_ROUTE_KEYS, ArrayList())
		favoriteSearches.remove(search.id)
		persistence.put(Constant.Persistence.FAVORITE_ROUTE_KEYS, favoriteSearches)
		search.favorite = false
		database.deleteSearch(search)
	}
}