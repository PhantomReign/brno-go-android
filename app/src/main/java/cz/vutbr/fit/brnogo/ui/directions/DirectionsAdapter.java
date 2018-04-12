package cz.vutbr.fit.brnogo.ui.directions;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cz.vutbr.fit.brnogo.data.model.recyclerview.Direction;
import cz.vutbr.fit.brnogo.data.model.recyclerview.RouteItem;
import cz.vutbr.fit.brnogo.data.model.response.Route;
import cz.vutbr.fit.brnogo.databinding.ListItemDirectionBinding;
import cz.vutbr.fit.brnogo.databinding.ListItemDirectionInfoBinding;
import cz.vutbr.fit.brnogo.tools.DiffUtilCallback;
import cz.vutbr.fit.brnogo.tools.constant.Constant;
import cz.vutbr.fit.brnogo.ui.base.BaseViewHolder;

public class DirectionsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

	private List<RouteItem> items = new ArrayList<>();

	@Inject
	public DirectionsAdapter() {
	}

	@NonNull
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

		if (viewType == Constant.ViewType.DIRECTION_LIST_ROUTE) {
			ListItemDirectionInfoBinding binding = ListItemDirectionInfoBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
			return new DirectionInfoViewHolder(binding);
		} else {
			ListItemDirectionBinding binding = ListItemDirectionBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
			return new DirectionViewHolder(binding);
		}
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		try {
			if (holder instanceof DirectionInfoViewHolder) {
				((DirectionInfoViewHolder) holder).bind(items.get(position));
			} else {
				((DirectionViewHolder) holder).bind(items.get(position));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public int getItemViewType(int position) {

		if (items.get(position) instanceof Direction) {
			return Constant.ViewType.DIRECTION_LIST_ITEM;
		} else {
			return Constant.ViewType.DIRECTION_LIST_ROUTE;
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

	static class DirectionInfoViewHolder extends BaseViewHolder<RouteItem> {

		final ListItemDirectionInfoBinding binding;

		public DirectionInfoViewHolder(ListItemDirectionInfoBinding binding) {
			super(binding.getRoot());
			this.binding = binding;
		}

		@Override
		public void bind(RouteItem item) {
			binding.setItem((Route) item);
			binding.executePendingBindings();
			setBinding((Route) item);
		}

		public void setBinding(Route route) {
			binding.routeTime.setText(String.valueOf(route.getRouteTime() / 60));
			binding.vehicleSum.setText(String.valueOf(route.getVehicles().size()));
		}
	}

	static class DirectionViewHolder extends BaseViewHolder<RouteItem> {

		final ListItemDirectionBinding binding;

		public DirectionViewHolder(ListItemDirectionBinding binding) {
			super(binding.getRoot());
			this.binding = binding;
		}

		@Override
		public void bind(RouteItem item) {
			binding.setItem((Direction) item);
			binding.executePendingBindings();
			setColor((Direction) item);
		}

		public void setColor(Direction direction) {
			int color = ContextCompat.getColor(binding.getRoot().getContext(), direction.getColor());
			binding.itemIcon.setColorFilter(color);
		}
	}
}
