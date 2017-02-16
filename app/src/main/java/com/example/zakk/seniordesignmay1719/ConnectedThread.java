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

    public String scan(){
        byte[] sendComm = "S".getBytes();
        byte[] recvData = new byte[2048];
        String recv = "";
        try {
            while(this.mmSocket.getUnderlyingSocket().isConnected()){
                outStream.write(sendComm);
                try {
                    Thread.sleep(100);
                } catch(InterruptedException time){
                    Log.e("Sleep", "Sleep was interrupeted? : " + time.getMessage());
                }
                dataAvailable = inStream.available();
                if (dataAvailable == 0){
                    Log.i("READ", "No data was sent back.");
                    break;
                } else {

                    inStream.read(recvData);
                    recv = new String(recvData);
                    Log.i("Read", "This is the received data: " + recv);
                }
            }
            //outStream.write(sendComm);
            Log.i("Connection", "Is this still connected? " + this.mmSocket.getUnderlyingSocket().isConnected());
        }catch (IOException e){
            Log.e("OUT", "Could not write out. " + e.getMessage());
            
        }
        return recv;
    }
}
