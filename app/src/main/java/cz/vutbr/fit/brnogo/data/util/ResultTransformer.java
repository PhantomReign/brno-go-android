package cz.vutbr.fit.brnogo.data.util;

import com.google.gson.Gson;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import cz.vutbr.fit.brnogo.data.model.response.Error;
import cz.vutbr.fit.brnogo.tools.constant.Constant;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.SingleTransformer;
import retrofit2.Response;

/**
 * Result Transformer Class containing methods for transforming response.
 */

@Singleton
public class ResultTransformer {

	@Inject Gson gson;

	@Inject
	public ResultTransformer() {
	}

	public <T> SingleTransformer<Response<T>, T> transformData() {
		return responseObservable -> responseObservable.flatMap(response -> {
			if (!response.isSuccessful()) {
				return Single.error(new RuntimeException(response.errorBody().toString()));
			}
			return Single.just(response.body());
		});
	}

	public Completable transformEmptyData(Response<Void> response) {
		if (!response.isSuccessful()) {
			Error error;
			try {
				error = gson.fromJson(response.errorBody().string(), Error.class);
			} catch (IOException e) {
				return Completable.error(new ApiException(Constant.ErrorCode.UNKNOWN_ERROR, "error during read error body"));
			}
			return Completable.error(new ApiException(error.getCode(), error.getMessage()));
		}
		return Completable.complete();
	}
}
