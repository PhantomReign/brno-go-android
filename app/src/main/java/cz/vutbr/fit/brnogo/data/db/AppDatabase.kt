package cz.vutbr.fit.brnogo.data.db

import android.arch.persistence.room.Room
import android.content.Context
import cz.vutbr.fit.brnogo.data.model.response.Stop
import io.reactivex.Flowable
import javax.inject.Inject
import javax.inject.Singleton
import cz.vutbr.fit.brnogo.injection.annotation.qualifier.ApplicationContext


@Singleton
class AppDatabase @Inject constructor(
		@ApplicationContext context: Context) {

	private val dataBase: StopsRoomDatabase =
			Room.databaseBuilder(context,
					StopsRoomDatabase::class.java,
					StopsRoomDatabase.DB_NAME)
					.fallbackToDestructiveMigration().build()

	fun getAllStops(): Flowable<List<Stop>> {
		return dataBase.stopsDao().getAll()
	}

	fun insertStops(stop: List<Stop>) {
		dataBase.stopsDao().insertAll(stop)
	}

	fun insertStop(stop: Stop) {
		dataBase.stopsDao().insert(stop)
	}
}