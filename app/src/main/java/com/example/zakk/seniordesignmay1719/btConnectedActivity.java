package com.example.zakk.seniordesignmay1719;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ProgressBar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;

public class btConnectedActivity extends AppCompatActivity {

    public BluetoothAdapter mBluetoothAdapter;
    public BluetoothSocketWrapper mConnected;
    public BluetoothDevice device;
    public ConnectedThread mOut;
    public String response;
    public Data data;
    public boolean acknowledged;
    //public ProgressBar spinner;
    private TextView process;
    private Button runBTN;
    private ImageButton settingsBTN;
    private Button db_BTN;
    private Button shutdownBTN;
    private Button backBTN;
    private Button integrationBTN;
    private Button wavelengthBTN;
    private Button settingsBackBTN;
    private EditText integrationTime;
    private EditText wavelengthStart;
    private EditText wavelengthEnd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bt_connected);

        this.process =(TextView)findViewById(R.id.textView);
        this.process.setVisibility(View.GONE);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        String raspMAC = "00:1B:DC:0F:AC:3C";
        device = mBluetoothAdapter.getRemoteDevice(raspMAC);
        mBluetoothAdapter.cancelDiscovery();


        ConnectThread mConnectThread = new ConnectThread(device, false, mBluetoothAdapter);

        try{
            mConnected = mConnectThread.connect();
            this.mOut = new ConnectedThread(mConnected); //Starts the bluetooth connection thread
        } catch(IOException e){
            Log.e("CONNECT", "Connection error: " + e.getMessage());
            Toast toast = Toast.makeText(this.getApplicationContext(), "Unable to connect to device.", Toast.LENGTH_LONG);
            toast.show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);


        }

        runBTN = (Button)findViewById(R.id.runBTN);
        settingsBTN = (ImageButton)findViewById(R.id.settingsBTN);
        db_BTN = (Button)findViewById(R.id.db_BTN);
        shutdownBTN = (Button)findViewById(R.id.shutdownBTN);
        backBTN = (Button)findViewById(R.id.backBtn);

        runBTN.setVisibility(View.VISIBLE);
        db_BTN.setVisibility(View.VISIBLE);
        shutdownBTN.setVisibility(View.VISIBLE);
        backBTN.setVisibility(View.VISIBLE);
        settingsBTN.setVisibility(View.VISIBLE);

        integrationBTN = (Button)findViewById(R.id.integrationBTN);
        integrationTime = (EditText)findViewById(R.id.integrationTime);
        wavelengthBTN = (Button)findViewById(R.id.wavelengthBTN);
        wavelengthStart = (EditText)findViewById(R.id.wavelengthStart);
        wavelengthEnd = (EditText)findViewById(R.id.wavelengthEnd);
        settingsBackBTN = (Button)findViewById(R.id.settingsBackBTN);

        integrationBTN.setVisibility(View.GONE);
        integrationTime.setVisibility(View.GONE);
        wavelengthBTN.setVisibility(View.GONE);
        wavelengthStart.setVisibility(View.GONE);
        wavelengthEnd.setVisibility(View.GONE);
        settingsBackBTN.setVisibility(View.GONE);

        runBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //process.setVisibility(View.VISIBLE);
                run();
                //process.setVisibility(View.GONE);
            }
        });


        settingsBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                //Make normal buttons gone
                runBTN.setVisibility(View.GONE);
                db_BTN.setVisibility(View.GONE);
                shutdownBTN.setVisibility(View.GONE);
                backBTN.setVisibility(View.GONE);
                settingsBTN.setVisibility(View.GONE);

                integrationBTN.setVisibility(View.VISIBLE);
                integrationTime.setVisibility(View.VISIBLE);
                wavelengthBTN.setVisibility(View.VISIBLE);
                wavelengthStart.setVisibility(View.VISIBLE);
                wavelengthEnd.setVisibility(View.VISIBLE);
                settingsBackBTN.setVisibility(View.VISIBLE);

            }

        });
        settingsBackBTN.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //Make normal buttons gone
                runBTN.setVisibility(View.VISIBLE);
                db_BTN.setVisibility(View.VISIBLE);
                shutdownBTN.setVisibility(View.VISIBLE);
                backBTN.setVisibility(View.VISIBLE);
                settingsBTN.setVisibility(View.VISIBLE);

                integrationBTN.setVisibility(View.GONE);
                integrationTime.setVisibility(View.GONE);
                wavelengthBTN.setVisibility(View.GONE);
                wavelengthStart.setVisibility(View.GONE);
                wavelengthEnd.setVisibility(View.GONE);
                settingsBackBTN.setVisibility(View.GONE);
            }
        });

        integrationBTN.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //Run integration communication here
                String integration = integrationTime.getText().toString();
                if(!"".equals(integration)) {
                    setIntegrationTime(integration);
                } else {

                }
            }
        });

        wavelengthBTN.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //Run partial pixel command here
                //String start = wavelengthStart.getText().toString();
                //String end = wavelengthEnd.getText().toString();
                spark();
            }
        });

/*        integrationTime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                integrationTime.setText("");
                //integrationTime.se
            }
        });*/
    }

    public void run() {
        response = "";

        this.response = this.mOut.scan();

        if(response.length() > 18000){

            data = new Data(response, System.currentTimeMillis());
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

    public void setIntegrationTime(String time){

        if(!time.equals("")) {
            //int intTime = Integer.parseInt(time);
            this.acknowledged = this.mOut.integrationTime(Integer.parseInt(time));
            if (this.acknowledged) {
                Toast toast = Toast.makeText(this.getApplicationContext(), "Integration time set to: " + time, Toast.LENGTH_LONG);
                toast.show();
            } else {
                Toast toast = Toast.makeText(this.getApplicationContext(), "Integration time not set.", Toast.LENGTH_LONG);
                toast.show();
            }
        } else {
            Toast toast = Toast.makeText(this.getApplicationContext(), "Please enter a valid integer.", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    public void spark(){
        this.acknowledged = this.mOut.partialPixelMode(0,1000);
        if(this.acknowledged){
            Toast toast = Toast.makeText(this.getApplicationContext(), "Tased", Toast.LENGTH_LONG);
            toast.show();
        } else {
            Toast toast = Toast.makeText(this.getApplicationContext(), "Not Tased", Toast.LENGTH_LONG);
            toast.show();
        }
    }


    public void goDB(View view){
        Intent intent = new Intent(this, DisplayDBActivity.class);
        startActivity(intent);
    }

    public void shutdown(View view){
        this.mOut.shutdown();
    }

    public void goBack(View view){
        try{
            if(mConnected != null) {
                mConnected.close();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
        } catch (IOException e ){
            Log.e("Connection close", "Connection could not close for some reason." + e.toString());
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

    }
}
