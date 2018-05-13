package cz.vutbr.fit.brnogo.ui.route;

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
import cz.vutbr.fit.brnogo.data.model.response.Route;
import cz.vutbr.fit.brnogo.data.model.response.Vehicle;
import cz.vutbr.fit.brnogo.databinding.ListItemRouteBinding;
import cz.vutbr.fit.brnogo.databinding.ListItemRouteFooterBinding;
import cz.vutbr.fit.brnogo.tools.DiffUtilCallback;
import cz.vutbr.fit.brnogo.tools.constant.Constant;
import cz.vutbr.fit.brnogo.tools.datetime.DateTimeConverter;
import cz.vutbr.fit.brnogo.ui.base.BaseViewHolder;

public class RoutesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

	private List<RouteItem> items = new ArrayList<>();

	@Inject
	RoutesView view;

	@Inject
	public RoutesAdapter() {
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

		if (viewType == Constant.ViewType.ROUTE_LIST_ITEM) {
			ListItemRouteBinding binding = ListItemRouteBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
			return new RouteItemViewHolder(binding);
		} else {
			ListItemRouteFooterBinding binding = ListItemRouteFooterBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
			binding.setView(view);
			return new RouteFooterViewHolder(binding);
		}
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		try {
			if (holder instanceof RouteItemViewHolder) {
				((RouteItemViewHolder) holder).bind(items.get(position));
			} else {
				((RouteFooterViewHolder) holder).bind(items.get(position));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public int getItemViewType(int position) {

		if (items.get(position) instanceof Vehicle) {
			return Constant.ViewType.ROUTE_LIST_ITEM;
		} else {
			return Constant.ViewType.ROUTE_LIST_FOOTER;
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

			binding.itemSourceTitle.setText(vehicle.getPath().get(0).getStationName());
			binding.startTime.setText(getFormattedTimeOfDepartureWithDelay(vehicle));
			binding.itemDestinationTitle.setText(vehicle.getPath().get(size - 1).getStationName());
			binding.destinationTime.setText(getFormattedTimeOfArrivalWithDelay(vehicle));

		}

		private String getFormattedTimeOfDepartureWithDelay(Vehicle vehicle) {
			long timeWithDelay = vehicle.getDelay() + vehicle.getPath().get(0).getTimeOfDeparture();
			return DateTimeConverter.epochSecToZonedHourMinute(timeWithDelay);
		}

		private String getFormattedTimeOfArrivalWithDelay(Vehicle vehicle) {
			int size = vehicle.getPath().size();
			long timeWithDelay = vehicle.getDelay() + vehicle.getPath().get(size - 1).getTimeOfArrival();
			return DateTimeConverter.epochSecToZonedHourMinute(timeWithDelay);
		}
	}

	static class RouteFooterViewHolder extends BaseViewHolder<RouteItem> {

		final ListItemRouteFooterBinding binding;

		public RouteFooterViewHolder(ListItemRouteFooterBinding binding) {
			super(binding.getRoot());
			this.binding = binding;
		}

		@Override
		public void bind(RouteItem item) {
			binding.setItem((Route) item);
			binding.executePendingBindings();

			int min = (((Route) item).getRouteTime() + 30) / 60;
			binding.totalTimeText.setText(itemView.getResources().getString(R.string.total_time, min));
		}
	}
}
