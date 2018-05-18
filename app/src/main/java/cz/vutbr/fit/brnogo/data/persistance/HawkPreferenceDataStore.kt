package cz.vutbr.fit.brnogo.data.persistance

import android.support.v7.preference.PreferenceDataStore

import javax.inject.Inject
import javax.inject.Singleton

/**
 * Preference Data Store Class. Contains methods for manipulating Persistence.
 *
 */

@Singleton
class HawkPreferenceDataStore @Inject
constructor(val persistence: Persistence) : PreferenceDataStore() {

	override fun putString(key: String, value: String?) {
		persistence.put(key, value)
	}

	override fun putStringSet(key: String, values: Set<String>?) {
		persistence.put(key, values)
	}

	override fun putInt(key: String, value: Int) {
		persistence.put(key, value)
	}

	override fun putLong(key: String, value: Long) {
		persistence.put(key, value)
	}

	override fun putFloat(key: String, value: Float) {
		persistence.put(key, value)
	}

	override fun putBoolean(key: String, value: Boolean) {
		persistence.put(key, value)
	}

	override fun getString(key: String, defValue: String?): String {
		return if (defValue != null) {
			persistence.get(key, defValue)
		} else {
			persistence.get(key)
		}
	}

	override fun getStringSet(key: String, defValues: Set<String>?): Set<String> {
		return if (defValues != null) {
			persistence.get(key, defValues)
		} else {
			persistence.get(key)
		}
	}

	override fun getInt(key: String, defValue: Int): Int {
		return persistence.get(key, defValue)
	}

	override fun getLong(key: String, defValue: Long): Long {
		return persistence.get(key, defValue)
	}

	override fun getFloat(key: String, defValue: Float): Float {
		return persistence.get(key, defValue)
	}

	override fun getBoolean(key: String, defValue: Boolean): Boolean {
		return persistence.get(key, defValue)
	}
}
