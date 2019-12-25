package com.example.attendanceplaystore;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
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


public class AddFragment extends Fragment {

    FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private String period;
    int num;
    private String day;

    private EditText semnext,semdone;
    private TextView ed1,ed2,ed3,ed4,ed5,ed6,ed7,ed8,semtxtname;
    private EditText s1,s2,s3,s4,s5,s6,s7,s8;
    private Button next,done,savebtn,backbtn;
    private LinearLayout txtndspin,content;

    private String[] id,idd;
    private int tempp, temp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_add,container,false);

        mAuth = FirebaseAuth.getInstance();

        final FirebaseUser user = mAuth.getCurrentUser();
        final DatabaseReference mdatabase = FirebaseDatabase.getInstance().getReference();

        semnext = view.findViewById(R.id.semname);
        semdone = view.findViewById(R.id.semdone);
        next = view.findViewById(R.id.nextsem);
        done = view.findViewById(R.id.nextdone);
        txtndspin = view.findViewById(R.id.txtndspin);
        content = view.findViewById(R.id.table);
        savebtn = view.findViewById(R.id.savebtn2);
        backbtn = view.findViewById(R.id.backbtn);
        ed1=view.findViewById(R.id.ed1);
        ed2=view.findViewById(R.id.ed2);
        ed3=view.findViewById(R.id.ed3);
        ed4=view.findViewById(R.id.ed4);
        ed5=view.findViewById(R.id.ed5);
        ed6=view.findViewById(R.id.ed6);
        ed7=view.findViewById(R.id.ed7);
        ed8=view.findViewById(R.id.ed8);
        s1=view.findViewById(R.id.s1);
        s2=view.findViewById(R.id.s2);
        s3=view.findViewById(R.id.s3);
        s4=view.findViewById(R.id.s4);
        s5=view.findViewById(R.id.s5);
        s6=view.findViewById(R.id.s6);
        s7=view.findViewById(R.id.s7);
        s8=view.findViewById(R.id.s8);
        semtxtname=view.findViewById(R.id.semtxtname);

        String [] values = {"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
        final Spinner spinner = (Spinner) view.findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(), R.layout.spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);




        final TextView[] text = new TextView[10];
        final EditText[] editTexts = new EditText[10];
        semnext.setVisibility(View.VISIBLE);
        next.setVisibility(View.VISIBLE);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                semnext.setVisibility(View.INVISIBLE);
                next.setVisibility(View.INVISIBLE);

                semdone.setVisibility(View.VISIBLE);
                done.setVisibility(View.VISIBLE);
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                period = semdone.getText().toString();
                if(period.isEmpty()){
                    semdone.setError("Please enter a number less than 8");
                    semdone.requestFocus();
                    return;
                }
                num = Integer.parseInt(period);
                if(num>9){
                    semdone.setError("Number should be less or equal to 8");
                    semdone.requestFocus();
                    return;
                }

                id = new String[]{"ed1","ed2","ed3","ed4","ed5","ed6","ed7","ed8"};
                idd = new String[]{"s1","s2","s3","s4","s5","s6","s7","s8"};

                for(int i=0; i<num; i++){
                    temp = getResources().getIdentifier(id[i], "id", getActivity().getPackageName());
                    text[i] = (TextView)view.findViewById(temp);
                    tempp = getResources().getIdentifier(idd[i], "id", getActivity().getPackageName());
                    editTexts[i] = (EditText) view.findViewById(tempp);
                    text[i].setVisibility(View.VISIBLE);
                    editTexts[i].setVisibility(View.VISIBLE);
                }

                semdone.setVisibility(View.INVISIBLE);
                done.setVisibility(View.INVISIBLE);
                semtxtname.setText(semnext.getText().toString());
                semtxtname.setVisibility(View.VISIBLE);
                txtndspin.setVisibility(View.VISIBLE);
                content.setVisibility(View.VISIBLE);
                savebtn.setVisibility(View.VISIBLE);
                backbtn.setVisibility(View.VISIBLE);
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                day = parent.getItemIdAtPosition(position)+"";
                if(day.equals("0")){
                    day="Monday";
                }
                else if(day.equals("1")){
                    day="Tuesday";
                }
                else if(day.equals("2")){
                    day="Wednesday";
                }
                else if(day.equals("3")){
                    day="Thursday";
                }
                else if(day.equals("4")){
                    day="Friday";
                }
                else if(day.equals("5")){
                    day="Saturday";
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        savebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //String[] id,idd;
                    id = new String[]{"ed1","ed2","ed3","ed4","ed5","ed6","ed7","ed8"};
                    idd = new String[]{"s1","s2","s3","s4","s5","s6","s7","s8"};

                    //int tempp, temp;
                    for(int i=0; i<num; i++){
                        temp = getResources().getIdentifier(id[i], "id", getActivity().getPackageName());
                        text[i] = (TextView)view.findViewById(temp);
                        tempp = getResources().getIdentifier(idd[i], "id", getActivity().getPackageName());
                        editTexts[i] = (EditText) view.findViewById(tempp);

                        String check = editTexts[i].getText().toString();
                        if(check.isEmpty()){
                            editTexts[i].setError("Enter the name of the subject");
                            editTexts[i].requestFocus();
                            return;
                        }

                        mdatabase.child("my_users").child(user.getUid()).child(day).child(text[i].getText().toString()).setValue(editTexts[i].getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if(task.isSuccessful()){
                                    Toast.makeText(getActivity(),"saved",Toast.LENGTH_LONG).show();

                                }
                                else{
                                    Toast.makeText(getActivity(),"Error 404 found",Toast.LENGTH_LONG).show();
                                }

                            }

                        });

                        editTexts[i].getText().clear();

                    }



                }
            });

        finalcall();



        return  view;
    }

    private void finalcall(){

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtndspin.setVisibility(View.INVISIBLE);
                content.setVisibility(View.INVISIBLE);
                semtxtname.setVisibility(View.INVISIBLE);
                savebtn.setVisibility(View.INVISIBLE);
                backbtn.setVisibility(View.INVISIBLE);
                semdone.getText().clear();
                semdone.setVisibility(View.VISIBLE);
                done.setVisibility(View.VISIBLE);

            }
        });

    }




}
