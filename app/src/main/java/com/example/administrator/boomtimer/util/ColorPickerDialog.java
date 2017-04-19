package com.example.administrator.boomtimer.util;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.example.administrator.boomtimer.R;
import com.larswerkman.holocolorpicker.ColorPicker;
import com.larswerkman.holocolorpicker.OpacityBar;
import com.larswerkman.holocolorpicker.SVBar;
import com.larswerkman.holocolorpicker.SaturationBar;
import com.larswerkman.holocolorpicker.ValueBar;

import static com.example.administrator.boomtimer.R.id.opacitybar;
import static com.example.administrator.boomtimer.R.id.valuebar;


/**
 * Created by shady on 2017/1/15.
 */

public class ColorPickerDialog extends Dialog {
    private static final String TAG = "ColorPickerDialog";
    Context mContext;
    SVBar svBar;
    OpacityBar opacityBar;
    SaturationBar saturationBar;
    ValueBar valueBar;
    Button ok, cancel;
    ColorPicker picker;
    private static ColorPickerDialog colorPickerDialog;

    public ColorPickerDialog(Context context) {
        super(context);
        mContext = context;
    }

    public ColorPickerDialog(Context context, int theme) {
        super(context, theme);
        mContext = context;
    }

    public synchronized static ColorPickerDialog getInstance(Context context) {
        if (colorPickerDialog == null) {
            colorPickerDialog = new ColorPickerDialog(context);
        }
        return colorPickerDialog;
    }

    // 利用interface来构造一个回调函数
    public interface ICustomDialogEventListener {
        public void customDialogEvent(int color);
    }

    private ICustomDialogEventListener onCustomDialogEventListener;

    // 在构造函数中，设置进去回调函数
    public void setListener(Context context,
                            ICustomDialogEventListener onCustomDialogEventListener) {
        mContext = context;
        this.onCustomDialogEventListener = onCustomDialogEventListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.dialog_colorpicker, null);
        this.setContentView(layout);
        picker = (ColorPicker) layout.findViewById(R.id.picker);
        svBar = (SVBar) findViewById(R.id.svbar);
        opacityBar = (OpacityBar) findViewById(opacitybar);
        saturationBar = (SaturationBar) findViewById(R.id.saturationbar);
        valueBar = (ValueBar) findViewById(valuebar);
        ok = (Button) findViewById(R.id.ok);
        cancel = (Button) findViewById(R.id.cancel);

        picker.addSVBar(svBar);
        picker.addOpacityBar(opacityBar);
        picker.addSaturationBar(saturationBar);
        picker.addValueBar(valueBar);

        //to turn of showing the old color
        picker.setShowOldCenterColor(true);
        picker.setOldCenterColor(picker.getColor());

        ok.setBackgroundColor(picker.getColor());
        cancel.setBackgroundColor(picker.getOldCenterColor());

        // adds listener to the colorpicker which is implemented
        //in the activity
        picker.setOnColorChangedListener(new ColorPicker.OnColorChangedListener() {
            @Override
            public void onColorChanged(int color) {
                ok.setBackgroundColor(picker.getColor());
                cancel.setBackgroundColor(picker.getOldCenterColor());
                Log.i(TAG, "onColorChanged" + picker.getColor() + picker.getOldCenterColor());
            }
        });


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCustomDialogEventListener.customDialogEvent(picker.getColor());
                //To set the old selected color u can do it like this
                picker.setOldCenterColor(picker.getColor());
                Log.i(TAG, "setOldColor" + picker.getColor());
                dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
}