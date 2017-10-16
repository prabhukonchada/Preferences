package prabhukonchada.android.preferences;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.preference.PreferenceScreen;

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        PreferenceManager.getDefaultSharedPreferences(getContext()).unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.pref_settings);

        PreferenceScreen preferenceScreen = getPreferenceScreen();
        SharedPreferences preferences = preferenceScreen.getSharedPreferences();
        preferences.registerOnSharedPreferenceChangeListener(this);
        int count = preferenceScreen.getPreferenceCount();

        for(int i=0;i<count;i++)
        {
            Preference preference = preferenceScreen.getPreference(i);
            if(preference instanceof ListPreference)
            {
                String value = preferences.getString(preference.getKey(),"");
                setPreferenceSummary(preference,value);
            }
            else if(preference instanceof EditTextPreference)
            {
                Float textSize = Float.valueOf(preferences.getString(preference.getKey(),"30"));
                preference.setSummary(String.valueOf(textSize));
            }

        }
    }

    private void setPreferenceSummary(Preference preference,String value)
    {
        if(preference instanceof ListPreference)
        {
            ListPreference listPreference = (ListPreference) preference;
            int index = (listPreference).findIndexOfValue(value);
            listPreference.setSummary(listPreference.getEntries()[index]);
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference preference = findPreference(key);
        if(preference != null)
        {
            if(preference instanceof ListPreference)
            {
                String value = sharedPreferences.getString(preference.getKey(),"");
                setPreferenceSummary(preference,value);
            }
            else if(preference instanceof EditTextPreference)
            {
                Float textSize = Float.valueOf(sharedPreferences.getString(preference.getKey(),"30"));
                preference.setSummary(String.valueOf(textSize));
            }
        }
    }
}
