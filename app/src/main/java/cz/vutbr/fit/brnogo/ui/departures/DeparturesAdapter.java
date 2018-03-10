package cz.vutbr.fit.brnogo.ui.departures;

import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cz.vutbr.fit.brnogo.data.model.recyclerview.DepartureHeader;
import cz.vutbr.fit.brnogo.data.model.recyclerview.DepartureItem;
import cz.vutbr.fit.brnogo.data.model.response.DepartureVehicle;
import cz.vutbr.fit.brnogo.databinding.ListItemDepartureBinding;
import cz.vutbr.fit.brnogo.databinding.ListItemDepartureHeaderBinding;
import cz.vutbr.fit.brnogo.tools.DiffUtilCallback;
import cz.vutbr.fit.brnogo.tools.constant.Constant;
import cz.vutbr.fit.brnogo.ui.base.BaseViewHolder;

public class DeparturesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

	private List<DepartureItem> items = new ArrayList<>();

	@Inject
	DeparturesView view;

	@Inject
	public DeparturesAdapter() {
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

		if (viewType == Constant.ViewType.DEPARTURE_LIST_HEADER) {
			ListItemDepartureHeaderBinding binding = ListItemDepartureHeaderBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
			return new DepartureHeaderViewHolder(binding);
		} else {
			ListItemDepartureBinding binding = ListItemDepartureBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
			return new DepartureVehicleViewHolder(binding);
		}
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		try {
			if (holder instanceof DepartureHeaderViewHolder) {
				((DepartureHeaderViewHolder) holder).bind(items.get(position));
			} else {
				((DepartureVehicleViewHolder) holder).bind(items.get(position));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public int getItemViewType(int position) {

		if (items.get(position) instanceof DepartureHeader) {
			return Constant.ViewType.DEPARTURE_LIST_HEADER;
		} else {
			return Constant.ViewType.DEPARTURE_LIST_VEHICLE;
		}
	}

	@Override
	public int getItemCount() {
		return items.size();
	}

	public void updateData(List<DepartureItem> currentDepartures) {
		final DiffUtilCallback<DepartureItem> diffCallback = new DiffUtilCallback<>(items, currentDepartures);
		final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
		items.clear();
		items.addAll(currentDepartures);
		diffResult.dispatchUpdatesTo(this);
	}

	static class DepartureHeaderViewHolder extends BaseViewHolder<DepartureItem> {

		final ListItemDepartureHeaderBinding binding;

		public DepartureHeaderViewHolder(ListItemDepartureHeaderBinding binding) {
			super(binding.getRoot());
			this.binding = binding;
		}

		@Override
		public void bind(DepartureItem item) {
			binding.setItem((DepartureHeader) item);
			binding.executePendingBindings();
		}
	}

	static class DepartureVehicleViewHolder extends BaseViewHolder<DepartureItem> {

		final ListItemDepartureBinding binding;

		public DepartureVehicleViewHolder(ListItemDepartureBinding binding) {
			super(binding.getRoot());
			this.binding = binding;
		}

		@Override
		public void bind(DepartureItem item) {
			binding.setItem((DepartureVehicle) item);
			binding.executePendingBindings();
		}
	}
}
