package com.boylab.multimodule.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.boylab.multimodule.R;
import com.boylab.smartspinner.OnSpinnerItemListener;
import com.boylab.smartspinner.SmartSpinner;

import java.util.Arrays;
import java.util.List;

public class ParaView extends RelativeLayout implements View.OnClickListener, OnSpinnerItemListener {

    private TextView text_Label;
    private Button btn_Value;
    private SmartSpinner spinner_Value;

    private OnParaListener onParaListener;
    private boolean isSelect = false;

    public ParaView(Context context) {
        this(context, null);
    }

    public ParaView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ParaView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }
    private void initView(Context context) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.layout_para_view, this);

        text_Label = rootView.findViewById(R.id.text_Label);
        btn_Value = rootView.findViewById(R.id.btn_Value);
        spinner_Value = rootView.findViewById(R.id.spinner_Value);
        setSelect(false);

        btn_Value.setOnClickListener(this);
        spinner_Value.setOnSpinnerItemListener(this);
    }

    public void setOnParaListener(OnParaListener onParaListener) {
        this.onParaListener = onParaListener;
    }

    public void setLabel(String label){
        text_Label.setText(label);
    }

    public boolean isSelect() {
        return isSelect;
    }

    private void setSelect(boolean select) {
        isSelect = select;
        btn_Value.setVisibility(isSelect ? GONE : VISIBLE);
        spinner_Value.setVisibility(isSelect ? VISIBLE : GONE);
    }

    public void setSelect(String... values){
        setSelect(true);
        List<String> dataList = Arrays.asList(values);
        //List<String> dataset = new LinkedList<>();
        spinner_Value.attachDataSource(dataList);
    }

    public void setValue(int value){
        if (isSelect()){
            spinner_Value.setSelection(value);
        }else {
            btn_Value.setText(String.valueOf(value));
        }
    }

    public void setValue(String value){
        if (isSelect()){
            //spinner_Value.setSelection(value);
        }else {
            btn_Value.setText(value);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_Value){
            if (onParaListener != null){
                onParaListener.onParaClick(this);
            }
        }
    }

    @Override
    public void onItemClick(SmartSpinner smartSpinner, View view, int position, long l) {
        if (smartSpinner.getId() == R.id.spinner_Value){
            if (onParaListener != null){
                onParaListener.onParaSelect(this, position);
            }
        }
    }

    public interface OnParaListener{
        void onParaClick(ParaView v);
        void onParaSelect(ParaView v, int itemPosition);
    }
}
