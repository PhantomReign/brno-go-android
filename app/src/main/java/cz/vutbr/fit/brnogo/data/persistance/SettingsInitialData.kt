package cz.vutbr.fit.brnogo.data.persistance

import android.content.Context
import cz.vutbr.fit.brnogo.R
import cz.vutbr.fit.brnogo.injection.annotation.qualifier.ApplicationContext
import cz.vutbr.fit.brnogo.tools.constant.Constant
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Class containing methods for initializing data into Persistence.
 *
 */

@Singleton
class SettingsInitialData @Inject constructor(
		val persistence: Persistence,
		@ApplicationContext val context: Context) {

	val isDataInitialized: Boolean
		get() = persistence.get(Constant.Preference.DATA_INITIALIZED, false)

	val isFirstStopSyncDone: Boolean
		get() = persistence.get(Constant.Preference.FIRST_STOP_SYNC_DONE, false)

	fun setInitialData() {
		persistence.put(context.getString(R.string.settings_key_screen), "0")
		persistence.put(context.getString(R.string.settings_key_limit), "3")
		persistence.put(context.getString(R.string.settings_key_implicit_enter), false)
		persistence.put(Constant.Preference.DATA_INITIALIZED, true)
	}

	fun setFirstStopSyncDone() {
		persistence.put(Constant.Preference.FIRST_STOP_SYNC_DONE, true)
	}

	fun unsetFirstStopSyncDone() {
		persistence.put(Constant.Preference.FIRST_STOP_SYNC_DONE, false)
	}
}
