package com.example.attendanceplaystore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ShowAttendence extends AppCompatActivity {
    private String currentdate,day;
    private ArrayList<String> subjectlist= new ArrayList<>();
    private ArrayList<String> periodlist = new ArrayList<>();
    private ArrayList<String> statuslist = new ArrayList<>();
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private ListView totperiods,totsubjects,status;
    private TextView datetxt,holiday;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_attendence);

        totperiods = findViewById(R.id.per);
        totsubjects = findViewById(R.id.sub);
        status = findViewById(R.id.status);
        datetxt = findViewById(R.id.showdatetxt);
        holiday = findViewById(R.id.showholiday);
        databaseReference = FirebaseDatabase.getInstance().getReference();


        pickedIntent();



        final ArrayAdapter<String> periodarrays = new ArrayAdapter<String>(ShowAttendence.this,android.R.layout.simple_list_item_1, periodlist){

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view =super.getView(position, convertView, parent);

                TextView textView=(TextView) view.findViewById(android.R.id.text1);
                textView.setTextColor(Color.WHITE);
                textView.setGravity(Gravity.CENTER);

                return view;
            }

        };
        totperiods.setAdapter(periodarrays);

        final ArrayAdapter<String> subjectarrays = new ArrayAdapter<String>(ShowAttendence.this,android.R.layout.simple_list_item_1, subjectlist){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view =super.getView(position, convertView, parent);

                TextView textView=(TextView) view.findViewById(android.R.id.text1);
                textView.setTextColor(Color.WHITE);
                textView.setGravity(Gravity.CENTER);

                return view;
            }
        };
        totsubjects.setAdapter(subjectarrays);
        final ArrayAdapter<String> statusarray = new ArrayAdapter<String>(ShowAttendence.this,android.R.layout.simple_list_item_1, statuslist){

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view =super.getView(position, convertView, parent);

                TextView textView=(TextView) view.findViewById(android.R.id.text1);
                textView.setTextColor(Color.WHITE);
                textView.setGravity(Gravity.CENTER);

                return view;
            }

        };
        status.setAdapter(statusarray);

        mAuth = FirebaseAuth.getInstance();

        final FirebaseUser user = mAuth.getCurrentUser();


        databaseReference.child("my_users").child(user.getUid()).child(day).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                String value = dataSnapshot.getValue(String.class);
                subjectlist.add(value);
                subjectarrays.notifyDataSetChanged();
                String key = dataSnapshot.getKey();
                periodlist.add(key);
                periodarrays.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference.child("my_users").child(user.getUid()).child("Dates").child(currentdate).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                String values = dataSnapshot.getKey();
                statuslist.add(values);
                statusarray.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void pickedIntent(){
        Intent intent = getIntent();
        currentdate = intent.getStringExtra("currentDate");
        day = intent.getStringExtra("currentDay");


        if(currentdate==null){
            Toast.makeText(ShowAttendence.this,"null",Toast.LENGTH_LONG).show();
            return;
        }
        datetxt.setText(currentdate);
        holiday.setText(day);

    }
}
