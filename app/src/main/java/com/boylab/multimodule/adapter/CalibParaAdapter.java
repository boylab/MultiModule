package com.boylab.multimodule.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.boylab.multimodule.R;
import com.boylab.multimodule.bean.ParaItem;
import com.boylab.multimodule.view.ParaView;

import java.util.ArrayList;
import java.util.List;

/**
 * 65 0x0041 1 分度值 厂商值
 * 66 0x0042 1 小数点 厂商值
 * 67 0x0043 1 重量单位 厂商值
 * 68 0x0044 2 满量程值 厂商值
 * 70 0x0046 1 滤波模式 0
 * 71 0x0047 1 滤波强度 3
 * 72 0x0048 1 判稳范围 3
 * 73 0x0049 1 开机置零范围（FULL%） 20
 * 74 0x004A 1 手动置零范围（FULL%） 4
 * 75 0x004B 1 零点跟踪模式 0
 * 76 0x004C 1 零点跟踪范围 0
 * 77 0x004D 1 零点跟踪速度 1
 * 78 0x004E 1 补偿模式 0
 * 80 0x0050 2 用户标定零点
 * 82 0x0052 2 用户标定系数
 * 84 0x0054 2 用户重力加速度
 * 86 0x0056 2 分度值切换点 1
 * 88 0x0058 2 分度值切换点 2
 *  */
public class CalibParaAdapter extends RecyclerView.Adapter<CalibParaAdapter.MyViewholder> {

    private LayoutInflater layoutInflater = null;
    private List<ParaItem> paraList = new ArrayList<ParaItem>(){{
        add(new ParaItem("分度值", new String[]{"1", "2", "5", "10", "20", "50", "100", "200", "500", "1000", "2000", "5000"}));
        add(new ParaItem("小数点", new String[]{"0", "1", "2", "3", "4"}));
        add(new ParaItem("重量单位", new String[]{"kg", "g", "t", "lb", "N", "kN", "mL", "L", "kL"}));
        add(new ParaItem("满量程值"));
        add(new ParaItem("滤波模式", new String[]{"滑动滤波", "快速滤波"}));
        add(new ParaItem("滤波强度", new String[]{"0", "1", "2", "3"}));
        add(new ParaItem("判稳范围", new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"}));
        add(new ParaItem("开机置零范围"));
        add(new ParaItem("手动置零范围"));
        add(new ParaItem("零点跟踪模式", new String[]{"毛重为0跟踪","净重为0跟踪"}));
        add(new ParaItem("零点跟踪范围", new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"}));
        add(new ParaItem("零点跟踪速度", new String[]{"0", "1", "2", "3"}));
        add(new ParaItem("蠕变补偿", new String[]{"不使用", "使用"}));
        add(new ParaItem("闪变点抑制", new String[]{"不使用", "使用"}));
        add(new ParaItem("用户标定零点"));
        add(new ParaItem("用户标定系数"));
        add(new ParaItem("用户重力加速度"));
        add(new ParaItem("分度值切换点"));
        add(new ParaItem("分度值切换点"));
    }};

    public CalibParaAdapter(Context context, List<Integer> valueList) {
        this.layoutInflater = LayoutInflater.from(context);
        if (paraList.size() == valueList.size()){
            for (int i = 0; i < paraList.size(); i++) {
                this.paraList.get(i).setValue(valueList.get(i));
            }
        }
    }

    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = layoutInflater.inflate(R.layout.layout_para_item, null);
        return new MyViewholder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewholder holder, int position) {

        ParaItem paraBean = paraList.get(position);
        boolean isSelect = paraBean.isSelect();
        if (isSelect){
            holder.paraView.setSelect(paraBean.getList());
        }
        holder.paraView.setLabel(paraBean.getLabel());
        holder.paraView.setValue(paraBean.getValue());
    }

    @Override
    public int getItemCount() {
        return paraList.size();
    }

    public class MyViewholder extends RecyclerView.ViewHolder {

        private ParaView paraView;

        public MyViewholder(@NonNull View itemView) {
            super(itemView);
            paraView = itemView.findViewById(R.id.paraView);
        }
    }
}
