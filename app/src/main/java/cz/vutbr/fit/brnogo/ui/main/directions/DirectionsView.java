package cz.vutbr.fit.brnogo.ui.main.directions;

import cz.vutbr.fit.brnogo.data.model.response.Route;
import cz.vutbr.fit.brnogo.ui.base.BaseView;

public interface DirectionsView extends BaseView {
	void onGetDirectionsClick();

	void onStartTextEditClick();

	void onDestinationTextEditClick();

	void onSwitchClick();

	void onTimeButtonClick();

	void onDateButtonClick();

	void onSavedItemClick(Route route);

	void onSavedButtonClick(Route route);
}
