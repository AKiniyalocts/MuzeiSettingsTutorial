package com.knok.behang.service;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.widget.Toast;
import com.google.android.apps.muzei.api.Artwork;
import com.google.android.apps.muzei.api.RemoteMuzeiArtSource;
import com.google.android.apps.muzei.api.UserCommand;
import com.knok.behang.R;
import com.wallpaper.core.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.prefs.Preferences;

/**
 * Created by rholland on 2/26/14.
 */
public class YourArtSource extends RemoteMuzeiArtSource  {

    public static final String SOURCE_NAME = "YourApp";
    private static final long ROTATE_TIME_MILLIS = 3 * 60 * 60 * 1000; // rotate every 3 hours
    private SharedPreferences mSharedPrefs;
    private boolean onlyWifi = false;
    private long duration = 3 * 60 * 60 * 1000;//3 hours
    private artworkWallpaper artwork = null;

    public BehangArtSource() {
        super(SOURCE_NAME);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mSharedPrefs = this.getSharedPreferences("com.knok.behang", Context.MODE_PRIVATE);
        duration = PreferenceManager.getDefaultSharedPreferences(this).getLong("duration", ROTATE_TIME_MILLIS );
    }
    
    protected void onTryUpdate(int reason) throws RemoteMuzeiArtSource.RetryException {
        duration = mSharedPrefs.getLong("duration", duration);
        try{
        //attempt to publish artwork
            publishArtwork(new Artwork.Builder()
                    .imageUri(Uri.parse(artwork.url))
                    .title(artwork.name)
                    .byline(artwork.author)
                    .viewIntent(new Intent(Intent.ACTION_VIEW,
                        Uri.parse(artwork.url)))
                    .build());
                    
                    //schedule update with duration set in shared prefs
                    scheduleUpdate(System.currentTimeMillis() + duration);
                }
                else{
                    //if update fails, schedule another update in duration set
                    scheduleUpdate(System.currentTimeMillis() + duration);

                }
            }
          
    }

}
