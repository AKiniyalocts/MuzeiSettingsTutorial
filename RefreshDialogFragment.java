package com.your.application;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by anthony on 2/21/14.
 */
public class RefreshDialogFragment extends DialogFragment {
    private SharedPreferences mSharedPrefs;
    private long duration = 3 * 60 * 60 * 1000; // default 3 hour duration for refresh
    private boolean min = false, hour = false;
    public RefreshDialogFragment(){
        super();
    }
    public static RefreshDialogFragment newInstance(){
        return new RefreshDialogFragment();
    }
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        final View content =  inflater.inflate(R.layout.refresh_dialog, null);
        //
        // Get shared preferences for application package
        //
        mSharedPrefs =  getActivity().getSharedPreferences(
                "com.your.appllication", Context.MODE_PRIVATE);
        dialog.setView(content);
        setFonts(content);
        setmSharedPrefs(content);
                dialog.setPositiveButton(getString(R.string.set), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        try {
                            onSaveDuration(content);// Save the duration to Shared Preferences
                            Toast.makeText(getActivity(), getString(R.string.time_set), Toast.LENGTH_LONG).show();

                        } catch (Exception ex) {
                            Toast.makeText(getActivity(), "Error saving refresh time", Toast.LENGTH_LONG).show();
                        }

                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Do nothing on cancel
                    }
                });

        return dialog.create();
    }

    //
    // Set's the fonts of the view's to the same as muzei
    // Comment out if it's not desirable
    // You must have the "alegreya-regular.ttf" in your /assets directory
    public  void setFonts(View content){
        TextView refreshTitle = (TextView)content.findViewById(R.id.refreshTitle);
        EditText refreshText = (EditText)content.findViewById(R.id.refreshTime);
        RadioButton minRad = (RadioButton)content.findViewById(R.id.radMin);
        RadioButton hourRad = (RadioButton)content.findViewById(R.id.radHour);

        Typeface light = Typeface.createFromAsset(getActivity().getAssets(), "alegreya-regular.ttf");
        refreshText.setTypeface(light);
        refreshTitle.setTypeface(light);
        minRad.setTypeface(light);
        hourRad.setTypeface(light);

    }
    //
    // Writes the duration (in milliseconds) to shared preferences
    //
    public void onSaveDuration(View content){
        EditText refreshTime = (EditText)content.findViewById(R.id.refreshTime);
        String refresh = refreshTime.getText().toString();
        try{
            duration = Long.parseLong(refresh);
            duration = getTime(content, duration);
            mSharedPrefs.edit().putLong("duration", duration).apply();
            mSharedPrefs.edit().putBoolean("min", min).apply();
            mSharedPrefs.edit().putBoolean("hour", hour).apply();
        }catch(Exception BadIntException){
            Toast.makeText(getActivity(), "Error setting refresh", Toast.LENGTH_LONG).show();
        }
    }

    // Determine the correct refresh time, whether it's minutes or seconds
    // Then convert that to milliseconds for muzei
    // Return it
    public long getTime(View content, Long refreshTime){
        RadioButton radMin = (RadioButton)content.findViewById(R.id.radMin);
        if(radMin.isChecked()){
            min = true;
            return refreshTime * 60000;
        }
        else{
            hour = true;
            return refreshTime * 60 * 60 * 1000;
        }
    }
    //
    // Set text field, and radio buttons to it's saved state for startup
    //
    public void setmSharedPrefs(View content){
        EditText refreshText = (EditText)content.findViewById(R.id.refreshTime);
        RadioButton minRad = (RadioButton)content.findViewById(R.id.radMin);
        RadioButton hourRad = (RadioButton)content.findViewById(R.id.radHour);
        if(mSharedPrefs.getBoolean("min", min))
            minRad.setChecked(true);
        else
            hourRad.setChecked(true);

        if(minRad.isChecked())
            refreshText.setText("" + mSharedPrefs.getLong("duration", duration) / 60000);
        else
            refreshText.setText("" + mSharedPrefs.getLong("duration", duration)/3600000);
    }
}
