package com.example.zakk.seniordesignmay1719;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    BluetoothAdapter mBluetoothAdapter;
    BluetoothLeScanner testScanner;
    int REQUEST_ENABLE_BT = 1;
    Set<BluetoothDevice> pairedDevices;
    List<String> listViewData = new ArrayList<String>();
    List<BluetoothDevice> deviceList = new ArrayList<>();
    ListView lv;
    ArrayAdapter<String> arrAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = (ListView)findViewById(R.id.bluetoohLV);
        arrAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listViewData);
        lv.setAdapter(arrAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position,
                                    long arg3)
            {
                String value = (String)adapter.getItemAtPosition(position);
                Log.e("LIST", "Pos in List: " + position);
                listItemClick(deviceList.get(position));
            }
        });

        int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 1;
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
               MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION); //testing for android 6.0
    }

    @Override
    protected void onPause(){
        super.onPause();
     //   mBluetoothAdapter.cancelDiscovery();
        try{
            if(mReciever != null){
                unregisterReceiver(mReciever);
            }
        }catch (IllegalArgumentException e){
            Log.e(" ", e.getMessage());
        }
    }

    public static String[] output_to_pixels(String input){
        //input = input.replace("\n", "");
        String[] output_Data = input.split(" ");

        String data_Mode = output_Data[2]; //0-WORDS (16-bit pixel values), 1-DWORDS (32-bit pixel values)
        //String scans = output_Data[3]; //Number of scans taken
        //String integration_Time = output_Data[4]; //Time taken to obtain sample data
        String pixels[] = new String[(output_Data.length-9)/2];
        Integer pixel_Index = 0;

        if(data_Mode.equals("0")){
            for(int i = 8; i < output_Data.length-2; i += 2){
                pixels[pixel_Index] = output_Data[i+1] + output_Data[i];
                pixel_Index++;
            }
        } else {
            for(int i = 8; i < output_Data.length; i+=4){
                pixels[pixel_Index] = output_Data[i+3] + output_Data[i+2] + output_Data[i+1] + output_Data[i];
                pixel_Index++;
            }
        }

        return pixels;
    }


    public void dbget(View view){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, World!");
    }

    public void dbview(View view){
        Intent intent = new Intent(this, DisplayDBActivity.class);
        startActivity(intent);
    }


    private BroadcastReceiver mReciever = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(BluetoothDevice.ACTION_FOUND.equals(action)){
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                arrAdapter.add(device.getName() + "\n" + device.getAddress()); //may need to move this
                deviceList.add(device);
                Log.v(" ", "DEVICE FOUND");
            }
        }
    };

    public void enableBluetooth(View view){
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        arrAdapter.clear();
        if(mBluetoothAdapter == null){
            Log.e(" ", "bluetooth adapter not enabled");
        }
        if(!mBluetoothAdapter.isEnabled()){
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            sendBroadcast(enableBtIntent);
        }

        pairedDevices = mBluetoothAdapter.getBondedDevices();
        if(pairedDevices.size() > 0){
            for(BluetoothDevice device : pairedDevices){
                arrAdapter.add(device.getName() + "/n" + device.getAddress());
                deviceList.add(device);
            }
        }
    }

    public void bluetoothScan(View view){

        if(mBluetoothAdapter.isDiscovering()){
            Log.e(" ", "discovery in progress. and you canceled the in progress one");
            mBluetoothAdapter.cancelDiscovery();
        }

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        this.registerReceiver(mReciever, filter);

        if(mBluetoothAdapter.isDiscovering()){
            mBluetoothAdapter.cancelDiscovery();
        }
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            mBluetoothAdapter.startDiscovery();
        }

        Log.i("BLUETOOTH", String.valueOf(mBluetoothAdapter.getState()));
        Log.i("BLUETOOTH", "Bluetooth Enabled: " + mBluetoothAdapter.isEnabled());
        Log.i("BLUETOOTH", "val: " + mBluetoothAdapter.isDiscovering()); // Return false


    }

    public void listItemClick(BluetoothDevice device){
        mBluetoothAdapter.cancelDiscovery();

        ConnectThread mConnectThread = new ConnectThread(device, false, mBluetoothAdapter);
        try{
            mConnectThread.connect(); //Starts the bluetooth connection thread
        } catch(IOException e){
            Log.e("CONNECT", "Connection error: " + e.getMessage());
        }
        //mConnectThread.run(mBluetoothAdapter);

    }


    //bullshit for now
    public void RUNCLICK(){

        /*
        1: check the connection
            if active
                send scan command ('S')
            else
                error, need to connect to bluetooth device (popup box?)

        2: read from socket

            check the header,
            if formatted S 65535 ....
                read until 65533
                stop read
                run output_to_pixels
                send output_to_pixels, along with other data, to the DB

         */


    }


    public void endDiscovery(View view){
        mBluetoothAdapter.cancelDiscovery();
    }
}

