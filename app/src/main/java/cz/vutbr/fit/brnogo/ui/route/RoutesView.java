package cz.vutbr.fit.brnogo.ui.route;

import cz.vutbr.fit.brnogo.data.model.response.Route;
import cz.vutbr.fit.brnogo.ui.base.BaseView;

public interface RoutesView extends BaseView {

	void onInformationClick(Route route);

	void onNavigationClick(Route route);
}
