package org.golde.android.carcontroller;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.golde.android.carcontroller.bluetooth.BlinkyController;
import org.golde.android.carcontroller.bluetooth.BluetoothConstants;

/*
TODO:
    Make ColorPicker.java more pleasing to the eye (With comments)
 */

public class MainActivity extends AppCompatActivity {

     private ColorPicker colorPicker;
     private BlinkyController blinkyController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermission();

        blinkyController = new BlinkyController(this);

        blinkyController.connect(BluetoothConstants.MAC_ADDRESS);

        colorPicker = new ColorPicker(this, new ColorPicker.ColorPickerCallback() {
            @Override
            public void onColorChange(int rRaw, int gRaw, int bRaw, int brightness, int rMod, int gMod, int bMod) {
                blinkyController.sendColor(rRaw, gRaw, bRaw);
                blinkyController.sendBrightness(brightness);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        blinkyController.onResume();
    }

    void checkPermission(){
        // Quick permission check
        int permissionCheck = this.checkSelfPermission("Manifest.permission.ACCESS_COARSE_LOCATION");
        if (permissionCheck != 0) {
            this.requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1001); //Any number
        }
    }
}
