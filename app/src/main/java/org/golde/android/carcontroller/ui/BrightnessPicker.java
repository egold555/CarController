package org.golde.android.carcontroller.ui;

import android.widget.SeekBar;

import org.golde.android.carcontroller.MainActivity;
import org.golde.android.carcontroller.R;

public class BrightnessPicker {

    SeekBar alphaSlider;

    public BrightnessPicker(MainActivity main, BrightnessCallback callback){
        alphaSlider = main.findViewById(R.id.seekBar);

        alphaSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                callback.onBrightnessChange(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public interface BrightnessCallback {
        public void onBrightnessChange(int brightness);
    }

}
