package com.example.zakk.seniordesignmay1719;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class activity_settings extends AppCompatActivity {

    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Bundle bundle = getIntent().getExtras();
        if(bundle.getString("USER_ID") != null){
            userID = bundle.getString("USER_ID");
            Log.i("USERID:", userID);
        }
    }


}
