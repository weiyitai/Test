package com.qianbajin.test.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.qianbajin.test.R;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    private boolean isBreak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    private SharedPreferences getSp() {
        return getSharedPreferences("xinxin", MODE_PRIVATE);
    }

    public void saveSP(View view) {
        SharedPreferences sp = getSp();
        boolean commit = sp.edit().putString("hehe", "去洗吧你").commit();

        Log.d("MainActivity", "commit:" + commit);

    }

    public void getSP(View view) {
        String hehe = getSp().getString("hehe", "");
        Log.d("MainActivity", hehe);
    }

    public void deleteDir(View view) {
//        Executors.newSingleThreadExecutor().execute(new Runnable() {
//            @Override
//            public void run() {
//                long length = FileUtil.deleteUnnecessaryFile();
//                Log.d("FileUtil", "length:" + Formatter.formatFileSize(MainActivity.this, length));
//            }
//        });
    }

}
