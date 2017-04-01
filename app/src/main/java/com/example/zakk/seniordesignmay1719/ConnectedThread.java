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
    private android.util.Log Log;

    /**
     * Creates the ConnectedThread, takes in a BluetoothSocketWrapper.
     * From the socket, it sets this threads input and output streams to be associated with
     * the bluetooth connection.
     * @param socket - bluetooth connection socket with the raspberry pi.
     */
    public ConnectedThread(BluetoothSocketWrapper socket){
        mmSocket = socket;
        InputStream in = null;
        OutputStream out = null;

        try {
            in = socket.getInputStream();
            this.inStream = in;

        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out = socket.getOutputStream();
            this.outStream = out;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Function to send the scan command to the spectrometer.
     * Acquires the pixel data sent back from the spectrometer.
     * @return - recv - the string representation of the pixel data
     */
    public String scan() {

        byte[] sendComm = "S".getBytes();

        String recv = "";

        //Call communication method
        recv = communication(sendComm, 12000);

        if(recv.equals(null)){
            Log.i("Scan","Scan did not perform properly");
            return recv;
        } else {
            return recv;
        }
    }

    /**
     * Sends the shutdown command to the device. Enables safer shutdowns than simply removing the power.
     */
    public void shutdown(){
        byte[] sendComm = "kill".getBytes();
        try {
            this.outStream.write(sendComm);
        } catch (IOException e){
            Log.e("Shutdown_Error", e.getMessage());
        }
    }

    /**
     * Sets the integration time of the scan on the spectrometer.
     * Essentially like changing the shutter time on a camera.
     * The shorter the integration time, the less light the spectrometer takes in.
     * @param time - new integration time in milliseconds.
     * @return boolean - wheter the command was successful or not
     */
    public boolean integrationTime(short time){
        //Send I{time}
        if(time > 0 && time < 65001) {
            String command = "I" + time;

            byte[] sendComm = command.getBytes();
            String recv = "";

            recv = communication(sendComm, 1000);
            if (recv.equals("ACK")) {
                return true;
            } else if (recv.equals("NAK")) {
                return false;
            } else {
                return false;
            }
        } else {
            Log.e("Integration Time", "Integration time set to outside of boundaries");
            return false;
        }
    }

    /**
     * Changes the serial communication baudrate between the rasperry pi and the spectrometer.
     * Process for changing buadrate
     * Send K{Data Word}
     * Data word values: 0 - 2400, 1 - 4800, 2 - 9600, 3 - 19200, 4 - 38400, 6 - 115200
     * Example:
     *  Send K6 (communicating at old baudrate)
     *  Spectrometer responds with ACK at old baudrate, or NAK and aborts the process
     *  Controlling program waits longer than 50 milliseconds (This is done on the server code)
     *  Send K6 at new baud rate (115200 in this example)
     * @param baudRate - new baudrate to communicate at
     */
    //TODO needs work on the server side as well, talk to Rupert about modifiying the serial port dynamically
    public void baudRate(short baudRate){
        //Figure out process
    }

    /**
     * Sets the spectrometer into partial pixel mode.
     * This means the spectrometer will only send out a select amount of pixel data between x and y.
     * @param x - Starting pixel to be sent out
     * @param y - Ending pixel to be sent out
     * @return - boolean, true if the command was acknowledged, false otherwise.
     */
    //TODO sending multiple commands
    public boolean partialPixelMode(int x, int y){
        String firstCommand = "P3";
        byte[] sendComm1 = firstCommand.getBytes();
        byte[] sendComm2 = (Integer.toString(x)).getBytes();
        byte[] sendComm3 = (Integer.toString(y)).getBytes();
        byte[] sendComm4 = (Integer.toString(3648)).getBytes();
        //byte[] recvData;
        String received = "";

        return false;
    }

    /**
     * Helper method to perform communication with the raspberry PI
     * @param command - command to be sent to the spe
     * @param sleepTime
     * @return
     */
    private String communication(byte[] command, long sleepTime){
        byte[] recvData;
        String recv = "";

        try {
            if (this.mmSocket.getUnderlyingSocket().isConnected()) {
                this.outStream.write(command);


                //Sleep time depends on the command sent to the spectrometer
                try {
                    Thread.sleep(sleepTime);
                }catch (InterruptedException e){
                    Log.i("Sleep", "Sleep interrupted: " +  e.getMessage());
                }


                Log.e("Stream_", "Available: " + this.inStream.available());
                int dataAvailable = this.inStream.available();
                if (dataAvailable == 0) {
                    Log.i("READ", "No data was sent back.");
                    //break; do something else here
                    recv = null;
                } else {
                    recvData = new byte[this.inStream.available()];
                    this.inStream.read(recvData);
                    recv = new String(recvData);
                }

            }
            //Log.i("Connection", "Is this still connected? " + this.mmSocket.getUnderlyingSocket().isConnected());


        } catch (IOException e) {
            Log.e("OUT", "Could not write out: " + e.getMessage());
        }

        return recv;
    }
}
