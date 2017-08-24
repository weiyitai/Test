package com.qianbajin.test.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.qianbajin.test.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

    }

    public void button1(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void button2(View view){
        Intent intent = new Intent(this, NavigationActivity.class);
        startActivity(intent);
    }

    public void button3(View view) {
        Intent intent = new Intent(this, NavigaDrawerActivity.class);
        startActivity(intent);
    }

    public void button4(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
