package com.example.pokedex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.MultiSelectListPreference;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceGroup;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import java.util.Collections;
import java.util.Set;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment()).commit();
        test();
    }

    public void test()
    {
//        SharedPreferences preferences = this.getSharedPreferences("switch", Context.MODE_PRIVATE);
//        SharedPreferences preferences = this.getSharedPreferences("switch", Context.MODE_PRIVATE);
        boolean isChecked = getIntent().getBooleanExtra("switch", false);
    }


    public static class SettingsFragment extends PreferenceFragment {
        @Override
        public void onCreate(final Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings);

            updateSummary(getPreferenceScreen());
        }

        private String updateSummary(Preference p)
        {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
            p.setSummary("");
            if (p instanceof EditTextPreference)
            {
                p.setSummary(preferences.getString(p.getKey(), ""));
            }
            else if (p instanceof ListPreference)
            {
                String value = preferences.getString(p.getKey(), "");
                int index = ((ListPreference) p).findIndexOfValue(value);
                if (index >= 0)
                {
                    p.setSummary(((ListPreference) p).getEntries()[index]);
                }
            }
            else if (p instanceof MultiSelectListPreference)
            {
                Set<String> values = preferences.getStringSet(p.getKey(), Collections.<String>emptySet());
                for (String value : values)
                {
                    int index = ((MultiSelectListPreference) p).findIndexOfValue(value);
                    if (index >= 0)
                    {
                        p.setSummary(p.getSummary() + (p.getSummary().length() == 0 ? ""
                                : ", ") + ((MultiSelectListPreference) p).getEntries()[index]);
                    }
                }
            }
            else if (p instanceof PreferenceCategory)
            {
                PreferenceCategory preference = (PreferenceCategory) p;
                for (int i = 0; i < preference.getPreferenceCount(); i++)
                {
                    updateSummary(preference.getPreference(i));
                }
            }
            else if (p instanceof PreferenceGroup)
            {
                PreferenceGroup preference = (PreferenceGroup) p;
                for (int i = 0; i < preference.getPreferenceCount(); i++)
                {
                    p.setSummary(p.getSummary() + (p.getSummary().length() == 0 ? ""
                            : ", ") + updateSummary(preference.getPreference(i)));
                }
            }
            return p.getSummary().toString();
        }

    }
}
