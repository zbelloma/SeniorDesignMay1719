package com.example.zakk.seniordesignmay1719;


import android.content.Intent;

import android.support.annotation.NonNull;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;

import android.widget.ProgressBar;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;





public class MainActivity extends AppCompatActivity {




    private static final String TAG = "Login";
    private FirebaseAuth authentication;
    private FirebaseAuth.AuthStateListener authListener;

    private Button signInBtn;
    private Button registerBtn;
    private EditText email;
    private EditText password;
    private ProgressBar progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        authentication = FirebaseAuth.getInstance();
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        progress = (ProgressBar) findViewById(R.id.progressBar);

          //State changing for user signed in and out...
        authListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if(user != null) {
                    //User successfully signed in.
                    Log.i(TAG, "Successful sign in: " + user.getUid());
                    Intent connected = new Intent(MainActivity.this, activity_settings.class);
                    connected.putExtra("USER_ID", user.getUid());
                    startActivity(connected);
                } else {
                    //User signed out
                    Log.i(TAG, "User: " + user.getEmail() + " signed out");
                }

            }
        };

        signInBtn = (Button)findViewById(R.id.signIn);
        signInBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(email.getText().toString() == ""){
                    Log.i("Email", "Enter email.");
                    Toast toast = Toast.makeText(v.getContext(), "Must enter an email.", Toast.LENGTH_LONG);
                    toast.show();
                } else if(password.getText().toString() == "" || password.getText().toString() == null){
                    Log.i("Password", "Enter password.");
                    Toast toast = Toast.makeText(v.getContext(), "Must enter a password.", Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    Log.i("Login: ", "Attempting login.");
                    progress.setVisibility(View.VISIBLE);
                    attemptSignIn(email.getText().toString(), password.getText().toString());
                }
            }

        });

        registerBtn = (Button) findViewById(R.id.register);
        registerBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(email.getText().toString() == null || email.getText().toString() == ""){
                    Toast toast = Toast.makeText(v.getContext(), "Must enter an email.", Toast.LENGTH_LONG);
                    toast.show();
                } else if(password.getText().toString() == "" || password.getText().toString() == null){
                    Toast toast = Toast.makeText(v.getContext(), "Must enter a password.", Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    progress.setVisibility(View.VISIBLE);

                    registerUser(email.getText().toString(), password.getText().toString());
                }
            }

        });

    }

    @Override
    public void onStart(){
        super.onStart();
        authentication.addAuthStateListener(authListener);
    }

    @Override
    public void onStop(){
        super.onStop();
        if(authListener != null){
            authentication.removeAuthStateListener(authListener);
        }
    }

    private void attemptSignIn(String email, String password){

        if(!validate(email)){
            progress.setVisibility(View.INVISIBLE);
            Toast toast = Toast.makeText(this.getApplicationContext(), "Please enter a valid email.", Toast.LENGTH_LONG);
            toast.show();
        } else {

            authentication.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                //Login did not work
                                progress.setVisibility(View.INVISIBLE);
                                Toast toast = Toast.makeText(MainActivity.this.getApplicationContext(), "Unsuccessful login.", Toast.LENGTH_LONG);
                                toast.show();
                                //If the login was successful, the AuthStateChanged will handle the work
                            }
                        }
                    });
        }
    }

    private void registerUser(String email, String password){
        if(!validate(email)){
            progress.setVisibility(View.INVISIBLE);
            Toast toast = Toast.makeText(this.getApplicationContext(), "Please enter a valid email.", Toast.LENGTH_LONG);
            toast.show();
        } else {
            authentication.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>(){
                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task){
                           if(!task.isSuccessful()){
                               Toast toast = Toast.makeText(MainActivity.this.getApplicationContext(), "Registration failed.", Toast.LENGTH_LONG);
                               toast.show();
                           }
                       }
                    });
        }

    }


    private boolean validate(String email){
        if(email.contains("@")){
            return true;
        } else {
            return false;
        }
    }



/*    public void beginTutorial(View view){
        Button button1 = (Button) findViewById(R.id.tutorial);
        Button button2 = (Button) findViewById(R.id.db_button);
        Button button3 = (Button) findViewById(R.id.enableButton);
       // new MaterialShowcaseView.Builder(this).setTarget(button1).setDismissText("DISMISS").setContentText("This button starts the tutorial, but you already know that").show();
        ShowcaseConfig config = new ShowcaseConfig();
        config.setDelay(500);
        MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(this , "ID");
        sequence.setConfig(config);
        sequence.addSequenceItem(button1, "This button starts the tutorial, but you already know that", "Got It!");
        sequence.addSequenceItem(button3, "This button will start a connection to the Raspberry Pi", "Got It!");
        sequence.addSequenceItem(button2, "This button will go to the database", "Got It!");

        sequence.start();

    }*/

}

