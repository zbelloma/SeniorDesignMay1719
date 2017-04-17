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
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AndroidException;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;


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

public class MainActivity extends AppCompatActivity {

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //lv = (ListView)findViewById(R.id.bluetoohLV);
        //arrAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listViewData);
        //lv.setAdapter(arrAdapter);

/*        lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position,
                                    long arg3)
            {
                String value = (String)adapter.getItemAtPosition(position);
                Log.e("LIST", "Pos in List: " + position);
                listItemClick(deviceList.get(position));
            }
        });*/

        int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 1;
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
               MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION); //testing for android 6.0

        connectBTN = (Button)findViewById(R.id.enableButton);
        connectBTN.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                enableBluetooth(v);
            }

        });
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


    private BroadcastReceiver mReciever = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(BluetoothDevice.ACTION_FOUND.equals(action)){
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                //arrAdapter.add(device.getName() + "\n" + device.getAddress()); //may need to move this
                //deviceList.add(device);
                Log.v(" ", "DEVICE FOUND");
            }
        }
    };

    public void enableBluetooth(View view){
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        //arrAdapter.clear();
        if(mBluetoothAdapter == null){
            Log.e(" ", "bluetooth adapter not enabled");
        }
        if(!mBluetoothAdapter.isEnabled()){
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            sendBroadcast(enableBtIntent);
        }

        /*pairedDevices = mBluetoothAdapter.getBondedDevices();
        if(pairedDevices.size() > 0){
            for(BluetoothDevice device : pairedDevices){
                //arrAdapter.add(device.getName() + "/n" + device.getAddress());
                //deviceList.add(device);
            }
        }*/

        Intent btConnected = new Intent(this, btConnectedActivity.class);
        //btConnected.putExtra("connectedSocket", mOut);
        startActivity(btConnected);
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

    public void dbview(View view){
        Intent intent = new Intent(this, DisplayDBActivity.class);
        startActivity(intent);
    }

    public void graphview(View view){
        Intent intent = new Intent(this, GraphViewActivity.class);
        startActivity(intent);
    }

    public void beginTutorial(View view){
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

    }

}

