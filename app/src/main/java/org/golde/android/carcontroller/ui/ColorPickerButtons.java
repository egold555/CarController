package org.golde.android.carcontroller.ui;

import android.view.View;
import android.widget.Button;

import org.golde.android.carcontroller.MainActivity;
import org.golde.android.carcontroller.R;

public class ColorPickerButtons {

    private ColorChangeCallback callback;

    public ColorPickerButtons(MainActivity main, ColorChangeCallback callback){
        this.callback = callback;
        setupButton(main, R.id.colorButton1, 0xFFFF0000); //Red
        setupButton(main, R.id.colorButton1, 0xFFFA0000); //Orange
        setupButton(main, R.id.colorButton1, 0xFFFFF000); //Yellow
        setupButton(main, R.id.colorButton1, 0xFF00FF00); //Green
        setupButton(main, R.id.colorButton1, 0xFF00FFFF); //Light Blue
        setupButton(main, R.id.colorButton1, 0xFF0000FF); //Blue
        setupButton(main, R.id.colorButton1, 0xFFFF00FF); //Purple
        setupButton(main, R.id.colorButton1, 0xFFFFFFFF); //White

    }

    private void setupButton(MainActivity main, int id, int color){
        Button button = main.findViewById(id);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                callback.onColorChange(new BetterColor(color));
            }
        });
    }
}
