package com.your.application;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.*;

/**
 * Created by anthony on 2/28/14.
 */
@SuppressWarnings("ALL")
public class MuzeiSettingsActivity extends PreferenceActivity {
    private SharedPreferences mSharedPrefs;


    @SuppressWarnings("deprecation")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // add the layout view for your shared preferences
        addPreferencesFromResource(R.xml.preferences);
        
        //your shared preferences for your package
        mSharedPrefs = this.getSharedPreferences("com.your.application", Context.MODE_PRIVATE);
        
        // onClick for refresh preference item in Settings Activity
        findPreference("refresh_time").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                showRefreshDialog();
                return false;
            }
        });
    
    //Shows the Refresh time dialog
    public void showRefreshDialog(){
        RefreshDialogFragment dialog = RefreshDialogFragment.newInstance();
        dialog.show(this.getFragmentManager(),"dialog");
    }



}

