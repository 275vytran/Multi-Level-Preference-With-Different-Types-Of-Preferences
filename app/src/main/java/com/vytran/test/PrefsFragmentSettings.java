package com.vytran.test;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.util.Log;

public class PrefsFragmentSettings extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
    public PrefsFragmentSettings() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.prefs_fragment_settings);
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);

    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        //Handle background music
        if (key.equals("key_music_on")) {
            boolean b = sharedPreferences.getBoolean("key_music_off", false);
            if (b==false) {
                if (Assets.mp != null)
                    Assets.mp.setVolume(0,0);
            }
            else {
                if (Assets.mp != null)
                    Assets.mp.setVolume(1, 1);
            }
        }

        //Handle sound effects
        if (key.equals("key_sound_off")) {
            boolean c = sharedPreferences.getBoolean("key_sound_on", true);
            if (c==false) {
                if (Assets.sp != null)
                    Assets.sp.setVolume(1, 0, 0);
            }
            else {
                if (Assets.sp != null)
                    Assets.sp.setVolume(1, 1, 1);
            }
        }

        if (key.equals("key_list")) {
            String d = sharedPreferences.getString("key_list", "Item One");
            if ("Item One".equals(d)) {
                Assets.text = "User choose Item One";
                Assets.textView.setText(Assets.text);
            }
            else if ("Item Two".equals(d)) {
                Assets.text = "User choose Item Two";
                Assets.textView.setText(Assets.text);
            }
            else if ("Item Three".equals(d)) {
                Assets.text = "User choose Item Three";
                Assets.textView.setText(Assets.text);
            }
        }
    }

}
