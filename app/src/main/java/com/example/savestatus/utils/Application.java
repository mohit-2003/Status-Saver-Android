package com.example.savestatus.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;


public class Application extends android.app.Application {

    private static final String TAG = Application.class.getSimpleName();

    private static Application instance;

    public static Application getInstance() {
        return instance ;
    }

    MyActivityLifecycleCallbacks lifecycleCallbacks;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

    }

    @Override
    public void onCreate() {
        super.onCreate();

        initApp();
    }

    private void initApp() {
        lifecycleCallbacks = new MyActivityLifecycleCallbacks();
        registerActivityLifecycleCallbacks(lifecycleCallbacks);


    }


    public boolean isInForeground() {
        return lifecycleCallbacks.resumed > lifecycleCallbacks.paused;
    }

    private final class MyActivityLifecycleCallbacks implements ActivityLifecycleCallbacks {

        private int resumed;
        private int paused;
        private int started;
        private int stopped;
        private Activity currentActivity;

        public void onActivityCreated(Activity activity, Bundle bundle) {
            Log.i(TAG, "onActivityCreated:" + activity.getLocalClassName());

        }

        public void onActivityDestroyed(Activity activity) {
            Log.i(TAG, "onActivityDestroyed:" + activity.getLocalClassName());
            if (activity == currentActivity) {
                currentActivity = null;
            }
        }

        public void onActivityPaused(Activity activity) {
            Log.i(TAG, "onActivityPaused:" + activity.getLocalClassName());
            if (activity == currentActivity) {
                currentActivity = null;
            }
            ++paused;
        }

        public void onActivityResumed(Activity activity) {
            currentActivity = activity;
            ++resumed;
            Log.i(TAG, "onActivityResumed:" + activity.getLocalClassName());
        }

        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            Log.i(TAG, "onActivitySaveInstanceState:" + activity.getLocalClassName());
        }

        public void onActivityStarted(Activity activity) {
            Log.i(TAG, "onActivityStarted:" + activity.getLocalClassName());
            ++started;
        }

        public void onActivityStopped(Activity activity) {
            Log.i(TAG, "onActivityStopped:" + activity.getLocalClassName());
            ++stopped;
        }
    }
}