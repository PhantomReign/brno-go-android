package cz.vutbr.fit.brnogo.data.model.store

import cz.vutbr.fit.brnogo.data.model.response.Stop
import cz.vutbr.fit.brnogo.tools.constant.Constant
import io.mironov.smuggler.AutoParcelable

data class Search(
		var startStop: Stop? = null,
		var destinationStop: Stop? = null,
		var time: String = Constant.SearchRequest.DEFAULT_TIME,
		var date: String = Constant.SearchRequest.DEFAULT_DATE,
		var transfers: Int = Constant.SearchRequest.DEFAULT_TRANSFERS,
		var transferTime: Int = Constant.SearchRequest.DEFAULT_TRANSFER_TIME) : AutoParcelable
