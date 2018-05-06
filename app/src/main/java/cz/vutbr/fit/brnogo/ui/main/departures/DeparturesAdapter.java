package cz.vutbr.fit.brnogo.ui.main.departures;

import android.support.v4.content.ContextCompat;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cz.vutbr.fit.brnogo.R;
import cz.vutbr.fit.brnogo.data.model.store.FavoriteStop;
import cz.vutbr.fit.brnogo.databinding.ListItemDepartureFavoriteBinding;
import cz.vutbr.fit.brnogo.tools.DiffUtilCallback;
import cz.vutbr.fit.brnogo.ui.base.BaseViewHolder;

public class DeparturesAdapter extends RecyclerView.Adapter<DeparturesAdapter.ViewHolder> {

	private List<FavoriteStop> items = new ArrayList<>();

	@Inject
	DeparturesView view;

	@Inject
	public DeparturesAdapter() {
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		ListItemDepartureFavoriteBinding binding = ListItemDepartureFavoriteBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
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

	public void updateData(List<FavoriteStop> favoriteStops) {
		final DiffUtilCallback<FavoriteStop> diffCallback = new DiffUtilCallback<>(items, favoriteStops);
		final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

		items.clear();
		items.addAll(favoriteStops);

		diffResult.dispatchUpdatesTo(this);
	}

	static class ViewHolder extends BaseViewHolder<FavoriteStop> {

		final ListItemDepartureFavoriteBinding binding;

		public ViewHolder(ListItemDepartureFavoriteBinding binding) {
			super(binding.getRoot());
			this.binding = binding;
		}

		@Override
		public void bind(FavoriteStop item) {
			binding.setItem(item);
			binding.executePendingBindings();
			setFavoriteColor(item.getFavorite());
		}

		private void setFavoriteColor(boolean isFavorite) {
			int tintColor = ContextCompat.getColor(binding.getRoot().getContext(), isFavorite ? R.color.red_500 : R.color.grey_400);
			binding.favoriteDeparture.setColorFilter(tintColor);
		}
	}

	public interface FavoriteClickListener {
		void onClick(FavoriteStop item);
	}
}

