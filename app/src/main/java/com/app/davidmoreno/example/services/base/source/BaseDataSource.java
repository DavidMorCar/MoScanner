package com.app.davidmoreno.example.services.base.source;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.app.davidmoreno.example.services.base.MainHttpService;

public class BaseDataSource {

    private final Context mContext;
    private SharedPreferences mSharedPreferences;
    private MainHttpService mMainHttpService;

    public BaseDataSource(Context context, SharedPreferences sharedPreferences,
                          MainHttpService mainHttpService) {
        mContext = context;
        mMainHttpService = mainHttpService;
        mSharedPreferences = sharedPreferences;
    }

    public boolean haveAnInternetConnection(){
        ConnectivityManager cm =
                (ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork;
        if (cm != null) {
            activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null &&
                    activeNetwork.isConnectedOrConnecting();
        }
        return false;
    }
}
