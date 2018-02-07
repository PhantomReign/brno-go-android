package cz.vutbr.fit.brnogo.ui.routes;

import cz.vutbr.fit.brnogo.ui.base.BaseView;

public interface RoutesView extends BaseView {

	void onFindRouteClick();

	void onAdvancedClick();

	void onCloseClick();

	void onStartTextEditClick();

	void onDestinationTextEditClick();

	void onSwitchClick();

	void onTimeButtonClick();

	void onDateButtonClick();

	void onTransfersButtonClick();

	void onTransferTimeButtonClick();
}
