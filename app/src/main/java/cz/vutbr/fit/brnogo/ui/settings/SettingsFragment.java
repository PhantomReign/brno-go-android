package cz.vutbr.fit.brnogo.ui.settings;

import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;

import javax.inject.Inject;

import cz.vutbr.fit.brnogo.R;
import cz.vutbr.fit.brnogo.data.persistance.HawkPreferenceDataStore;
import dagger.android.support.AndroidSupportInjection;

public class SettingsFragment extends PreferenceFragmentCompat {

	@Inject HawkPreferenceDataStore preferenceDataStore;

	@Override
	public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
		AndroidSupportInjection.inject(this);
		getPreferenceManager().setPreferenceDataStore(preferenceDataStore);
		setPreferencesFromResource(R.xml.settings, rootKey);

		Preference pref = findPreference(getString(R.string.settings_key_sync));
		pref.setOnPreferenceClickListener(preference -> {
			if (getActivity() != null) {
				((SettingsActivity) getActivity()).sync();
			}
			return false;
		});
	}
}
