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

/* 48 0x0030 1 通讯模式
 * 49 0x0031 1 波特率
 * 50 0x0032 1 校验方式
 * 51 0x0033 1 通讯地址
 * 52 0x0034 1 通讯发送延时
 * */
public class SysSetAdapter extends RecyclerView.Adapter<SysSetAdapter.MyViewholder> {

    private LayoutInflater layoutInflater = null;
    private List<ParaItem> paraList = new ArrayList<ParaItem>(){{
        add(new ParaItem("通讯模式", new String[]{"Modbus-RTU", "自定义协议", "小黄狗定制协议"}));
        add(new ParaItem("波特率", new String[]{"600", "1200", "2400", "4800", "9600", "14400", "19200", "28800", "38400", "57600", "76800", "96000", "115200", "160000", "200000", "250000"}));
        add(new ParaItem("校验方式", new String[]{"无校验", "奇校验", "偶校验", "零校验"}));
        add(new ParaItem("通讯地址"));
        add(new ParaItem("通讯发送延时"));
    }};

    public SysSetAdapter(Context context, List<Integer> valueList) {
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
