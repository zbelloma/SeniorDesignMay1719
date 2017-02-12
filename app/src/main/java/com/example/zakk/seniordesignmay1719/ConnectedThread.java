package com.example.zakk.seniordesignmay1719;

import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Zakk on 11/10/2016.
 */

public class ConnectedThread extends Thread {
    public BluetoothSocketWrapper mmSocket;
    public InputStream inStream;
    public OutputStream outStream;
    private android.util.Log Log;

    public ConnectedThread(BluetoothSocketWrapper socket){
        mmSocket = socket;
        InputStream in = null;
        OutputStream out = null;

        try {
            in = socket.getInputStream();
            inStream = in;

        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out = socket.getOutputStream();
            outStream = out;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run(){
        byte[] sendComm = "S".getBytes();
        
        try {
            outStream.write(sendComm);
        }catch (IOException e){
            Log.e("OUT", "Could not write out. " + e.getMessage());
            
        }
    }
}
