package cz.vutbr.fit.brnogo.injection;

import android.content.Context;

import com.google.gson.Gson;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import cz.vutbr.fit.brnogo.data.api.ApiService;
import cz.vutbr.fit.brnogo.injection.annotation.qualifier.ApplicationContext;
import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

@Module
public class NetModule {

	@Provides
	@Named("loggingInterceptor")
	@Singleton
	public Interceptor loggingInterceptor() {
		HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(message -> Timber.tag("OkHttp").d(message));
		loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
		return loggingInterceptor;
	}

	@Provides
	@Named("authInterceptor")
	@Singleton
	public Interceptor interceptor() {
		return chain -> {
			Request.Builder request = chain.request().newBuilder();
			return chain.proceed(request.build());
		};
	}

	@Provides
	@Singleton
	public OkHttpClient okHttpClient(Cache cache, @Named("loggingInterceptor") Interceptor interceptor) {
		return new OkHttpClient.Builder()
				.cache(cache)
				.addInterceptor(interceptor)
				.readTimeout(60, TimeUnit.SECONDS)
				.writeTimeout(60, TimeUnit.SECONDS)
				.build();
	}

	@Provides
	@Singleton
	public Cache cache(@Named("okHttpCache") File cacheDir) {
		return new Cache(cacheDir, 10 * 1024 * 1024);
	}

	@Provides
	@Named("okHttpCache")
	@Singleton
	public File okHttpCache(@ApplicationContext Context context) {
		return context.getCacheDir();
	}

	@Provides
	@Singleton
	static ApiService apiService(OkHttpClient okHttpClient, Gson gson, HttpUrl apiEndpoint, @Named("authInterceptor") Interceptor interceptor) {
		OkHttpClient newClient = okHttpClient.newBuilder()
				.addInterceptor(interceptor)
				.build();

		return new Retrofit.Builder()
				.client(newClient)
				.addConverterFactory(GsonConverterFactory.create(gson))
				.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
				.baseUrl(apiEndpoint)
				.build()
				.create(ApiService.class);
	}
}
