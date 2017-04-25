package com.example.zakk.seniordesignmay1719;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;

import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;

public class activity_settings extends Activity {

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
    private Button integrationBTN;
    private Button wavelengthBTN;
    private Boolean connected = false;
    //private Boolean scanning = false;
    private ProgressBar scanningProgress;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        findViewById(R.id.SettingsMenu).setVisibility(View.GONE);
        findViewById(R.id.MainMenu).setVisibility(View.VISIBLE);
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

        scanningProgress = (ProgressBar) findViewById(R.id.scanningSpinner);
        //scanningProgress.getIndeterminateDrawable().setColorFilter(0x000000, android.graphics.PorterDuff.Mode.MULTIPLY);
        scanningProgress.setVisibility(View.GONE);

        connectBTN = (Button) findViewById(R.id.enableButton);
        if(connected) {
            connectBTN.setText("New Test");
            connectBTN.setBackgroundColor(0xff00ff00); //Set Button to green
        }

        connectBTN.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(!connected){
                    scanningProgress.setVisibility(View.VISIBLE);
                    //This button should connect to the system
                    mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                    String raspMAC = "00:1B:DC:0F:AC:3C";
                    device = mBluetoothAdapter.getRemoteDevice(raspMAC);
                    mBluetoothAdapter.cancelDiscovery();


                    ConnectThread mConnectThread = new ConnectThread(device, false, mBluetoothAdapter);
                    //mConnectThread.start();

                    try{
                        mConnected = mConnectThread.connect();
                        activity_settings.this.mOut = new ConnectedThread(mConnected); //Starts the bluetooth connection thread
                        connected = true;
                        scanningProgress.setVisibility(View.INVISIBLE);
                        connectBTN.setText("New Test");
                        connectBTN.setBackgroundColor(0xff00ff00); //Set Button to green

                    } catch(IOException e){
                        scanningProgress.setVisibility(View.INVISIBLE);
                        Log.e("CONNECT", "Connection error: " + e.getMessage());
                        Toast toast = Toast.makeText(activity_settings.this, "Unable to connect to device.", Toast.LENGTH_LONG);
                        toast.show();
                    }
                } else {
                    Log.i("UI","Original color");
                    scanningProgress.setVisibility(View.VISIBLE);
                    connectBTN.setText("TESTING");
                    //connectBTN.setBackgroundColor(0xffff0000); //Set button to red

                    //this should do what we want
                    //run();
                    new runAsync().execute(); //Seperate thread changes the UI
                    //Executes communication



                    Log.i("UI","Second color");
                }
            }
        });

        integrationBTN = (Button) findViewById(R.id.integrationBTN);
        integrationBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //Currently none functional, toast user to say work in progress.
                Toast toast = Toast.makeText(activity_settings.this, "Work in progress...", Toast.LENGTH_LONG);
                toast.show();
            }
        });

        wavelengthBTN = (Button) findViewById(R.id.wavelengthBTN);
        wavelengthBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Currently none functional, toast user to say work in progress.
                Toast toast = Toast.makeText(activity_settings.this, "Work in progress...", Toast.LENGTH_LONG);
                toast.show();
            }
        });

        Button openSettingsButton = (Button) findViewById(R.id.OpenSettings);
        openSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSettings();
            }
        });

        Button closeSettingsButton = (Button) findViewById(R.id.settingsBackBTN);
        closeSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSettings();
            }
        });

        Button shutDownButton = (Button) findViewById(R.id.shutdownBTN);
        shutDownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(connected) {
                    Toast toast = Toast.makeText(activity_settings.this, "Shutting down system.", Toast.LENGTH_LONG);
                    toast.show();
                    mOut.shutdown();
                } else {
                    Toast toast = Toast.makeText(activity_settings.this, "Must establish connection first.", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });

    }

    private class runAsync extends AsyncTask<Void, Void, Void> {

        private String toastMess = "SHOULD NOT HAPPEN";

        @Override
        protected Void doInBackground(Void... args) {
            //Maybe need to refresh the UI here?
            //...
            toastMess = run(); //Unsure of exactly how long runs will take, should not perform the communication in this thread
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            connectBTN.setText("New Test");
            connectBTN.setBackgroundColor(0xff00ff00); //Set button back to green
            scanningProgress.setVisibility(View.GONE);
            Toast toast2 = Toast.makeText(activity_settings.this, toastMess, Toast.LENGTH_LONG);
            toast2.show();
            super.onPostExecute(result);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            scanningProgress.setVisibility(View.VISIBLE);
            connectBTN.setText("TESTING");
            connectBTN.setBackgroundColor(0xffff0000);

        }
    }

    /*private class runDBAsync extends AsyncTask<Void, Void, Void> {

        private byte[] str_data;
        private String USER_ID;

        private runDBAsync(byte[] response, String userID){
            this.str_data = response;
            this.USER_ID = userID;
        }

        @Override
        protected Void doInBackground(Void... args) {

            data = new Data(this.str_data, this.USER_ID, System.currentTimeMillis());

            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference ref = database.getReference();
            ref.child("data").child(data.id).setValue(data);

            Toast toast2 = Toast.makeText(activity_settings.this, "Scan Data added to DB", Toast.LENGTH_LONG);
            toast2.show();

            return null;
        }
    }*/


    public String run() {
        //response = "";

        this.response = this.mOut.scan();


        if(response.length() > 18000){

            data = new Data(response, userID, System.currentTimeMillis());

            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference ref = database.getReference();
            ref.child("data").child(data.id).setValue(data);
            return "Scan complete...\nAdded to database";

        } else if(response.length() == 0) {
            //Toast toast = Toast.makeText(this.getApplicationContext(), "No data was received.", Toast.LENGTH_LONG);
            //toast.show();
            return "No data was received";
        } else {
            //Toast toast = Toast.makeText(this.getApplicationContext(), "Incorrect data was received.", Toast.LENGTH_LONG);
            //toast.show();
            return "Incorrect data was received";
        }
    }

    public void dbview(View view){
        Intent intent = new Intent(this, DisplayDBActivity.class);
        startActivity(intent);
    }


    public void showSettings(){
        findViewById(R.id.MainMenu).setVisibility(View.GONE);
        findViewById(R.id.SettingsMenu).setVisibility(View.VISIBLE);
    }

    public void hideSettings(){
        findViewById(R.id.MainMenu).setVisibility(View.VISIBLE);
        findViewById(R.id.SettingsMenu).setVisibility(View.GONE);
    }

    public void beginTutorial(View view){

        Button button1 = (Button) findViewById(R.id.tutorial);
        Button button2 = (Button) findViewById(R.id.db_button);
        Button button3 = (Button) findViewById(R.id.enableButton);
        Button button4 = (Button) findViewById(R.id.OpenSettings);
       // new MaterialShowcaseView.Builder(this).setTarget(button1).setDismissText("DISMISS").setContentText("This button starts the tutorial, but you already know that").show();
        ShowcaseConfig config = new ShowcaseConfig();
        config.setDelay(500);
        MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(this);
        sequence.setConfig(config);
        sequence.addSequenceItem(button1, "This button starts the tutorial, but you already know that", "Got It!");
        sequence.addSequenceItem(button3, "This button will start a connection to the Raspberry Pi", "Got It!");
        sequence.addSequenceItem(button2, "This button will go to the database", "Got It!");
        sequence.addSequenceItem(button4, "This button will take you to the settings menu where you can shutdown the Pi and change spectrometer settings", "Got It!");
        //sequence.addSequenceItem(button4, " ");

        sequence.start();

    }

}
