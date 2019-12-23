package com.example.attendanceplaystore;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class TaskFragment extends Fragment  {

    private TextView datetxt;
    private Button addbtn,showbtn,datebtn;
    private FirebaseAuth mAuth;

    private String currentdate;
    private String dayofTheweek;

    private FirebaseUser user;
    private final DatabaseReference mdatabase = FirebaseDatabase.getInstance().getReference();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task,container,false);



        datebtn = view.findViewById(R.id.datebtn);
        addbtn = view.findViewById(R.id.addbtn);
        showbtn = view.findViewById(R.id.showbtn);
        datetxt = view.findViewById(R.id.showdatetxt);


        mAuth = FirebaseAuth.getInstance();

        user = mAuth.getCurrentUser();


        datebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });





        return  view;

    }

    DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            currentdate = String.valueOf(dayOfMonth) + "-" + String.valueOf(monthOfYear+1) + "-" + String.valueOf(year);
            datetxt.setText(currentdate);
            SimpleDateFormat simpledateformat = new SimpleDateFormat("EEEE");
            Date date = new Date(year,monthOfYear,dayOfMonth-1);
            dayofTheweek = simpledateformat.format(date);

            mdatabase.child("my_users").child(user.getUid()).child("days").child(currentdate).setValue(dayofTheweek).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(getActivity(),"daySaved",Toast.LENGTH_LONG).show();
                    }
                }
            });
            addbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mdatabase.child("my_users").child(user.getUid()).child("Dates").child(currentdate).setValue(dayofTheweek).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                           if(task.isSuccessful()){

                           }
                        }
                    });
                    Intent intent = new Intent(getActivity(),AddAttendence.class);
                    intent.putExtra("currentDate",currentdate);
                    intent.putExtra("currentDay",dayofTheweek);

                    startActivity(intent);
                }

            });
            showbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(),ShowAttendence.class);
                    intent.putExtra("currentDate",currentdate);
                    intent.putExtra("currentDay",dayofTheweek);
                    addbtn.setText(currentdate);
                    startActivity(intent);
                }
            });

        }
    };

    private void showDatePicker() {
        DatePickerFragment date = new DatePickerFragment();
        /**
         * Set Up Current Date Into dialog
         */
        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);
        /**
         * Set Call back to capture selected date
         */
        date.setCallBack(ondate);
        date.show(getFragmentManager(), "Date Picker");
    }



}
