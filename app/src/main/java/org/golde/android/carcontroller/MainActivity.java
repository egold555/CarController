package org.golde.android.carcontroller;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.golde.android.carcontroller.bluetooth.BlinkyController;
import org.golde.android.carcontroller.bluetooth.BluetoothConstants;
import org.golde.android.carcontroller.ui.BetterColor;
import org.golde.android.carcontroller.ui.ColorChangeCallback;
import org.golde.android.carcontroller.ui.ColorPickerButtons;
import org.golde.android.carcontroller.ui.ColorPickerWheel;
import org.golde.android.carcontroller.ui.ColorViewer;
import org.golde.android.carcontroller.voicecontrol.NotificationService;

/*
TODO:
    Make ColorPicker.java more pleasing to the eye (With comments)
 */

public class MainActivity extends AppCompatActivity {

     private ColorPickerWheel colorPickerWheel;
     private BlinkyController blinkyController;
     private ColorViewer colorViewer;
    private ColorPickerButtons colorPickerButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermission();

        colorViewer = new ColorViewer(this);

        blinkyController = new BlinkyController(this);

        blinkyController.connect(BluetoothConstants.MAC_ADDRESS);

        colorPickerWheel = new ColorPickerWheel(this, new ColorChangeCallback() {
            @Override
            public void onColorChange(BetterColor color) {
                setColor(color);
            }
        });

        colorPickerButtons = new ColorPickerButtons(this, new ColorChangeCallback() {
            @Override
            public void onColorChange(BetterColor color) {
                setColor(color);
            }
        });

        NotificationService.checkPermission(this);

        NotificationService.setCallback(new ColorChangeCallback() {
            @Override
            public void onColorChange(BetterColor color) {
                setColor(color);
            }
        });


    }

    //TODO: Make these two seperate calls. Doesn't seem to affect performance of device though
    private void setColor(BetterColor color){
        colorViewer.updateUI(color);
        blinkyController.sendColor(color);
        blinkyController.sendBrightness(color);

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
