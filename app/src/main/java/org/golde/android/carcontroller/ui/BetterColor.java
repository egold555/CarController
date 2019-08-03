package org.golde.android.carcontroller.ui;

import android.graphics.Color;

public class BetterColor {

    public int r = 0, g = 0, b = 0, brightness = 255;
    public int rUI, gUI, bUI, colorUI;
    public String hexUI;

    public BetterColor(String hex){
        this(
                Integer.valueOf(hex.substring(1, 3 ), 16),
                Integer.valueOf( hex.substring(3, 5), 16),
                Integer.valueOf( hex.substring(5, 7), 16),
                255
        );
    }

    public BetterColor(int r, int g, int b){
        this(r, g, b, 255);
    }

    public BetterColor(int r, int g, int b, int brightness){
        this.r = r;
        this.g = g;
        this.b = b;
        this.brightness = brightness;
        update();
    }

    public BetterColor setRGB(BetterColor in){
        this.r = in.r;
        this.g = in.g;
        this.b = in.b;
        update();
        return this;
    }

//    public void setR(int in){
//        this.r = in;
//        update();
//    }
//
//    public void setG(int in){
//        this.g = in;
//        update();
//    }
//
//    public void setB(int in){
//        this.b = in;
//        update();
//    }

    public BetterColor setBrightness(int in){
        this.brightness = in;
        update();
        return this;
    }

    public void update(){

        float a = (this.brightness / 255f);
        this.rUI = (int)(this.r * a);
        this.gUI = (int)(this.g * a);
        this.bUI = (int)(this.b * a);

        this.colorUI = Color.rgb(rUI, gUI, bUI);

        this.hexUI = "#" + Integer.toHexString(this.colorUI);
    }
}
