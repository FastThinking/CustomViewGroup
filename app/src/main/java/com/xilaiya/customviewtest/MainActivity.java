package com.xilaiya.customviewtest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CustomViewGroup customViewGroup = findViewById(R.id.customViewGroup);
        customViewGroup.setMyPadding(0, 0, 0, 0);
    }
}