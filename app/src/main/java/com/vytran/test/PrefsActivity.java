package com.vytran.test;

import android.os.Build;
import android.preference.PreferenceActivity;

import java.util.List;

public class PrefsActivity extends PreferenceActivity {
    @Override
    protected boolean isValidFragment(String fragmentName) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB)
            return true;
        else if (PrefsFragmentSettings.class.getName().equals(fragmentName))
            return true;
        else if (PrefsFragmentAbout.class.getName().equals(fragmentName))
            return true;
        return false;
    }

    @Override
    public void onBuildHeaders(List<PreferenceActivity.Header> target) {
        //Multiple level preference
        loadHeadersFromResource(R.xml.prefs_headers, target);
        //Single Level Preference
        //getFragmentManager().beginTransaction().replace(android.R.id.content, new PrefsFragmentSettings()).commit();
    }
}
