package org.golde.android.carcontroller.ui;

import android.graphics.Color;

public class BetterColor {

    public final int r, g, b, brightness;
    public final int rUI, gUI, bUI, colorUI;
    public final String hexUI;

    public BetterColor(int r, int g, int b, int brightness){
        this.r = r;
        this.g = g;
        this.b = b;
        this.brightness = brightness;

        float a = (this.brightness / 255f);
        this.rUI = (int)(this.r * a);
        this.gUI = (int)(this.g * a);
        this.bUI = (int)(this.b * a);

        this.colorUI = Color.rgb(rUI, gUI, bUI);

        this.hexUI = "#" + Integer.toHexString(this.colorUI);
    }
}
