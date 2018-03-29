package cz.vutbr.fit.brnogo.ui.stop;

import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cz.vutbr.fit.brnogo.R;
import cz.vutbr.fit.brnogo.data.model.response.Stop;
import cz.vutbr.fit.brnogo.databinding.ListItemStopBinding;
import cz.vutbr.fit.brnogo.tools.DiffUtilCallback;
import cz.vutbr.fit.brnogo.ui.base.BaseViewHolder;
import me.xuender.unidecode.Unidecode;
import timber.log.Timber;

public class StopSearchAdapter extends RecyclerView.Adapter<StopSearchAdapter.ViewHolder> implements Filterable {

	private List<Stop> items = new ArrayList<>();
	private List<Stop> suggestions = new ArrayList<>();

	@Inject
	StopSearchView view;

	@Inject
	public StopSearchAdapter() {
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		ListItemStopBinding binding = ListItemStopBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
		binding.setView(view);
		return new ViewHolder(binding);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		holder.bind(suggestions.get(position));
	}

	@Override
	public int getItemCount() {
		return suggestions.size();
	}

	public void updateData(List<Stop> stops) {
		final DiffUtilCallback<Stop> diffCallback = new DiffUtilCallback<>(items, stops);
		final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

		items.clear();
		items.addAll(stops);
		suggestions = items;

		diffResult.dispatchUpdatesTo(this);
	}

	private static String stripAccent(String string) {
		return Unidecode.decode(string);
	}

	@Override
	public Filter getFilter() {
		return new Filter() {

			@Override
			protected FilterResults performFiltering(CharSequence charSequence) {
				String charString = charSequence.toString();
				if (charString.isEmpty()) {
					suggestions = items;
				} else {
					List<Stop> filteredList = new ArrayList<>();
					for (Stop stop : items) {
						if (stripAccent(stop.getName().toLowerCase())
								.contains(stripAccent(charString.toLowerCase()))) {
							filteredList.add(stop);
						}
					}

					suggestions = filteredList;
				}

				FilterResults filterResults = new FilterResults();
				filterResults.values = suggestions;
				return filterResults;
			}

			@SuppressWarnings("unchecked")
			@Override
			protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
				suggestions = (ArrayList<Stop>) filterResults.values;
				notifyDataSetChanged();
			}
		};
	}

	static class ViewHolder extends BaseViewHolder<Stop> {

		final ListItemStopBinding binding;

		public ViewHolder(ListItemStopBinding binding) {
			super(binding.getRoot());
			this.binding = binding;
		}

		@Override
		public void bind(Stop item) {
			binding.setItem(item);
			binding.itemStopSubtitle.setText(itemView.getResources().getString(R.string.zone, item.getZone()));
			binding.itemStopIcon.setBackground(itemView.getResources().getDrawable(R.drawable.shape_oval_red));
			binding.executePendingBindings();
		}
	}
}
