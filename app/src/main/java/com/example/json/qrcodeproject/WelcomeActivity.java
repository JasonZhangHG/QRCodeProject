package com.example.json.qrcodeproject;

import android.content.Intent;
import android.os.Bundle;

import com.example.json.qrcodeproject.base.BaseActivity;

public class WelcomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        doInUI(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                WelcomeActivity.this.finish();}
        }, 50);
    }
}
