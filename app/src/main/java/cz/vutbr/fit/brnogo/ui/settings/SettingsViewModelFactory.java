package cz.vutbr.fit.brnogo.ui.settings;

import javax.inject.Inject;
import javax.inject.Provider;

import cz.vutbr.fit.brnogo.injection.annotation.scope.PerScreen;
import cz.vutbr.fit.brnogo.ui.base.BaseViewModelFactory;

@PerScreen
public class SettingsViewModelFactory extends BaseViewModelFactory<SettingsViewModel> {

    @Inject
    Provider<SettingsViewModel> viewModelProvider;

    @Inject
    public SettingsViewModelFactory() {
    }

    @Override
    protected SettingsViewModel createViewModel() {
        return viewModelProvider.get();
    }
}
