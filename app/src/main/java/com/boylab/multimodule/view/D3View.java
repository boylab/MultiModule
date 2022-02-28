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
    private CheckBox box_Stable, box_Tare, box_Zero;
    private Button btn_Add, btn_Remove, btn_Tare, btn_Zero;

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

        box_Stable = rootView.findViewById(R.id.box_Stable);
        box_Tare = rootView.findViewById(R.id.box_Tare);
        box_Zero = rootView.findViewById(R.id.box_Zero);

        btn_Add = rootView.findViewById(R.id.btn_Add);
        btn_Remove = rootView.findViewById(R.id.btn_Remove);
        btn_Tare = rootView.findViewById(R.id.btn_Tare);
        btn_Zero = rootView.findViewById(R.id.btn_Zero);

        btn_Add.setOnClickListener(this);
        btn_Remove.setOnClickListener(this);
        btn_Tare.setOnClickListener(this);
        btn_Zero.setOnClickListener(this);
    }

    public void setData(String weight, boolean isStable, boolean isTare, boolean isZero ){
        text_Weight.setText(weight);
        box_Stable.setChecked(isStable);
        box_Tare.setChecked(isTare);
        box_Zero.setChecked(isZero);
    }

    public void setOnViewCallBack(OnViewCallBack onViewCallBack) {
        this.onViewCallBack = onViewCallBack;
    }

    @Override
    public void onClick(View view) {
        if (ViewClick.isFastClick()){
            return;
        }
        if (view.getId() == R.id.btn_Add){
            if (onViewCallBack != null){
                onViewCallBack.onViewAdd();
            }
        }else if (view.getId() == R.id.btn_Remove){
            if (onViewCallBack != null){
                onViewCallBack.onViewRemove();
            }
        }else if (view.getId() == R.id.btn_Tare){
            if (onViewCallBack != null){
                onViewCallBack.onViewTare();
            }
        }else if (view.getId() == R.id.btn_Zero){
            if (onViewCallBack != null){
                onViewCallBack.onViewZero();
            }
        }
    }

    public interface OnViewCallBack{
        void onViewAdd();
        void onViewRemove();
        void onViewTare();
        void onViewZero();
    }
}
