package com.example.zakk.seniordesignmay1719;

import android.bluetooth.BluetoothSocket;
import android.widget.Toast;

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
    public Integer dataAvailable;
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

    public String scan() {
        byte[] sendComm = "S".getBytes();
        byte[] recvData;
        String recv = "";
        try {
            if (this.mmSocket.getUnderlyingSocket().isConnected()) {
                outStream.write(sendComm);

                long dif = 0;
                long baseTime = System.currentTimeMillis();
                while (inStream.available() == 0 && dif <= 12000) {
                    dif = System.currentTimeMillis() - baseTime;
                }

                if (dif > 12000) {
                    Log.i("Scan_time", "Scan took to long to complete, over 10 seconds");
                    //Make toast
                    //Toast toast = Toast.makeText(getApplicationContext(), "No Data was returned", Toast.LENGTH_LONG);
                    //toast.show();
                    dataAvailable = inStream.available();
                    if (dataAvailable == 0) {
                        Log.i("READ", "No data was sent back.");
                        //break; do something else here
                    } else {
                        recvData = new byte[inStream.available()];
                        inStream.read(recvData);
                        recv = new String(recvData);
                        Log.i("Initial", "Data: " + recv);
                    }

                } else {
                    dataAvailable = inStream.available();
                    if (dataAvailable == 0) {
                        Log.i("READ", "No data was sent back.");
                        //break; do something else here
                    } else {
                        recvData = new byte[inStream.available()];
                        inStream.read(recvData);

                        recv = new String(recvData);

                    }

                }


                Log.i("Connection", "Is this still connected? " + this.mmSocket.getUnderlyingSocket().isConnected());
            }

        } catch (IOException e) {
            Log.e("OUT", "Could not write out: " + e.getMessage());
        }
        return recv;
    }
}
