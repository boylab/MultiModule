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

        add(new ParaItem("用户标定零点", false));
        add(new ParaItem("判稳范围", new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"}));
        add(new ParaItem("用户标定系数", false));
        add(new ParaItem("开机置零范围±%", true));

        add(new ParaItem("分度值", new String[]{"1", "2", "5", "10", "20", "50", "100", "200", "500", "1000", "2000", "5000"}));
        add(new ParaItem("手动置零范围±%", true));
        add(new ParaItem("小数点", new String[]{"0", "1", "2", "3", "4"}));
        add(new ParaItem("零点跟踪模式", new String[]{"毛重为0跟踪","净重为0跟踪"}));

        add(new ParaItem("重量单位", new String[]{"kg", "g", "t", "lb", "N", "kN", "mL", "L", "kL"}));
        add(new ParaItem("零点跟踪范围", new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"}));
        add(new ParaItem("满量程值", true));
        add(new ParaItem("零点跟踪速度", new String[]{"0", "1", "2", "3"}));

        add(new ParaItem("用户重力加速度", false));
        add(new ParaItem("蠕变补偿", new String[]{"不使用", "使用"}));
        add(new ParaItem("滤波模式", new String[]{"滑动滤波", "快速滤波"}));
        add(new ParaItem("分度值切换点1", false));
        add(new ParaItem("滤波强度", new String[]{"0", "1", "2", "3"}));
        add(new ParaItem("分度值切换点2", false));
        add(new ParaItem("闪变点抑制", new String[]{"不使用", "使用"}));

    }};
    private List<Integer> valueList = null;

    public CalibParaAdapter(Context context, List<Integer> valueList) {
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

        if (paraBean.getLabel() == "满量程"){
            int point = valueList.get(1);
            double scale = valueList.get(position)/Math.pow(10,point);
            String value = String.format("%6.2f", scale).trim();
            holder.paraView.setValue(value);
        }else if (position < valueList.size()){
            holder.paraView.setValue(valueList.get(position));
        }
        if (paraBean.isCanEdit()){
            holder.paraView.setOnParaListener(new ParaView.OnParaListener() {
                @Override
                public void onParaClick(ParaView v) {
                    int index = (int) v.getTag();
                    String label = paraList.get(index).getLabel();
                    String value = "";
                    if (label == "满量程"){
                        //满量程
                        int point = valueList.get(1);
                        double scale = valueList.get(position)/Math.pow(10,point);
                        value = String.format("%6.2f", scale).trim();
                    }else {
                        value = String.valueOf(valueList.get(position));
                    }
                    new InputDialog("温馨提示", "输入"+label, "确定", "取消")
                            .setInputText(value)
                            .setOkButton(new OnInputDialogButtonClickListener<InputDialog>() {
                                @Override
                                public boolean onClick(InputDialog baseDialog, View v, String inputStr) {
                                    if (label == "满量程"){
                                        int point = valueList.get(1);
                                        String regx = "^[0-9]+(\\.[0-9]{0,"+point+"})?$";
                                        if (inputStr.matches(regx)){
                                            double value = Double.parseDouble(inputStr);
                                            int scale = (int) (value * Math.pow(10, point));
                                            valueList.set(index, scale);
                                        }else {
                                            TipDialog.show("请输入数字", WaitDialog.TYPE.ERROR);
                                        }
                                    }else if (label.contains("置零范围")){
                                        String regx = "[0-9]+";
                                        if (inputStr.matches(regx)){
                                            int value = Integer.parseInt(inputStr);
                                            if (value >=0 && value <=100){
                                                TipDialog.show("输入成功", WaitDialog.TYPE.SUCCESS);
                                            }else {
                                                TipDialog.show("请输入0-100", WaitDialog.TYPE.ERROR);
                                            }
                                        }else {
                                            TipDialog.show("请输入数字", WaitDialog.TYPE.ERROR);
                                        }
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
        }else {
            //TipDialog.show("暂不可修改");
        }
    }

    @Override
    public int getItemCount() {
        return paraList.size();
    }

    class MyViewholder extends RecyclerView.ViewHolder {

        private ParaView paraView;

        public MyViewholder(@NonNull View itemView) {
            super(itemView);
            paraView = itemView.findViewById(R.id.paraView);
        }
    }
}
