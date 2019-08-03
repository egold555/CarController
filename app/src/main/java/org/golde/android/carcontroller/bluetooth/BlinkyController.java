package org.golde.android.carcontroller.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.os.Handler;

import org.golde.android.carcontroller.MainActivity;

import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.NonNull;

public class BlinkyController implements BlinkyManagerCallbacks {

    Timer keepaliveTimer;
    TimerTask keepaliveTimerTask;
    //we are going to use a handler to be able to run in our TimerTask
    final Handler handler = new Handler();

    private BlinkyManager manager;
    private BluetoothDevice mDevice;
    private MainActivity main;
    private boolean isConnected = false;

    public BlinkyController(MainActivity main){
        this.main = main;
        this.manager = new BlinkyManager((main));
        manager.setGattCallbacks(this);
        startTimer();
    }

    public void onResume() {
        startTimer();
    }

    public void connect(String mac) {
        // Prevent from calling again when called again (screen orientation changed)
        if (mDevice == null) {
            BluetoothAdapter adapter = ((BluetoothManager)this.main.getSystemService(Context.BLUETOOTH_SERVICE)).getAdapter();

            mDevice = adapter.getRemoteDevice(mac);

            reconnect();
        }
    }

    public void startTimer() {
        //set a new Timer
        keepaliveTimer = new Timer();

        //initialize the TimerTask's job
        initializeTimerTask();

        //schedule the timer, after the first 5000ms the TimerTask will run every 10000ms
        keepaliveTimer.schedule(keepaliveTimerTask, 1000, 2000); //
    }

    public void stopTimerTask() {
        //stop the timer, if it's not already null
        if (keepaliveTimer != null) {
            keepaliveTimer.cancel();
            keepaliveTimer = null;
        }
    }

    public void initializeTimerTask() {

        keepaliveTimerTask = new TimerTask() {
            public void run() {

                //use a handler to run a toast that shows the current timestamp
                handler.post(new Runnable() {
                    public void run() {
                        reconnect();
                        sendKeepAlive();
                    }
                });
            }
        };
    }

    public void reconnect() {
        if (mDevice != null) {
            manager.connect(mDevice)
                    .retry(3, 100)
                    .useAutoConnect(false)
                    .enqueue();
        }
    }

    public void disconnect() {
        mDevice = null;
        manager.disconnect().enqueue();
    }

    public void sendColor(int r, int g, int b){
        manager.sendColor(r, g, b);
    }

    public void sendBrightness(int brightness){
        manager.sendBrightness(brightness);
    }

    public void sendKeepAlive(){
        manager.sendKeepAlive();
    }

    @Override
    public void onDeviceConnecting(@NonNull final BluetoothDevice device) {

    }

    @Override
    public void onDeviceConnected(@NonNull final BluetoothDevice device) {
        this.isConnected = true;
        sendKeepAlive();
    }

    @Override
    public void onDeviceDisconnecting(@NonNull final BluetoothDevice device) {

    }

    @Override
    public void onDeviceDisconnected(@NonNull final BluetoothDevice device) {
        this.isConnected = false;
    }

    @Override
    public void onLinkLossOccurred(@NonNull final BluetoothDevice device) {
        this.isConnected = false;
    }

    @Override
    public void onServicesDiscovered(@NonNull final BluetoothDevice device, final boolean optionalServicesFound) {

    }

    @Override
    public void onDeviceReady(@NonNull final BluetoothDevice device) {

    }

    @Override
    public void onBondingRequired(@NonNull final BluetoothDevice device) {

    }

    @Override
    public void onBonded(@NonNull final BluetoothDevice device) {

    }

    @Override
    public void onBondingFailed(@NonNull final BluetoothDevice device) {

    }

    @Override
    public void onError(@NonNull final BluetoothDevice device, @NonNull final String message, final int errorCode) {

    }

    @Override
    public void onDeviceNotSupported(@NonNull final BluetoothDevice device) {

    }

}
