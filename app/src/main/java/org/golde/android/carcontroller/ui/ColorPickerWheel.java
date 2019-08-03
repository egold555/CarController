package org.golde.android.carcontroller.ui;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import org.golde.android.carcontroller.MainActivity;
import org.golde.android.carcontroller.R;

public class ColorPickerWheel {

    private final ColorChangeCallback callback;

    ImageView mImageView;


    Bitmap bitmap;



    private int r = 255, g = 255, b = 255;

    public ColorPickerWheel(MainActivity main, ColorChangeCallback callback){
        this.callback = callback;

        mImageView = main.findViewById(R.id.imageView);



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



    }

    void calcColor(){
        callback.onColorChange(new BetterColor(r, g, b));
    }

}
