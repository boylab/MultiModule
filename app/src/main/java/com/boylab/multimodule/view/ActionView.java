package com.boylab.multimodule.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.boylab.multimodule.R;
import com.boylab.multimodule.util.ViewClick;

public class ActionView extends RelativeLayout implements View.OnClickListener {

    private Button btn_Start, btn_Stop, btn_Tare, btn_Zero, btn_CalibPara, btn_SysSet;
    private D3View.OnActionListener onViewCallBack;
    private boolean isStart = false;

    public ActionView(Context context) {
        this(context, null);
    }

    public ActionView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ActionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }
    private void initView(Context context) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.layout_action_view, this);

        btn_Start = rootView.findViewById(R.id.btn_Start);
        btn_Stop = rootView.findViewById(R.id.btn_Stop);
        btn_Tare = rootView.findViewById(R.id.btn_Tare);
        btn_Zero = rootView.findViewById(R.id.btn_Zero);
        btn_CalibPara = rootView.findViewById(R.id.btn_CalibPara);
        btn_SysSet = rootView.findViewById(R.id.btn_SysSet);

        btn_Start.setOnClickListener(this);
        btn_Stop.setOnClickListener(this);
        btn_Tare.setOnClickListener(this);
        btn_Zero.setOnClickListener(this);
        btn_CalibPara.setOnClickListener(this);
        btn_SysSet.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (ViewClick.isFastClick()){
            return;
        }
        if (view.getId() == R.id.btn_Start){
            if (onViewCallBack != null){
                setStart(true);
                onViewCallBack.onD3Start();
            }
        }else if (view.getId() == R.id.btn_Stop){
            if (onViewCallBack != null){
                setStart(false);
                onViewCallBack.onD3Stop();
            }
        }else if (view.getId() == R.id.btn_Tare){
            if (onViewCallBack != null){
                onViewCallBack.onD3Tare();
            }
        }else if (view.getId() == R.id.btn_Zero){
            if (onViewCallBack != null){
                onViewCallBack.onD3Zero();
            }
        }else if (view.getId() == R.id.btn_CalibPara){
            if (onViewCallBack != null){
                onViewCallBack.onD3Calib();
            }
        }else if (view.getId() == R.id.btn_SysSet){
            if (onViewCallBack != null){
                onViewCallBack.onD3SysSet();
            }
        }
    }

    public void setStart(boolean start) {
        isStart = start;
        btn_Start.setTextColor(isStart ? Color.RED : Color.BLACK);
    }

    public void setOnViewCallBack(D3View.OnActionListener onViewCallBack) {
        this.onViewCallBack = onViewCallBack;
    }

}
