package com.example.zakk.seniordesignmay1719;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import com.example.zakk.seniordesignmay1719.NativeBluetoothSocket;
import android.util.Log;

public class ConnectThread{

    private BluetoothSocketWrapper mmSocket;
    private BluetoothDevice mmDevice;
    private boolean secure;
    private BluetoothAdapter mAdapter;
    private UUID MY_UUID;


    /**
     * @param device         the device
     * @param secure         if connection should be done via a secure socket
     * @param adapter        the Android BT adapter
     *
     *                       This is the creator for the ConnectThread, used to create a connection
     *                       to the bluetooth dongle.
     */
    public ConnectThread(BluetoothDevice device, boolean secure, BluetoothAdapter adapter) {
        this.mmDevice = device;
        this.secure = secure;
        this.mAdapter = adapter;
        this.MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    }


    /**
     * @exception if there is an exception due to the socket not being able to connect, it is thrown.
     */
    public BluetoothSocketWrapper connect() throws IOException {
        boolean success = false;
        while (selectSocket()) {
            mAdapter.cancelDiscovery();

            try {
                mmSocket.connect();
                success = true;
                Log.i("connection", "Connection complete.");
                break;
            } catch (IOException e) {
                //try the fallback
                try {
                    mmSocket = new FallbackBluetoothSocket(mmSocket.getUnderlyingSocket());
                    Thread.sleep(500);
                    mmSocket.connect();
                    Log.i("connection", "Connection complete.");
                    success = true;
                    break;
                } catch (FallbackException e1) {
                    Log.w("BT", "Could not initialize FallbackBluetoothSocket classes.", e);

                    break;
                } catch (InterruptedException e1) {
                    Log.w("BT", e1.getMessage(), e1);
                    break;
                } catch (IOException e1) {
                    Log.w("BT", "Fallback failed. Cancelling.", e1);
                    break;
                }
            }
        }

        if (!success) {
            throw new IOException("Could not connect to device: " + mmDevice.getAddress());

        }

        return mmSocket;
    }


    /**
     * Allows for the creation of either a secure rfcomm socket, or an insecure one.
     * @return boolean: true.
     * @throws IOException
     */
    private boolean selectSocket() throws IOException {

        BluetoothSocket tmp;

        Log.i("BT", "Attempting to connect to Protocol: " + MY_UUID);
        if (secure) {

            tmp = mmDevice.createRfcommSocketToServiceRecord(MY_UUID);
        } else {
            tmp = mmDevice.createInsecureRfcommSocketToServiceRecord(MY_UUID);
        }
        mmSocket = new NativeBluetoothSocket(tmp);

        return true;
    }


    public class FallbackBluetoothSocket extends NativeBluetoothSocket {

        private BluetoothSocket fallbackSocket;

        public FallbackBluetoothSocket(BluetoothSocket tmp) throws FallbackException {
            super(tmp);
            try {
                Class<?> clazz = tmp.getRemoteDevice().getClass();
                Class<?>[] paramTypes = new Class<?>[]{Integer.TYPE};
                Method m = clazz.getMethod("createRfcommSocket", paramTypes);
                Object[] params = new Object[]{Integer.valueOf(1)};
                fallbackSocket = (BluetoothSocket) m.invoke(tmp.getRemoteDevice(), params);
            } catch (Exception e) {
                throw new FallbackException(e);
            }
        }

        @Override
        public InputStream getInputStream() throws IOException {
            return fallbackSocket.getInputStream();
        }

        @Override
        public OutputStream getOutputStream() throws IOException {
            return fallbackSocket.getOutputStream();
        }


        @Override
        public void connect() throws IOException {
            fallbackSocket.connect();
        }


        @Override
        public void close() throws IOException {
            fallbackSocket.close();
        }

    }

    public static class FallbackException extends Exception {

        /**
         *
         */
        private static final long serialVersionUID = 1L;

        public FallbackException(Exception e) {
            super(e);
        }

    }
}