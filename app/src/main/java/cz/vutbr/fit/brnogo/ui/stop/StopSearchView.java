package cz.vutbr.fit.brnogo.ui.stop;

import cz.vutbr.fit.brnogo.data.model.response.Stop;
import cz.vutbr.fit.brnogo.ui.base.BaseView;

public interface StopSearchView extends BaseView {

	void onStopClick(Stop stop);
}
