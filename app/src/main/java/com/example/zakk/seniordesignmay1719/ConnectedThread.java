package com.example.zakk.seniordesignmay1719;

import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Zakk on 11/10/2016.
 */

public class ConnectedThread extends Thread {
    public BluetoothSocket mmSocket;
    public InputStream inStream;
    public OutputStream outStream;

    public ConnectedThread(BluetoothSocket socket){
        mmSocket = socket;
        try {
            InputStream in = socket.getInputStream();
            inStream = in;
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            OutputStream out = socket.getOutputStream();
            outStream = out;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run(){

    }
}
