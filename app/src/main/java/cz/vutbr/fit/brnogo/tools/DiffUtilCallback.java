package cz.vutbr.fit.brnogo.tools;

import android.support.v7.util.DiffUtil;

import java.util.List;

import cz.vutbr.fit.brnogo.data.model.recyclerview.AdapterItem;

public class DiffUtilCallback<T extends AdapterItem> extends DiffUtil.Callback {

	private List<T> oldItems;
	private List<T> newItems;

	public DiffUtilCallback(List<T> oldItems, List<T> newItems) {
		this.oldItems = oldItems;
		this.newItems = newItems;
	}

	@Override
	public int getOldListSize() {
		return oldItems.size();
	}

	@Override
	public int getNewListSize() {
		return newItems.size();
	}

	@Override
	public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
		return oldItems.get(oldItemPosition).getId().equals(newItems.get(newItemPosition).getId());
	}

	@Override
	public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
		return oldItems.get(oldItemPosition).equals(newItems.get(newItemPosition));
	}
}
