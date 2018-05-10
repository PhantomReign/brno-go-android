package cz.vutbr.fit.brnogo.ui.main.routes;

import android.support.v4.content.ContextCompat;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cz.vutbr.fit.brnogo.R;
import cz.vutbr.fit.brnogo.data.model.store.Search;
import cz.vutbr.fit.brnogo.databinding.ListItemSearchFavoriteBinding;
import cz.vutbr.fit.brnogo.tools.DiffUtilCallback;
import cz.vutbr.fit.brnogo.ui.base.BaseViewHolder;

public class RoutesAdapter extends RecyclerView.Adapter<RoutesAdapter.ViewHolder> {

	private List<Search> items = new ArrayList<>();

	@Inject
	RoutesView view;

	@Inject
	public RoutesAdapter() {
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		ListItemSearchFavoriteBinding binding = ListItemSearchFavoriteBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
		binding.setView(view);
		final ViewHolder viewHolder = new ViewHolder(binding);
		binding.setFavoriteClickListener(item -> {
			viewHolder.setFavoriteColor(!item.getFavorite());
			view.onFavoriteButtonClick(item);
		});
		return new ViewHolder(binding);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		holder.bind(items.get(position));
	}

	@Override
	public int getItemCount() {
		return items.size();
	}

	public void updateData(List<Search> searches) {
		final DiffUtilCallback<Search> diffCallback = new DiffUtilCallback<>(items, searches);
		final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

		items.clear();
		items.addAll(searches);

		diffResult.dispatchUpdatesTo(this);
	}

	static class ViewHolder extends BaseViewHolder<Search> {

		final ListItemSearchFavoriteBinding binding;

		public ViewHolder(ListItemSearchFavoriteBinding binding) {
			super(binding.getRoot());
			this.binding = binding;
		}

		@Override
		public void bind(Search item) {
			binding.setItem(item);
			binding.executePendingBindings();
			setFavoriteColor(item.getFavorite());
		}

		private void setFavoriteColor(boolean isFavorite) {
			int tintColor = ContextCompat.getColor(binding.getRoot().getContext(), isFavorite ? R.color.red_500 : R.color.grey_400);
			binding.favoriteSearch.setColorFilter(tintColor);
		}
	}

	public interface FavoriteClickListener {
		void onClick(Search item);
	}
}
