package org.golde.android.carcontroller.ui;

import android.view.View;
import android.widget.TextView;

import org.golde.android.carcontroller.MainActivity;
import org.golde.android.carcontroller.R;

public class ColorViewer {

    TextView mTextResult;
    View mColorView;

    public ColorViewer(MainActivity main){
        mTextResult = main.findViewById(R.id.resultTv);
        mColorView = main.findViewById(R.id.colorView);
    }

    public void updateUI(BetterColor color){
        mColorView.setBackgroundColor(color.colorUI);
        mTextResult.setText("RGB: " + color.rUI + ", " + color.gUI + ", " + color.bUI + "\nHEX: " + color.hexUI);
    }

}
