package cz.vutbr.fit.brnogo.tools

import android.support.v7.util.DiffUtil

import cz.vutbr.fit.brnogo.data.model.recyclerview.AdapterItem

/**
 * Utility class that can calculate the difference between two lists and output.
 * It is used to calculate updates for a RecyclerView Adapter.
 */

class DiffUtilCallback<T : AdapterItem>(private val oldItems: List<T>, private val newItems: List<T>) : DiffUtil.Callback() {

	override fun getOldListSize(): Int {
		return oldItems.size
	}

	override fun getNewListSize(): Int {
		return newItems.size
	}

	override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
		return oldItems[oldItemPosition].itemId == newItems[newItemPosition].itemId
	}

	override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
		return oldItems[oldItemPosition] == newItems[newItemPosition]
	}
}
