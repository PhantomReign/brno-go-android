package cz.vutbr.fit.brnogo.data.model.response

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import cz.vutbr.fit.brnogo.R
import cz.vutbr.fit.brnogo.data.model.recyclerview.AdapterItem
import io.mironov.smuggler.AutoParcelable

@Entity(tableName = "stop")
data class Stop(
		@PrimaryKey
		@ColumnInfo(name = "id")
		var id: String = "",
		@ColumnInfo(name = "name")
		var name: String = "",
		@ColumnInfo(name = "zone")
		var zone: String = "") : AutoParcelable, AdapterItem {
	override fun getItemId(): String = id

	fun getIcon(): Pair<Int, Int> = Pair(R.drawable.ic_place, R.drawable.shape_oval_red)

}
