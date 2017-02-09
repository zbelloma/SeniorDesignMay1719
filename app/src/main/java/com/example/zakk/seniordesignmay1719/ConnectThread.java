package com.example.zakk.seniordesignmay1719;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by Zakk on 11/9/2016.
 */

public class ConnectThread extends Thread {
    public BluetoothSocket mmSocket;
    public BluetoothDevice mmDevice;
    //Default UUID leave as is
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
    public ConnectThread(BluetoothDevice device) {
        //BluetoothSocket tmp = null;
        mmDevice = device;
        mmSocket = null;
/*        try {
            tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
        } catch (IOException e) { }
        mmSocket = tmp;
*/
    }
    public void run() {
        BluetoothSocket tmp = null;
        //mmDevice = getDevice();
        try {
            Log.i("BT", "Device Name: " + mmDevice.getName());
            Log.i("BT", "Device UUID: " + mmDevice.getUuids()[0].getUuid());
            tmp = mmDevice.createRfcommSocketToServiceRecord(mmDevice.getUuids()[0].getUuid());
            //tmp = mmDevice.createRfcommSocketToServiceRecord(MY_UUID);
        } catch (IOException e) { }
            mmSocket = tmp;

            Log.d("BT", " UUID from device is null, Using Default UUID, Device name: " + mmDevice.getName());
            try {
                tmp = mmDevice.createRfcommSocketToServiceRecord(MY_UUID);
            } catch (IOException e1) {
               e1.printStackTrace();
            }
            //Log.e("Thread", "BLUETOOTH soccket running");
            //mBluetoothAdapter.cancelDiscovery();

        try {
            mmSocket.connect();
        } catch (IOException connectException) {
            try {
                mmSocket.close();
            } catch (IOException closeException) {
                Log.d("BT", "Socket did not connect, and would not close.");
            }
            return;
        }
    }
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
