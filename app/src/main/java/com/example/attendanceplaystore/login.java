package com.example.attendanceplaystore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;

public class login extends AppCompatActivity implements View.OnClickListener{


    FirebaseAuth mauth;
    private EditText email,pass,name;
    private Button loginbtn;
    private TextView signup;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mauth=FirebaseAuth.getInstance();
        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        pass=findViewById(R.id.pass);
        pass.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    onClick(loginbtn);
                }
                return false;
            }
        });
        loginbtn=findViewById(R.id.loginbtn);
        signup=findViewById(R.id.signuptxt);

        progressBar=(ProgressBar) findViewById(R.id.progressbar);
        loginbtn.setOnClickListener(login.this);
        signup.setOnClickListener(login.this);

    }

    private void login(){
        String mail = email.getText().toString().trim();
        String password = pass.getText().toString().trim();

        if (mail.isEmpty()){
            email.setError("Email is required");
            email.requestFocus();
            return;
        }
        if(password.isEmpty())
        {
            pass.setError("Password is required");
            pass.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(mail).matches())
        {
            email.setError("Please enter valid E-mail addresses");
            email.requestFocus();
            return;
        }
        if(password.length()<6){
            pass.setError("Minimum length of password should be 6");
            pass.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mauth.signInWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
                    //FirebaseDatabase.getInstance().getReference().child("my_users").child(task.getResult().getUser().getUid()).child("username").setValue(name.getText().toString());
                    Toast.makeText(login.this, "looged in",Toast.LENGTH_LONG).show();

                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(name.getText().toString()).build();

                    FirebaseUser user = mauth.getCurrentUser();
                    if(user!=null) {
                        user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d("Tag","User profile updated");
                                    Toast.makeText(login.this, "saved", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                    transistiontodashboard();
                }
                else{
                    Toast.makeText(login.this,task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void transistiontodashboard(){
        Intent intent = new Intent(login.this,Dashboard.class);
        startActivity(intent);
        finish();
    }


    private void transistiontosignup(){
        Intent intent = new Intent(login.this, signup.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.loginbtn:
                login();
                break;
            case R.id.signuptxt:
                transistiontosignup();
                break;
        }
    }
    public void rootlayout(View view){

        try{
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        catch (Exception e){
            e.printStackTrace();
        }


    }

}
