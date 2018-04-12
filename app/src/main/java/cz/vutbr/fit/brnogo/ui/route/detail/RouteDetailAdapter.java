package cz.vutbr.fit.brnogo.ui.route.detail;

import android.support.v4.content.ContextCompat;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cz.vutbr.fit.brnogo.R;
import cz.vutbr.fit.brnogo.data.model.recyclerview.RouteItem;
import cz.vutbr.fit.brnogo.data.model.response.Node;
import cz.vutbr.fit.brnogo.data.model.response.Vehicle;
import cz.vutbr.fit.brnogo.databinding.ListItemRouteBinding;
import cz.vutbr.fit.brnogo.databinding.ListItemRoutePathBinding;
import cz.vutbr.fit.brnogo.tools.DiffUtilCallback;
import cz.vutbr.fit.brnogo.tools.constant.Constant;
import cz.vutbr.fit.brnogo.ui.base.BaseViewHolder;
import timber.log.Timber;

public class RouteDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

	private List<RouteItem> items = new ArrayList<>();

	@Inject
	RouteDetailView view;

	@Inject
	public RouteDetailAdapter() {
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

		if (viewType == Constant.ViewType.ROUTE_LIST_ITEM) {
			ListItemRouteBinding binding = ListItemRouteBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
			return new RouteItemViewHolder(binding);
		} else {
			ListItemRoutePathBinding binding = ListItemRoutePathBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
			binding.setView(view);
			return new RoutePathViewHolder(binding);
		}
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		try {
			if (holder instanceof RouteItemViewHolder) {
				((RouteItemViewHolder) holder).bind(items.get(position));
			} else {
				((RoutePathViewHolder) holder).bind(items.get(position));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public int getItemViewType(int position) {

		if (items.get(position) instanceof Vehicle) {
			return Constant.ViewType.ROUTE_DETAIL_LIST_ITEM;
		} else {
			return Constant.ViewType.ROUTE_DETAIL_LIST_PATH;
		}
	}

	@Override
	public int getItemCount() {
		return items.size();
	}

	public void updateData(List<RouteItem> routes) {
		final DiffUtilCallback<RouteItem> diffCallback = new DiffUtilCallback<>(items, routes);
		final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
		items.clear();
		items.addAll(routes);
		diffResult.dispatchUpdatesTo(this);
	}

	static class RouteItemViewHolder extends BaseViewHolder<RouteItem> {

		final ListItemRouteBinding binding;

		public RouteItemViewHolder(ListItemRouteBinding binding) {
			super(binding.getRoot());
			this.binding = binding;
		}

		@Override
		public void bind(RouteItem item) {
			binding.setItem((Vehicle) item);
			binding.executePendingBindings();
			setBinding((Vehicle) item);
		}

		public void setBinding(Vehicle vehicle) {
			int size = vehicle.getPath().size();
			int color = ContextCompat.getColor(binding.getRoot().getContext(), vehicle.getColor());

			binding.itemVehicleIcon.setColorFilter(color);
			binding.itemVehicleNumber.setText(String.valueOf(vehicle.getLineId()));
			binding.itemVehicleNumber.setTextColor(color);

			if (vehicle.getDelay() == 0) {
				binding.itemDelay.setText(R.string.delay);
			} else {
				int min = (vehicle.getDelay() + 30) / 60;
				binding.itemDelay.setText(itemView.getResources().getString(R.string.delay_min, min));
			}

			binding.itemSourceTitle.setText(vehicle.getPath().get(0).getStationName());
			binding.startTime.setText(vehicle.getPath().get(0).getFormattedTimeOfDeparture());
			binding.itemDestinationTitle.setText(vehicle.getPath().get(size - 1).getStationName());
			binding.destinationTime.setText(vehicle.getPath().get(size - 1).getFormattedTimeOfArrival());

		}
	}

	static class RoutePathViewHolder extends BaseViewHolder<RouteItem> {

		final ListItemRoutePathBinding binding;

		public RoutePathViewHolder(ListItemRoutePathBinding binding) {
			super(binding.getRoot());
			this.binding = binding;
		}

		@Override
		public void bind(RouteItem item) {
			binding.setItem((Node) item);
			binding.executePendingBindings();
		}
	}
}
