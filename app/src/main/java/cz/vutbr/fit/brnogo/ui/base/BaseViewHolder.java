package cz.vutbr.fit.brnogo.ui.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Base Class representing ViewHolder.
 */

public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {

	public BaseViewHolder(View itemView) {
		super(itemView);
	}

	public abstract void bind(T item);
}
