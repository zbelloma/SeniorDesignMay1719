package com.example.zakk.seniordesignmay1719;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;

public class btConnectedActivity extends AppCompatActivity {

    public BluetoothAdapter mBluetoothAdapter;
    public BluetoothSocketWrapper mConnected;
    public BluetoothDevice device;
    public ConnectedThread mOut;
    public String response;
    public Data[] datas = new Data[10];
    public int data_Index = 0;
    public Data data;

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
        ProgressDialog dialog = new ProgressDialog(btConnectedActivity.this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Loading. Please wait...");
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        response = this.mOut.scan();
        dialog.dismiss();

        if(response.length() > 100){

            data = new Data(response, System.currentTimeMillis());
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference ref = database.getReference();
            ref.child("data").child(data.id).setValue(data);

            Toast toast = Toast.makeText(this.getApplicationContext(), "Scan Data added to DB", Toast.LENGTH_LONG);
            toast.show();
        } else {
            Toast toast = Toast.makeText(this.getApplicationContext(), "No Data was returned", Toast.LENGTH_LONG);
            toast.show();

        }

        //String[] pixs = data.getPixels();
        Log.i("Dislay", data.getTime() + "\n" + data.numScans + "\n" + data.getIntegrationTime() + "\n");

    }


/*    public void initialize(View view){
        this.mOut().initialize();
        try{
            mConnected.close();
        } catch (IOException e ){
            Log.e("Connection close", "Connection could not close for some reason." + e.toString());
        }

    }*/

    public void goDB(View view){
        Intent intent = new Intent(this, DisplayDBActivity.class);
        startActivity(intent);
    }

    public void goBack(View view){
        try{
            mConnected.close();
        } catch (IOException e ){
            Log.e("Connection close", "Connection could not close for some reason." + e.toString());
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
