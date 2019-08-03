package org.golde.android.carcontroller.ui;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;

import org.golde.android.carcontroller.MainActivity;
import org.golde.android.carcontroller.R;

public class ColorPicker {

    private final ColorPickerCallback callback;

    ImageView mImageView;


    Bitmap bitmap;

    SeekBar alphaSlider;

    private int r = 255, g = 255, b = 255, brightness = 55;

    public ColorPicker(MainActivity main, ColorPickerCallback callback){
        this.callback = callback;

        mImageView = main.findViewById(R.id.imageView);

        alphaSlider = main.findViewById(R.id.seekBar);

        //Not exactly sure what this does but I needed it
        mImageView.setDrawingCacheEnabled(true);
        mImageView.buildDrawingCache(true);

        calcColor();

        //On touch of the image. Seems to be global though for on touch whereever on the screen
        mImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN || motionEvent.getAction() == MotionEvent.ACTION_MOVE){
                    bitmap = mImageView.getDrawingCache();

                    int px = (int) motionEvent.getX();
                    int py = (int) motionEvent.getY();

                    //Fixes crashing when touching places not on the image. See comment about the setOnTouchListener
                    if(py >= bitmap.getHeight() || px >= bitmap.getWidth()){
                        return true;
                    }

                    int pixel = bitmap.getPixel(px, py);

                    //Black when clicking any transparent part of the image. Fixes that
                    if(Color.alpha(pixel) != 255){
                        return true;
                    }

                    r = Color.red(pixel);
                    g = Color.green(pixel);
                    b = Color.blue(pixel);

                    calcColor();

                }

                return true;
            }
        });

        alphaSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                brightness = progress;

                calcColor();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    void calcColor(){
        callback.onColorChange(new BetterColor(r, g, b, brightness));
    }

    public interface ColorPickerCallback {
        public void onColorChange(BetterColor color);
    }

}
