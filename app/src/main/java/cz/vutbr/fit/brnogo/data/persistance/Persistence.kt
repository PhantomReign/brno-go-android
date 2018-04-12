package cz.vutbr.fit.brnogo.data.persistance

import android.content.Context
import com.google.gson.Gson
import com.orhanobut.hawk.GsonParser
import com.orhanobut.hawk.Hawk
import cz.vutbr.fit.brnogo.injection.annotation.qualifier.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Persistence @Inject constructor(
        @ApplicationContext context: Context,
        gson: Gson) {

    init {
        val hawkBuilder = Hawk
                .init(context)
                .setParser(GsonParser(gson))
        hawkBuilder.build()
    }

    fun <T> put(key: String, value: T?): Boolean = Hawk.put(key, value)

    fun <T> get(key: String, defaultValue: T): T = Hawk.get(key, defaultValue)

    fun <T> get(key: String): T = Hawk.get(key)

    fun delete(key: String): Boolean = Hawk.delete(key)
}
