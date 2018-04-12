package cz.vutbr.fit.brnogo.ui.route.navigation;

import cz.vutbr.fit.brnogo.data.model.response.Route;
import cz.vutbr.fit.brnogo.ui.base.BaseView;

public interface RouteNavigationView extends BaseView {

	void onShowFasterRoute(Route route);
}
