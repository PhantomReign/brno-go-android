package cz.vutbr.fit.brnogo.ui.route.navigation.map;

import cz.vutbr.fit.brnogo.ui.base.BaseView;

public interface MapView extends BaseView {

	void onEnterVehicleClick();

	void onExitVehicleClick();

	void onTrackClick();

	void onReplaceClick();

	void onKeepClick();
}
