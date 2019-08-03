package org.golde.android.carcontroller;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class ColorPicker {

    private final ColorPickerCallback callback;

    ImageView mImageView;
    TextView mTextResult;
    View mColorView;

    Bitmap bitmap;

    SeekBar alphaSlider;

    private int or = 255, og = 255, ob = 255;
    private float a = 1;

    public ColorPicker(MainActivity main, ColorPickerCallback callback){
        this.callback = callback;

        mImageView = main.findViewById(R.id.imageView);
        mTextResult = main.findViewById(R.id.resultTv);
        mColorView = main.findViewById(R.id.colorView);
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

                    or = Color.red(pixel);
                    og = Color.green(pixel);
                    ob = Color.blue(pixel);

                    calcColor();

                }

                return true;
            }
        });

        alphaSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                a = (progress /255F); //0 - 100 > 0 - 1

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

        int r = (int)(or * a);
        int g = (int)(og * a);
        int b = (int)(ob * a);

        int pickedColor = Color.rgb(r, g, b);

        String hex = "#" + Integer.toHexString(pickedColor);

        mColorView.setBackgroundColor(pickedColor);

        mTextResult.setText("RGB: " + r + ", " + g + ", " + b + "\nHEX: " + hex);

        callback.onColorChange(or, og, ob, (int)(a * 255), r, g, b);
    }

    public interface ColorPickerCallback {
        public void onColorChange(int rRaw, int gRaw, int bRaw, int brightness, int rMod, int gMod, int bMod);
    }

}
