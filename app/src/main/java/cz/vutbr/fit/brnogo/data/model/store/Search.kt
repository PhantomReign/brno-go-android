package cz.vutbr.fit.brnogo.data.model.store

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import cz.vutbr.fit.brnogo.data.model.recyclerview.AdapterItem
import cz.vutbr.fit.brnogo.data.model.response.Stop
import cz.vutbr.fit.brnogo.tools.constant.Constant
import io.mironov.smuggler.AutoParcelable

@Entity(tableName = "search")
data class Search(
		@PrimaryKey
		@ColumnInfo(name = "id")
		var id: String = "",

		@ColumnInfo(name = "start_stop")
		var startStop: Stop? = null,

		@ColumnInfo(name = "destination_stop")
		var destinationStop: Stop? = null,

		@Ignore
		var time: String = Constant.SearchRequest.DEFAULT_TIME,

		@Ignore
		var date: String = Constant.SearchRequest.DEFAULT_DATE,

		@Ignore
		var transfers: Int = Constant.TransfersDialog.DEFAULT,

		@Ignore
		var transferTime: Int = Constant.TransferTimeDialog.DEFAULT,

		@Ignore
		var dateTime: Long = -1,

		@ColumnInfo(name = "favorite")
		var favorite: Boolean = false) : AdapterItem, AutoParcelable {
	override fun getItemId(): String {
		return id
	}
}
