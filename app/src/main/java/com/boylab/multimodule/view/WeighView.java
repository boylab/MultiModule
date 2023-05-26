package com.boylab.multimodule.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.boylab.multimodule.R;

public class WeighView extends RelativeLayout {

    private TextView label_Weight, text_Weight;

    public WeighView(Context context) {
        this(context, null);
    }

    public WeighView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WeighView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }
    private void initView(Context context) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.layout_weigh_view, this);

        label_Weight = rootView.findViewById(R.id.label_Weight);
        text_Weight = rootView.findViewById(R.id.text_Weight);
    }

    public void setTextSize(float size){
        label_Weight.setTextSize(size - 3);
        text_Weight.setTextSize(size);
    }

    public void setLabel(String label){
        label_Weight.setText(label);
    }

    public void setText(String weigh){
        text_Weight.setText(weigh);
    }

    public void setBackgroundColor(int color){
        text_Weight.setBackgroundColor(color);
    }

}
