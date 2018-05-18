package cz.vutbr.fit.brnogo.interactors;

import android.content.Context;

import javax.inject.Inject;

import cz.vutbr.fit.brnogo.R;
import cz.vutbr.fit.brnogo.data.persistance.Persistence;
import cz.vutbr.fit.brnogo.injection.annotation.qualifier.ApplicationContext;
import cz.vutbr.fit.brnogo.injection.annotation.scope.PerScreen;
import cz.vutbr.fit.brnogo.interactors.base.BaseSingleInteractor;
import cz.vutbr.fit.brnogo.tools.constant.StartScreenType;
import io.reactivex.Single;

/**
 * Class used to return current start screen.
 */

@PerScreen
public class GetStartScreenInteractor extends BaseSingleInteractor<String> {

	@Inject Persistence persistence;
	@Inject @ApplicationContext Context context;

	@Inject
	public GetStartScreenInteractor() {
	}

	@Override
	protected Single<String> buildSingle() {
		return Single.fromCallable(() ->
				persistence.get(context.getString(R.string.settings_key_screen), StartScreenType.TYPE_ROUTES));
	}
}
