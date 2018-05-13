package cz.vutbr.fit.brnogo.ui.main.routes;

import cz.vutbr.fit.brnogo.data.model.store.Search;
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

	void onFavoriteItemClick(Search search);

	void onFavoriteButtonClick(Search search);
}
