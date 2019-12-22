package com.example.attendanceplaystore;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class slideadapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;


    public slideadapter (Context context){
        this.context = context;
    }

    public int[] slide_images ={R.drawable.clock,R.drawable.docs, R.drawable.increase};

    public String[] slide_headings = {"ADD IN","TASK","ANALYSIS"};

    public String[] slide_description = {"Enter your subject and no of periods according to your timetable for respective days","See the attendance of the particular subject whether you present or not",
            "Analysis each subject attendance and create an report of overall attendance"};


    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == (RelativeLayout) o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);

        TextView slidetext= view.findViewById(R.id.text);
        ImageView slideimage = view.findViewById(R.id.slideimage);
        TextView slidehead = view.findViewById(R.id.slidehead);

        slidetext.setText(slide_description[position]);
        slidehead.setText(slide_headings[position]);
        slideimage.setImageResource(slide_images[position]);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((RelativeLayout)object);

    }
}
