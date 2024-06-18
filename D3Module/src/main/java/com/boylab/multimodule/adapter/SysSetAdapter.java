package com.boylab.multimodule.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.boylab.multimodule.R;
import com.boylab.multimodule.bean.ParaItem;
import com.boylab.multimodule.view.ParaView;
import com.kongzue.dialogx.dialogs.InputDialog;
import com.kongzue.dialogx.dialogs.TipDialog;
import com.kongzue.dialogx.dialogs.WaitDialog;
import com.kongzue.dialogx.interfaces.OnInputDialogButtonClickListener;

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
        add(new ParaItem("通讯地址", true));
        add(new ParaItem("通讯发送延时", false));
    }};
    private List<Integer> valueList = null;

    public SysSetAdapter(Context context, List<Integer> valueList) {
        this.layoutInflater = LayoutInflater.from(context);
        this.valueList = valueList;
        /*if (paraList.size() == valueList.size()){
            for (int i = 0; i < paraList.size(); i++) {
                this.paraList.get(i).setValue(valueList.get(i));
            }
        }*/
    }

    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = layoutInflater.inflate(R.layout.layout_para_item, null);
        return new MyViewholder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewholder holder, @SuppressLint("RecyclerView") int position) {

        ParaItem paraBean = paraList.get(position);
        boolean isSelect = paraBean.isSelect();
        if (isSelect){
            holder.paraView.setSelect(paraBean.getList());
        }
        holder.paraView.setTag(position);
        holder.paraView.setLabel(paraBean.getLabel());
        if (position < valueList.size()){
            holder.paraView.setValue(valueList.get(position));
        }

        holder.paraView.setOnParaListener(new ParaView.OnParaListener() {
            @Override
            public void onParaClick(ParaView v) {
                int index = (int) v.getTag();
                String label = paraList.get(index).getLabel();
                int value = valueList.get(position);
                new InputDialog("温馨提示", "输入"+label, "确定", "取消")
                        .setInputText(String.valueOf(value))
                        .setOkButton(new OnInputDialogButtonClickListener<InputDialog>() {
                            @Override
                            public boolean onClick(InputDialog baseDialog, View v, String inputStr) {
                                String regx = "[0-9]+";
                                if (inputStr.matches(regx)){
                                    int value = Integer.parseInt(inputStr);
                                    if (index == paraList.size() - 2){
                                        if (value >=1 && value <=247){
                                            TipDialog.show("输入成功", WaitDialog.TYPE.SUCCESS);
                                            valueList.set(paraList.size() - 2, value);
                                        }else {
                                            TipDialog.show("输入失败，请输入1-247", WaitDialog.TYPE.ERROR);
                                        }
                                    }else if (index == paraList.size() - 1){
                                        if (value >=0 && value <=255){
                                            TipDialog.show("输入成功", WaitDialog.TYPE.SUCCESS);
                                            valueList.set(paraList.size() - 1, value);
                                        }else {
                                            TipDialog.show("输入失败，请输入0-255", WaitDialog.TYPE.ERROR);
                                        }
                                    }
                                }else {
                                    TipDialog.show("输入失败，请输入数字", WaitDialog.TYPE.ERROR);
                                }
                                notifyDataSetChanged();
                                return false;
                            }
                        })
                        .show();
            }

            @Override
            public void onParaSelect(ParaView v, int itemPosition) {
                int index = (int) v.getTag();
                if (position < valueList.size()){
                    valueList.set(index, itemPosition);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return paraList.size();
    }

    protected class MyViewholder extends RecyclerView.ViewHolder {

        private ParaView paraView;

        public MyViewholder(@NonNull View itemView) {
            super(itemView);
            paraView = itemView.findViewById(R.id.paraView);
        }
    }
}
