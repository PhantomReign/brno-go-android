package cz.vutbr.fit.brnogo.tools;

import android.databinding.BindingAdapter;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.view.View;

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

}
