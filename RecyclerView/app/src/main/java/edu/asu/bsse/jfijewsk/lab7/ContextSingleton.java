package edu.asu.bsse.jfijewsk.lab7;

import android.app.Application;
import android.app.admin.DevicePolicyManager;
import android.content.Context;

public class ContextSingleton extends Application {

    //private static MyApp instance;
    private static Context mContext;

    public static Context getContext() {
        //  return instance.getApplicationContext();
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //  instance = this;
        mContext = getApplicationContext();
    }
}
