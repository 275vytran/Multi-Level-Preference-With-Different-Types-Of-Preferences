package com.vytran.test;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.util.Log;

public class PrefsFragmentAbout extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    public PrefsFragmentAbout() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.prefs_fragment_about);
    }

    @Override
    public void onResume(){
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        Preference pref;
        pref = getPreferenceScreen().findPreference("key_author");
        pref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://androidofvy.wordpress.com/"));
                    startActivity(intent);
                }
                catch (Exception e) {
                    Log.e("PrefsFragmentSettings", "Invalid URL", e);
                }
                return true;
            }
        });

    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("key_username")) {
            String e = sharedPreferences.getString("key_username", "Username");
            if (e != null) {
                Assets.userTextView.setText(e);
            }
        }
    }
}
