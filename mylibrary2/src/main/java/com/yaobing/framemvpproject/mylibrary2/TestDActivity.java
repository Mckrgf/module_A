package com.yaobing.framemvpproject.mylibrary2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.yaobing.module_apt.Router;

@Router("qwer")
public class TestDActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_d);
    }
}