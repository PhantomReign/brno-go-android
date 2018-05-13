package cz.vutbr.fit.brnogo.ui.main.departures;

import cz.vutbr.fit.brnogo.data.model.store.FavoriteStop;
import cz.vutbr.fit.brnogo.ui.base.BaseView;

public interface DeparturesView extends BaseView {

	void onFindDeparturesClick();

	void onFavoriteButtonClick(FavoriteStop stop);

	void onFavoriteItemClick(FavoriteStop stop);

	void onStartTextEditClick();
}
