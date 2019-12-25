package com.example.attendanceplaystore;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AccountFragment extends Fragment {

    private Button logoutbtn;

    private DatabaseReference databaseReference;

    private FirebaseAuth mAuth;
    private TextView usernametxt,emailtxt;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account,container,false);

        logoutbtn = view.findViewById(R.id.logout);
        usernametxt = view.findViewById(R.id.usernametxt);
        emailtxt = view.findViewById(R.id.emailtxt);


        databaseReference = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();

        String name = user.getDisplayName();

        usernametxt.setText(name);

        String mail = user.getEmail();
        emailtxt.setText(mail);


        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent(getActivity(),signup.class);
                startActivity(intent);
                getActivity().finish();
            }
        });


        return view;
    }
}
