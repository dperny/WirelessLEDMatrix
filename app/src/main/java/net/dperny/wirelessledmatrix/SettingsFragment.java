package net.dperny.wirelessledmatrix;

import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.widget.Toolbar;

/**
 * Created by drewe on 4/5/2016.
 */
public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // load preferences from XML resource
        addPreferencesFromResource(R.xml.preferences);
    }
}
