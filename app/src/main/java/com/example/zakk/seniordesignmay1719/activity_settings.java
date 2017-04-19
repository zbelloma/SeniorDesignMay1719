package com.example.zakk.seniordesignmay1719;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;

public class activity_settings extends AppCompatActivity {

    public BluetoothAdapter mBluetoothAdapter;
    private BluetoothSocketWrapper mConnected;
    public BluetoothDevice device;
    public ConnectedThread mOut;
    public Data data;
    private String userID;
    private String response;
    private FirebaseAuth mAuth;
    private Button signOut;
    private Button connectBTN;
    private Boolean connected = false;
    private ProgressBar scanning;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        if(mConnected != null){
            connected = true;
        }
        mAuth = FirebaseAuth.getInstance();

        Log.i("USER:", mAuth.getCurrentUser().getUid().toString());
        Intent intent = getIntent();
        userID = intent.getStringExtra("USER_ID");

        signOut = (Button) findViewById(R.id.signOut);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mAuth.getCurrentUser() != null){
                    mAuth.signOut();
                }
                        try{
                            if(mConnected != null) {
                                mOut.inStream.close();
                                mOut.outStream.close();
                                mConnected.getUnderlyingSocket().close();
                                finish();
                            } else {

                               finish();
                            }
                    } catch (IOException e) {
                        Log.i("Closing:", "Exception closing the socket: " + e.getMessage());
                        finish();
                    }

                finish();
            }
        });

        scanning = (ProgressBar) findViewById(R.id.scanningSpinner);
        scanning.setVisibility(View.INVISIBLE);
        connectBTN = (Button) findViewById(R.id.enableButton);
        if(connected) {
            connectBTN.setText("New Test");
            connectBTN.setBackgroundColor(0xff00ff00); //Set Button to green
        } else {
            connectBTN.setBackgroundColor(0xffffff00); //Set button to yellow
        }
        connectBTN.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(!connected){
                    //This button should connect to the system
                    mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                    String raspMAC = "00:1B:DC:0F:AC:3C";
                    device = mBluetoothAdapter.getRemoteDevice(raspMAC);
                    mBluetoothAdapter.cancelDiscovery();


                    ConnectThread mConnectThread = new ConnectThread(device, false, mBluetoothAdapter);

                    try{
                        mConnected = mConnectThread.connect();
                        activity_settings.this.mOut = new ConnectedThread(mConnected); //Starts the bluetooth connection thread
                        connected = true;
                        connectBTN.setText("New Test");
                        connectBTN.setBackgroundColor(0xff00ff00); //Set Button to green

                    } catch(IOException e){
                        Log.e("CONNECT", "Connection error: " + e.getMessage());
                        Toast toast = Toast.makeText(activity_settings.this, "Unable to connect to device.", Toast.LENGTH_LONG);
                        toast.show();
                    }
                } else {
                    scanning.setVisibility(View.VISIBLE);
                    connectBTN.setText("TESTING");
                    connectBTN.setBackgroundColor(0xffff0000); //Set button to red
                    run();
                    connectBTN.setText("New Test");
                    connectBTN.setBackgroundColor(0xff00ff00); //Set button back to green
                    scanning.setVisibility(View.INVISIBLE);
                }
            }
        });
        Log.i("USER_ID: ", userID);
    }

    public void run() {
        response = "";

        this.response = this.mOut.scan();

        if(response.length() > 18000){

            data = new Data(response, this.userID, System.currentTimeMillis());
            //final FirebaseDatabase database = FirebaseDatabase.getInstance();
            //DatabaseReference ref = database.getReference();
            //ref.child("data").child(data.id).setValue(data);

            Toast toast = Toast.makeText(this.getApplicationContext(), "Scan Data added to DB", Toast.LENGTH_LONG);
            toast.show();
        } else if ( 0 < response.length() && response.length() < 18000){
            Toast toast = Toast.makeText(this.getApplicationContext(), "Incorrect data was received.", Toast.LENGTH_LONG);
            toast.show();
        } else {
            Toast toast = Toast.makeText(this.getApplicationContext(), "No data was received.", Toast.LENGTH_LONG);
            toast.show();
        }

        //Log.i("Dislay", data.getTime() + "\n" + data.numScans + "\n" + data.getIntegrationTime() + "\n");

    }

}
