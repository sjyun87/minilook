package com.minilook.minilook;

import android.app.Application;

import butterknife.BindString;
import timber.log.Timber;

public class App extends Application {

    @Override public void onCreate() {
        super.onCreate();

        init();
    }

    private void init() {
        if (BuildConfig.DEBUG) Timber.plant(new Timber.DebugTree());
    }
}
