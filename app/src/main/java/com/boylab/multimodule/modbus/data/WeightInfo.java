package com.boylab.multimodule.modbus.data;

import com.gotokeep.keep.taira.Taira;
import com.gotokeep.keep.taira.TairaData;
import com.gotokeep.keep.taira.annotation.ParamField;

public class WeightInfo implements TairaData {

    /**0x0000 2 毛重 注 1
     * 0x0002 2 皮重
     * 0x0004 2 净重
     * 0x0006 1 状态标志 1 注 2
     * 0x0007 1 状态标志 2 注 3
    */
    @ParamField(order = 0,length = 4)
    private int gross;
    @ParamField(order = 1,length = 4)
    private int tare;
    @ParamField(order = 2,length = 4)
    private int net;
    @ParamField(order = 3,length = 2)
    private int sign1;
    @ParamField(order = 4,length = 2)
    private int sign2;
    private WeighSign1 weighSign1 = new WeighSign1();
    private WeighSign2 weighSign2 = new WeighSign2();

    private static WeightInfo instance = null;

    public WeightInfo() {
    }

    public static WeightInfo info(){
        if (instance == null){
            instance = new WeightInfo();
        }
        return instance;
    }

    public void toInfo(byte[] data){
        if (data.length >= 35 * 2){
            WeightInfo mWeightInfo = Taira.DEFAULT.fromBytes(data, WeightInfo.class);
            if (mWeightInfo != null){
                this.freshInfo(mWeightInfo);
            }
        }
    }

    private void freshInfo(WeightInfo mWeightInfo){
        setGross(mWeightInfo.getGross());
        setTare(mWeightInfo.getTare());
        setNet(mWeightInfo.getNet());
        setSign1(mWeightInfo.getSign1());
        setSign2(mWeightInfo.getSign2());
        weighSign1.setSign(this.sign1);
        weighSign2.setSign(this.sign2);
    }

    public int getGross() {
        return gross;
    }

    public String grossFormat() {
        int point = weighSign1.getPoint();
        double realGross = gross / Math.pow(10, point);
        String formatWeigh = String.format("%6."+point+"f", realGross);
        return formatWeigh;
    }

    public void setGross(int gross) {
        this.gross = gross;
    }

    public int getTare() {
        return tare;
    }

    public String tareFormat() {
        int point = weighSign1.getPoint();
        double realTare = tare / Math.pow(10, point);
        String formatWeigh = String.format("%6."+point+"f", realTare);
        return formatWeigh;
    }

    public void setTare(int tare) {
        this.tare = tare;
    }

    public int getNet() {
        return net;
    }

    public String netFormat() {
        //return net;
        int point = weighSign1.getPoint();
        double realNet = net / Math.pow(10, point);
        String formatWeigh = String.format("%6."+point+"f", realNet);
        /*String grossValue = String.valueOf(gross);
        for (int i = 0; i < point; i++) {
            if (!grossValue.matches("^-?\\d+.\\d{"+point+"}$")){
                grossValue = grossValue.concat("0");
            }else {
                break;
            }
        }*/
        return formatWeigh;
    }

    public void setNet(int net) {
        this.net = net;
    }

    public int getSign1() {
        return sign1;
    }

    public void setSign1(int sign1) {
        this.sign1 = sign1;
    }

    public int getSign2() {
        return sign2;
    }

    public void setSign2(int sign2) {
        this.sign2 = sign2;
    }

    public WeighSign1 weighSign1() {
        return weighSign1;
    }

    public WeighSign2 weighSign2() {
        return weighSign2;
    }


    @Override
    public String toString() {
        return "WeightInfo{" +
                "gross=" + gross +
                ", tare=" + tare +
                ", net=" + net +
                ", sign1=" + sign1 +
                ", sign2=" + sign2 +
                ", weighSign1=" + weighSign1 +
                ", weighSign2=" + weighSign2 +
                '}';
    }
}
