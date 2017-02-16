package com.example.zakk.seniordesignmay1719;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.IOException;

public class btConnectedActivity extends AppCompatActivity {

    public BluetoothAdapter mBluetoothAdapter;
    public BluetoothSocketWrapper mConnected;
    public BluetoothDevice device;
    public ConnectedThread mOut;
    public String response;

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
        } catch(IOException e){
            Log.e("CONNECT", "Connection error: " + e.getMessage());
        }

        this.mOut = new ConnectedThread(mConnected); //Starts the bluetooth connection thread
    }

    public void run(View view){
        response = this.mOut.scan();
        //Create data
        Data entry = new Data(response, System.currentTimeMillis());
    }

    public void goDB(View view){
        Intent intent = new Intent(this, DisplayDBActivity.class);
        startActivity(intent);
    }

}
