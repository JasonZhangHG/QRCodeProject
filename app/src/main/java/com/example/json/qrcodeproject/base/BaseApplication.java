package com.example.json.qrcodeproject.base;

import android.app.Application;

/**
 * Created by Json on 2017/6/27.
 */

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ToastHelper.init(getApplicationContext());
    }
}
