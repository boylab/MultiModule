package com.boylab.multimodule.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;

import com.boylab.multimodule.R;

public class SignView extends RelativeLayout {

    private CheckBox box_Stable, box_Tare, box_Zero;

    public SignView(Context context) {
        this(context, null);
    }

    public SignView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SignView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }
    private void initView(Context context) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.layout_sign_view, this);

        box_Stable = rootView.findViewById(R.id.box_Stable);
        box_Tare = rootView.findViewById(R.id.box_Tare);
        box_Zero = rootView.findViewById(R.id.box_Zero);
    }

    public void setTextSize(float size){
        box_Stable.setTextSize(size);
        box_Tare.setTextSize(size);
        box_Zero.setTextSize(size);
    }

    public void setSign(boolean isStable, boolean isTare, boolean isZero ){
        box_Stable.setChecked(isStable);
        box_Tare.setChecked(isTare);
        box_Zero.setChecked(isZero);
    }

}
