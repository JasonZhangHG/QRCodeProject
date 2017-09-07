package com.example.json.qrcodeproject.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

/**
 * Created by Json on 2017/7/3.
 */

public class BaseActivity extends AppCompatActivity {

    protected Handler mHandler;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    public Handler getHandler(){
        if(mHandler == null){
            synchronized (this){
                if(mHandler == null){
                    mHandler = new Handler(Looper.getMainLooper());
                }
            }
        }
        return mHandler;
    }

    public void doInUI(Runnable runnable, long delayMillis){
        getHandler().postDelayed(runnable, delayMillis);
    }

    public void toActivity(Class<? extends Activity> clazz) {
        startActivity(new Intent(this, clazz));
    }

    //是否连接WIFI
    public boolean isWifiConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if(wifiNetworkInfo.isConnected()) {
            return true ;
        }
        return false ;
    }

    //设置屏幕背景透明度
    public void setBackgroundAlpha(float alpha) {
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.alpha = alpha;
        getWindow().setAttributes(layoutParams);
    }

}
