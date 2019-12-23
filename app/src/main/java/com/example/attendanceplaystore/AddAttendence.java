//

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
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
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


public class AddAttendence extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference databaseReference;
    private Spinner spinner;
    private ArrayList<String> subject= new ArrayList<>();
    private ArrayList<String> period = new ArrayList<>();
    private ListView listperiod,listsubject;
    private String days,currentdate,day;
    private int num;
    private TextView datetxt;
    private RadioButton ones,onen,twos,twon,threes,threen,fours,fourn,fives,fiven,sixs,sixn,sevens,sevenn,holiday,eights,eightn;
    private RadioGroup r1,r2,r3,r4,r5,r6,r7,r8;
    private String[] id,rsid,rnid;
    private int temp, rstemp,rntemp;
    private Button addsavebtn;
    private final DatabaseReference mdatabase = FirebaseDatabase.getInstance().getReference();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_attendence);


        listsubject=findViewById(R.id.listsubject);
        listperiod=findViewById(R.id.listperiod);
        datetxt = findViewById(R.id.datetxt);
        ones = findViewById(R.id.ones);
        onen=findViewById(R.id.onen);
        twos=findViewById(R.id.twos);
        twon=findViewById(R.id.twon);
        threes=findViewById(R.id.threes);
        threen=findViewById(R.id.threen);
        fours=findViewById(R.id.fours);
        fourn=findViewById(R.id.fourn);
        fives=findViewById(R.id.fives);
        fiven=findViewById(R.id.fiven);
        sixs=findViewById(R.id.sixs);
        sixn=findViewById(R.id.sixn);
        sevens=findViewById(R.id.sevens);
        sevenn=findViewById(R.id.sevenn);
        eights = findViewById(R.id.eights);
        eightn = findViewById(R.id.eightn);

        holiday=findViewById(R.id.addholiday);
        r1=findViewById(R.id.r1);
        r2=findViewById(R.id.r2);
        r3=findViewById(R.id.r3);
        r4=findViewById(R.id.r4);
        r5=findViewById(R.id.r5);
        r6=findViewById(R.id.r6);
        r7=findViewById(R.id.r7);
        r8=findViewById(R.id.r8);
        addsavebtn = findViewById(R.id.addsavebtn);

        final RadioGroup[] radio = new RadioGroup[10];
        final RadioButton[] radioS = new RadioButton[10];
        final RadioButton[] radioN = new RadioButton[10];
        databaseReference = FirebaseDatabase.getInstance().getReference();



        pickedIntent();
        datetxt.setText(day);

        final ArrayAdapter<String> periodarray = new ArrayAdapter<String>(AddAttendence.this,android.R.layout.simple_list_item_1, period){

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
        listperiod.setAdapter(periodarray);

        final ArrayAdapter<String> subjectarray = new ArrayAdapter<String>(AddAttendence.this,android.R.layout.simple_list_item_1, subject){
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
        listsubject.setAdapter(subjectarray);

        mAuth = FirebaseAuth.getInstance();

        final FirebaseUser user = mAuth.getCurrentUser();


        databaseReference.child("my_users").child(user.getUid()).child(day).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                String value = dataSnapshot.getValue(String.class);
                subject.add(value);
                subjectarray.notifyDataSetChanged();
                String key = dataSnapshot.getKey();
                period.add(key);
                periodarray.notifyDataSetChanged();


                String list = listperiod.getAdapter().getCount()+"";
                num=Integer.parseInt(list+"");
                id = new String[]{"r1","r2","r3","r4","r5","r6","r7","r8"};


                for(int i=0; i<num; i++) {
                    temp = getResources().getIdentifier(id[i], "id", AddAttendence.this.getPackageName());
                    radio[i] = (RadioGroup) findViewById(temp);
                    radio[i].setVisibility(View.VISIBLE);
                }
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

//        databaseReference.child("my_users").child(user.getUid()).child(day).addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//                String value = dataSnapshot.getValue(String.class);
//                String key = dataSnapshot.getKey();
//                period.add(key);
//                subject.add(value);
//                periodarray.notifyDataSetChanged();
//                subjectarray.notifyDataSetChanged();
//

//
//
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

//        id = new String[]{"r1","r2","r3","r4","r5","r6","r7","r8"};
//
//
//                for(int i=0; i<num; i++) {
//                    temp = getResources().getIdentifier(id[i], "id", AddAttendence.this.getPackageName());
//                    radio[i] = (RadioGroup) findViewById(temp);
//                    radio[i].setVisibility(View.VISIBLE);
//                }

//        addsavebtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //databaseReference.child("my_users").child(user.getUid()).child(currentdate).setValue()
//
////                String list = listperiod.getAdapter().getCount()+"";
////                num=Integer.parseInt(list+"");
//                id = new String[]{"r1","r2","r3","r4","r5","r6","r7","r8"};
//                rsid = new String[]{"ones","twos","threes","fours","fives","sixs","sevens","eights"};
//                rnid = new String[]{"onen","twon","threen","fourn","fiven","sixn","sevenn","eightn"};
//
//
//                for(int i=0; i<8; i++) {
//                    temp = getResources().getIdentifier(id[i], "id", AddAttendence.this.getPackageName());
//                    radio[i] = (RadioGroup) findViewById(temp);
//                    radio[i].setVisibility(View.VISIBLE);
//
//                    rstemp = getResources().getIdentifier(rsid[i],"rsid",AddAttendence.this.getPackageName());
//                    radioS[i] = (RadioButton) findViewById(rstemp);
//
//                    rntemp = getResources().getIdentifier(rnid[i],"rnid",AddAttendence.this.getPackageName());
//                    radioN[i] = (RadioButton) findViewById(rntemp);
//
//                    //radioN[i].setText("hi");
//
//                    try {
//                        if (radioS[i].isChecked()) {
//                            databaseReference.child("my_users").child(user.getUid()).child("Dates").child(currentdate).child(i + "").setValue("Present");
//                        } else if (radioN[i].isChecked()) {
//                            databaseReference.child("my_users").child(user.getUid()).child("Dates").child(currentdate).child(i + "").setValue("Absent");
//                        } else {
//                            Toast.makeText(AddAttendence.this, "Please select all buttons", Toast.LENGTH_LONG).show();
//                        }
//                    }catch (Exception e){
//                        Toast.makeText(AddAttendence.this,e.getMessage(),Toast.LENGTH_LONG).show();
//                    }
//
//
//
//                }
////
////                if(ones.isChecked()){
////                    Toast.makeText(Addactivity.this, "hi", Toast.LENGTH_SHORT).show();
////                }
////                else if(onen.isChecked()){
////                    Toast.makeText(Addactivity.this,"hello",Toast.LENGTH_LONG).show();
////                }
//
//
//
//
//            }
//        });

    }

    private void pickedIntent(){
        Intent intent = getIntent();
        currentdate = intent.getStringExtra("currentDate");
        day = intent.getStringExtra("currentDay");
    }

}
