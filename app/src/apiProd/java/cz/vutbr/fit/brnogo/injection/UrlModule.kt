package cz.vutbr.fit.brnogo.injection

import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import okhttp3.HttpUrl

@Module
class UrlModule {

	@Provides
	@Singleton
	fun url(): HttpUrl? {
		return HttpUrl.parse("http://localhost:8080/api/")
	}
}
