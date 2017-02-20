package com.example.zakk.seniordesignmay1719;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * Created by Zakk on 11/9/2016.
 */

public class ConnectThread extends Thread {
    public BluetoothSocket mmSocket;
    public BluetoothDevice mmDevice;
    //Default UUID leave as is
    private static final UUID MY_UUID = UUID.fromString("00001800-0000-1000-8000-00805f9b34fb");
    public ConnectThread(BluetoothDevice device) {
        //BluetoothSocket tmp = null;
        mmDevice = device;
        mmSocket = null;
        BluetoothSocket tmp = null;

        try {
            Log.i("BT", "Device Name: " + mmDevice.getName());
            //Log.i("BT: ", "num" + mmDevice.getUuids().length);
            //Log.i("BT:", "UUIDs" + mmDevice.getUuids()[0].toString());
            //Log.i("BT:", "UUIDs" + mmDevice.getUuids()[1].toString());
            //Log.i("BT:", "UUIDs" + mmDevice.getUuids()[2].toString());
            Method m = mmDevice.getClass().getMethod("createInsecureRfcommSocket", new Class[]{int.class});
            tmp = (BluetoothSocket)m.invoke(device, 1);
            //tmp = mmDevice.createRfcommSocketToServiceRecord(MY_UUID);
            Log.i("BT", "Tmp sucess");
            //tmp = mmDevice.createRfcommSocketToServiceRecord(MY_UUID);
        } catch (Exception e) {
            Log.i("BT", "Creation Error");
        }
        mmSocket = tmp;

    }
    public void run() {

        //Log.d("BT", " UUID from device is null, Using Default UUID, Device name: " + mmDevice.getName());
        try {
            mmSocket.connect();
        } catch (IOException connectException) {
            Log.e("BT", "Other Catch");
            try {
                mmSocket.close();
            } catch (IOException closeException) {
                Log.e("BT", "Could not close the client socket", closeException);
            }
            return;
        }
        Log.e("BT:", "Connect attempted succeded");
    }
            //Log.e("Thread", "BLUETOOTH soccket running");
            //mBluetoothAdapter.cancelDiscovery();
    //Closes the socket, connection, and thread
    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) { }
    }

    public BluetoothDevice getDevice(){
        return mmDevice;
    }
    public BluetoothSocket getMmSocket() {
        return mmSocket;
    }

}
