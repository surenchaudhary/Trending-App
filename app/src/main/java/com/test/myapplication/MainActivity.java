package com.test.myapplication;

import android.os.Bundle;


import com.google.android.material.tabs.TabLayout;

public class MainActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TabLayout tabs = findViewById(R.id.tabs);

    }
}