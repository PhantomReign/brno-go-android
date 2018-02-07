package cz.vutbr.fit.brnogo.tools

import android.support.v7.util.DiffUtil

import cz.vutbr.fit.brnogo.data.model.recyclerview.AdapterItem

class DiffUtilCallback<T : AdapterItem>(private val oldItems: List<T>, private val newItems: List<T>) : DiffUtil.Callback() {

	override fun getOldListSize(): Int {
		return oldItems.size
	}

	override fun getNewListSize(): Int {
		return newItems.size
	}

	override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
		return oldItems[oldItemPosition].id == newItems[newItemPosition].id
	}

	override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
		return oldItems[oldItemPosition] == newItems[newItemPosition]
	}
}
