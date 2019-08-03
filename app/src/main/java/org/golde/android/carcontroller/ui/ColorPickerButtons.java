package org.golde.android.carcontroller.ui;

import android.view.View;
import android.widget.Button;

import org.golde.android.carcontroller.MainActivity;
import org.golde.android.carcontroller.R;

public class ColorPickerButtons {

    private ColorChangeCallback callback;

    public ColorPickerButtons(MainActivity main, ColorChangeCallback callback){
        this.callback = callback;
        setupButton(main, R.id.colorButton1, "#FF0000"); //Red
        setupButton(main, R.id.colorButton2, "#FFAA00"); //Orange
        setupButton(main, R.id.colorButton3, "#FFF000"); //Yellow
        setupButton(main, R.id.colorButton4, "#00FF00"); //Green
        setupButton(main, R.id.colorButton5, "#00FFFF"); //Light Blue
        setupButton(main, R.id.colorButton6, "#0000FF"); //Blue
        setupButton(main, R.id.colorButton7, "#FF00FF"); //Purple
        setupButton(main, R.id.colorButton8, "#FFFFFF"); //White

    }

    private void setupButton(MainActivity main, int id, String hex){
        Button button = main.findViewById(id);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                callback.onColorChange(new BetterColor(hex));
            }
        });
    }
}
