package com.example.attendanceplaystore;

import android.content.Intent;
import android.content.SharedPreferences;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ViewPager slidepager;
    private LinearLayout linearLayout;
    private TextView[] dots;
    private slideadapter slideadapter;
    private Button next,previous;
    private int currentpage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        slidepager = (ViewPager) findViewById(R.id.viewpager);
        linearLayout = findViewById(R.id.slidelayout);

        next = findViewById(R.id.next);
        previous = findViewById(R.id.previous);

        SharedPreferences preferences = getSharedPreferences("prefs", MODE_PRIVATE);
        String firststart = preferences.getString("firsttime","");

        if(firststart.equals("YES")){
            transistiontosplash();
        }
        else{
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("firsttime", "YES");
            editor.apply();
            onboarding();
        }
    }

    public void onboarding(){
        slideadapter = new slideadapter(MainActivity.this);

        slidepager.setAdapter(slideadapter);
        dotIncicator(0);
        slidepager.addOnPageChangeListener(viewlistener);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slidepager.setCurrentItem(currentpage+1);
            }
        });
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slidepager.setCurrentItem(currentpage-1);
            }
        });

    }

    public void dotIncicator(int position){

        dots = new TextView[3];
        linearLayout.removeAllViews();

        for(int i=0;i<dots.length;i++){
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.colorTransparentWhite));

            linearLayout.addView(dots[i]);
        }

        if(dots.length>0)
        {
            dots[position].setTextColor(getResources().getColor(R.color.colorWhite));
        }

    }

    ViewPager.OnPageChangeListener viewlistener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {

            dotIncicator(i);

            currentpage = i;

            if(i==0){
                next.setEnabled(true);
                previous.setEnabled(false);
                previous.setVisibility(View.INVISIBLE);
                next.setText("Next");
                previous.setText("");
            }
            else if(i==dots.length-1)
            {
                next.setEnabled(true);
                previous.setEnabled(true);
                previous.setVisibility(View.VISIBLE);
                next.setText("Finish");
                previous.setText("Back");
                next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        transistiontosplash();
                        finish();

                    }
                });

            }
            else{
                next.setEnabled(true);
                previous.setEnabled(true);
                previous.setVisibility(View.VISIBLE);
                next.setText("Next");
                previous.setText("Back");

            }
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };


    public void transistiontosplash(){
        Intent intent = new Intent(MainActivity.this, splashscreen.class);
        startActivity(intent);
        finish();
    }
}
