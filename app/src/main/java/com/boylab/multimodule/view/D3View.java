package com.boylab.multimodule.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.boylab.multimodule.R;
import com.boylab.multimodule.util.ViewClick;

public class D3View extends RelativeLayout implements View.OnClickListener {

    private TextView text_Weight;
    private CheckBox box_Tare,box_Stable,box_Zero;
    private Button btn_Tare, btn_Zero, btn_Edit;

    private OnViewCallBack onViewCallBack;

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

        text_Weight = rootView.findViewById(R.id.text_Weight);
        box_Tare = rootView.findViewById(R.id.box_Tare);
        box_Stable = rootView.findViewById(R.id.box_Stable);
        box_Zero = rootView.findViewById(R.id.box_Zero);
        btn_Tare = rootView.findViewById(R.id.btn_Tare);
        btn_Zero = rootView.findViewById(R.id.btn_Zero);
        btn_Edit = rootView.findViewById(R.id.btn_Edit);

        btn_Tare.setOnClickListener(this);
        btn_Zero.setOnClickListener(this);
        btn_Edit.setOnClickListener(this);
    }

    public void setOnViewCallBack(OnViewCallBack onViewCallBack) {
        this.onViewCallBack = onViewCallBack;
    }

    @Override
    public void onClick(View view) {
        if (ViewClick.isFastClick()){
            return;
        }
        if (view.getId() == R.id.btn_Tare){
            if (onViewCallBack != null){
                onViewCallBack.onViewTare();
            }
        }else if (view.getId() == R.id.btn_Zero){
            if (onViewCallBack != null){
                onViewCallBack.onViewZero();
            }
        }else if (view.getId() == R.id.btn_Edit){
            if (onViewCallBack != null){
                onViewCallBack.onViewEdit();
            }
        }
    }

    public interface OnViewCallBack{
        void onViewTare();
        void onViewZero();
        void onViewEdit();
    }
}
