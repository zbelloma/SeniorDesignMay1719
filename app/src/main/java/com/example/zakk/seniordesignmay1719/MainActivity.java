package com.example.zakk.seniordesignmay1719;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import com.example.zakk.seniordesignmay1719.NativeBluetoothSocket;
import com.example.zakk.seniordesignmay1719.BluetoothSocketWrapper;
import android.bluetooth.le.BluetoothLeScanner;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AndroidException;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;

import static android.R.id.button1;
import static android.R.id.progress;

public class MainActivity extends Activity {

    private static final String TAG = "FirebaseUser";
    BluetoothAdapter mBluetoothAdapter;
    BluetoothSocketWrapper mConnected;
    BluetoothLeScanner testScanner;
    int REQUEST_ENABLE_BT = 1;
    int SHOWCASE_ID = 0;
    Set<BluetoothDevice> pairedDevices;
    //List<String> listViewData = new ArrayList<String>();
    //List<BluetoothDevice> deviceList = new ArrayList<>();
    //ListView lv;
    ArrayAdapter<String> arrAdapter;
    private Button connectBTN;



    private EditText email;
    private EditText password;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 1;
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
               MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION); //testing for android 6.0

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);

        findViewById(R.id.signIn).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                //Attempt sign in
                if(TextUtils.isEmpty(email.getText().toString())){
                    Toast.makeText(MainActivity.this, "Must enter an email.",
                            Toast.LENGTH_SHORT).show();
                } else if(TextUtils.isEmpty(password.getText().toString())){
                    Toast.makeText(MainActivity.this, "Must enter a password.",
                            Toast.LENGTH_SHORT).show();
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    signIn(email.getText().toString(), password.getText().toString());
                }

            }

        });


        findViewById(R.id.register).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                //Attempt to register a new user
                if(TextUtils.isEmpty(email.getText().toString())){
                    Toast.makeText(MainActivity.this, "Must enter an email.",
                            Toast.LENGTH_SHORT).show();
                } else if(TextUtils.isEmpty(password.getText().toString())){
                    Toast.makeText(MainActivity.this, "Must enter a password.",
                            Toast.LENGTH_SHORT).show();
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    register(email.getText().toString(), password.getText().toString());
                }

            }

        });
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    go_to_functional(user.getUid().toString());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }

            }
        };


    }


    @Override
    public void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }


    @Override
    public void onStop(){
        super.onStop();
        if(mAuthListener != null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void signIn(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                            Toast.makeText(MainActivity.this, "Sign in unsuccessful.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void register(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(MainActivity.this, "Account registration failed.",
                                    Toast.LENGTH_SHORT).show();
                        }


                    }
                });
    }


    private void go_to_functional(String user_id){
        progressBar.setVisibility(View.INVISIBLE);
        Intent signedIn = new Intent(this, activity_settings.class);
        signedIn.putExtra("USER_ID", user_id);
        startActivity(signedIn);
    }



    public void bypass(View view){
        Intent signedIn = new Intent(this, activity_settings.class);
        signedIn.putExtra("USER_ID", "temp");
        startActivity(signedIn);
    }


}

