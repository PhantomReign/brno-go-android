package cz.vutbr.fit.brnogo.data.store

import com.jakewharton.rxrelay2.BehaviorRelay
import cz.vutbr.fit.brnogo.data.api.ApiManager
import cz.vutbr.fit.brnogo.data.db.AppDatabase
import cz.vutbr.fit.brnogo.data.model.response.Stop
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StopStore @Inject constructor(
		val apiManager: ApiManager,
		val dataBase: AppDatabase) {

	private val syncStatus: BehaviorRelay<Boolean> = BehaviorRelay.createDefault(false)

	fun getStops(): Flowable<List<Stop>> {
		return dataBase.getAllStops()
	}

	fun getStopsSyncStatus(): Observable<Boolean> = syncStatus

	fun syncStops(): Single<List<Stop>> {
		return apiManager.getStops()
				.map { stops -> dataBase.insertStops(stops)
					stops
				}
	}

	fun setStopsSyncStatus(isSyncing: Boolean) {
		syncStatus.accept(isSyncing)
	}
}
