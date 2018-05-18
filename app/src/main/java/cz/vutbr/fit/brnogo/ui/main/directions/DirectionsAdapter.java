package cz.vutbr.fit.brnogo.ui.main.directions;

import android.support.v4.content.ContextCompat;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cz.vutbr.fit.brnogo.R;
import cz.vutbr.fit.brnogo.data.model.response.Route;
import cz.vutbr.fit.brnogo.databinding.ListItemDirectionsSavedBinding;
import cz.vutbr.fit.brnogo.tools.DiffUtilCallback;
import cz.vutbr.fit.brnogo.tools.datetime.DateTimeConverter;
import cz.vutbr.fit.brnogo.ui.base.BaseViewHolder;

/**
 * Adapter Class used to manage saved directions in RecyclerView.
 */

public class DirectionsAdapter extends RecyclerView.Adapter<DirectionsAdapter.ViewHolder> {

	private List<Route> items = new ArrayList<>();

	@Inject
	DirectionsView view;

	@Inject
	public DirectionsAdapter() {
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		ListItemDirectionsSavedBinding binding = ListItemDirectionsSavedBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
		binding.setView(view);
		final ViewHolder viewHolder = new ViewHolder(binding);
		binding.setSavedClickListener(item -> {
			viewHolder.setSavedColor(!item.getSaved());
			view.onSavedButtonClick(item);
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

	public void updateData(List<Route> searches) {
		final DiffUtilCallback<Route> diffCallback = new DiffUtilCallback<>(items, searches);
		final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

		items.clear();
		items.addAll(searches);

		diffResult.dispatchUpdatesTo(this);
	}

	static class ViewHolder extends BaseViewHolder<Route> {

		final ListItemDirectionsSavedBinding binding;

		public ViewHolder(ListItemDirectionsSavedBinding binding) {
			super(binding.getRoot());
			this.binding = binding;
		}

		@Override
		public void bind(Route item) {
			binding.setItem(item);
			binding.executePendingBindings();
			binding.itemTime.setText(getTimeString(item));
			setSavedColor(item.getSaved());
		}

		private String getTimeString(Route item) {
			long time = item.getVehicles().get(0).getPath().get(0).getTimeOfDeparture();
			return DateTimeConverter.epochSecToZonedDayTime(time);
		}

		private void setSavedColor(boolean isSaved) {
			int tintColor = ContextCompat.getColor(binding.getRoot().getContext(), isSaved ? R.color.red_500 : R.color.grey_400);
			binding.savedRoute.setColorFilter(tintColor);
		}
	}

	public interface SavedClickListener {
		void onClick(Route item);
	}
}
