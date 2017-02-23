package com.example.zakk.seniordesignmay1719;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;

public class btConnectedActivity extends AppCompatActivity {

    public BluetoothAdapter mBluetoothAdapter;
    public BluetoothSocketWrapper mConnected;
    public BluetoothDevice device;
    public ConnectedThread mOut;
    public String response;
    public Data[] datas = new Data[10];
    public int data_Index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bt_connected);

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
        }


    }

    public void run(View view){
        response = this.mOut.scan();
        Log.i("Data", response);
        if(response.length() > 100){
            Toast toast = Toast.makeText(this.getApplicationContext(), "Scan Data added to DB", Toast.LENGTH_LONG);
            toast.show();
        }
        //Create data
        //datas[data_Index] = new Data(response, System.currentTimeMillis());
        //Figure out how to push the data into the database, for now...
        //data_Index++;
    }

    public void goDB(View view){
        Intent intent = new Intent(this, DisplayDBActivity.class);
        startActivity(intent);
    }

    public void goBack(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
