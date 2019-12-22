package com.example.attendanceplaystore;

import android.content.Intent;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class splashscreen extends AppCompatActivity {

    public static int SPLAH_TIME_OUT = 4000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(splashscreen.this, signup.class);
                startActivity(intent);
                finish();

            }
        },SPLAH_TIME_OUT);

    }
}
