package cz.vutbr.fit.brnogo.data.api;

import javax.inject.Inject;
import javax.inject.Singleton;

import cz.vutbr.fit.brnogo.data.util.ResultTransformer;

@Singleton
public class ApiManager {

	@Inject
	ApiService apiService;

	@Inject
	ResultTransformer resultTransformer;

	@Inject
	public ApiManager() {
	}

}
