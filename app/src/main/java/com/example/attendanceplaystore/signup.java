package com.example.attendanceplaystore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;

public class signup extends AppCompatActivity implements View.OnClickListener {

    private EditText user,email,pass;
    private Button signup;
    private TextView login;
    private ProgressBar progressBar;

    GoogleApiClient mGoogleSignInClient;
    SignInButton Gsign;
    FirebaseAuth mAuth;
    private final static int RC_SIGN_IN =2;
    FirebaseAuth.AuthStateListener mAuthlistener;

    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthlistener);
        if(mAuth.getCurrentUser()!=null){
            startActivity(new Intent(signup.this,Dashboard.class));
            //mAuth.signOut();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        user = findViewById(R.id.username);
        email = findViewById(R.id.signupemail);
        pass = findViewById(R.id.signuppass);
        pass.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    onClick(signup);
                }
                return false;
            }
        });
        signup = findViewById(R.id.signupbtn);
        login = findViewById(R.id.logintxt);
        Gsign = findViewById(R.id.sign_in_button);
        progressBar= (ProgressBar) findViewById(R.id.progressbar);

        mAuth = FirebaseAuth.getInstance();

        signup.setOnClickListener(signup.this);
        login.setOnClickListener(signup.this);
        Gsign.setOnClickListener(this);

        mAuthlistener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() != null){
                    //transistiontodashboard();
                }

            }
        };

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(signup.this,"Please try again later!",Toast.LENGTH_LONG).show();
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleSignInClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                e.printStackTrace();
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        progressBar.setVisibility(View.VISIBLE);

        String name = user.getText().toString().trim();

        if (name.isEmpty()){
            user.setError("A Name is required");
            user.requestFocus();
            progressBar.setVisibility(View.GONE);
            return;
        }
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(signup.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressBar.setVisibility(View.GONE);
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseDatabase.getInstance().getReference().child("my_users").child(task.getResult().getUser().getUid()).child("email").setValue(email.getText().toString());


                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(user.getText().toString()).build();

                            FirebaseUser user = mAuth.getCurrentUser();
                            if(user!=null) {
                                user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Log.d("Tag","User profile updated");
                                        }
                                        else{
                                            Toast.makeText(signup.this,"Please try again later!",Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }

                            transistiontodashboard();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Tag", "signInWithCredential:failure", task.getException());
                            Toast.makeText(signup.this,"Oops!, Authenication Failed",Toast.LENGTH_LONG).show();
                            //updateUI(null);
                        }
                    }
                });

    }

    private void authenicateviamail(){

        String mail = email.getText().toString().trim();
        String password = pass.getText().toString().trim();
        String name = user.getText().toString().trim();

        if (name.isEmpty()){
            user.setError("Name is required");
            user.requestFocus();
            return;
        }
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

        mAuth.createUserWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    progressBar.setVisibility(View.GONE);

                    FirebaseDatabase.getInstance().getReference().child("my_users").child(task.getResult().getUser().getUid()).child("email").setValue(email.getText().toString());

                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(user.getText().toString()).build();

                    FirebaseUser user = mAuth.getCurrentUser();
                    if(user!=null) {
                        user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d("Tag","User profile updated");
                                }
                                else{
                                    Toast.makeText(signup.this,"Please try again later!",Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }

                    transistiontodashboard();

                }

                else {
                    Toast.makeText(signup.this,"Please try again later", Toast.LENGTH_LONG).show();
                }


            }

        });

    }


    private void transistiontodashboard(){
        Intent intent = new Intent(signup.this,Dashboard.class);
        startActivity(intent);
        finish();
    }

    private void transistiontologin(){
        Intent intent = new Intent(signup.this, login.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.signupbtn:
                authenicateviamail();
                break;

                case R.id.logintxt:
                    transistiontologin();
                    break;

            case R.id.sign_in_button:
                signIn();

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
