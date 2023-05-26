package com.boylab.multimodule.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.boylab.multimodule.R;

public class D3View extends RelativeLayout {

    private WeighView netWeight, grossWeight, tareWeight;
    private SignView signView;
    private ActionView actionView;

    public D3View(Context context) {
        super(context);
        initView(context);
    }

    public D3View(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public D3View(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.layout_d3_view, this);

        netWeight = rootView.findViewById(R.id.netWeight);
        grossWeight = rootView.findViewById(R.id.grossWeight);
        tareWeight = rootView.findViewById(R.id.tareWeight);
        signView = rootView.findViewById(R.id.signView);
        actionView = rootView.findViewById(R.id.actionView);

        netWeight.setLabel("净重");
        //netWeight.setTextSize(30);
        grossWeight.setLabel("毛重");
        grossWeight.setTextSize(20);
        tareWeight.setLabel("皮重");
        tareWeight.setTextSize(20);
    }

    /**
     * 刷新重量数据
     * @param net
     * @param gross
     * @param tare
     */
    public void setWeight(String net, String gross, String tare){
        netWeight.setText(net);
        grossWeight.setText(gross);
        tareWeight.setText(tare);
    }

    /**
     * 刷新状态
     * @param isStable
     * @param isTare
     * @param isZero
     */
    public void setSign(boolean isStable, boolean isTare, boolean isZero ){
        signView.setSign(isStable, isTare, isZero);
    }

    /**
     *
     * @param onViewCallBack
     */
    public void setOnViewCallBack(OnActionListener onViewCallBack) {
        actionView.setOnViewCallBack(onViewCallBack);
    }

    public interface OnActionListener{
        void onViewAdd();
        void onViewRemove();
        void onViewTare();
        void onViewZero();
    }
}
