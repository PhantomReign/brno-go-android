package cz.vutbr.fit.brnogo.tools;

import android.databinding.BindingAdapter;
import android.support.annotation.ColorInt;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

/**
 * Class containing all custom binding methods.
 */

public class BindingUtility {

	private BindingUtility() {
	}

	@BindingAdapter("navigation_view_click")
	public static void bottomNavigation(BottomNavigationView bottomNavigationView, BottomNavigationView.OnNavigationItemSelectedListener listener) {
		bottomNavigationView.setOnNavigationItemSelectedListener(listener);
	}

	@BindingAdapter({"on_menu_item_click"})
	public static void setNavigationViewItemClick(NavigationView navigationView, NavigationView.OnNavigationItemSelectedListener listener) {
		navigationView.setNavigationItemSelectedListener(listener);
	}

	@BindingAdapter("on_click")
	public static void onClick(View view, View.OnClickListener listener) {
		view.setOnClickListener(listener);
	}

	@BindingAdapter("visible")
	public static void visible(View view, boolean visible) {
		view.setVisibility(visible ? View.VISIBLE : View.GONE);
	}

	@BindingAdapter("visible_animated")
	public static void visibleWithAnimation(View view, boolean visible) {
		if (visible) {
			view.setVisibility(View.VISIBLE);
			view.setAlpha(0f);
			view.animate()
					.alpha(1f);
		} else {
			view.setVisibility(View.GONE);
		}
	}

	@BindingAdapter("on_refresh")
	public static void refreshListener(SwipeRefreshLayout layout, SwipeRefreshLayout.OnRefreshListener listener) {
		layout.setOnRefreshListener(listener);
	}

	@BindingAdapter("refreshing")
	public static void refreshing(SwipeRefreshLayout layout, boolean refreshing) {
		layout.setRefreshing(refreshing);
	}

	@BindingAdapter("swipe_refresh_color")
	public static void refreshColor(SwipeRefreshLayout layout, @ColorInt int color) {
		layout.setColorSchemeColors(color);
	}

}
