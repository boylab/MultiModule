package com.boylab.multimodule.modbus.data;

import com.gotokeep.keep.taira.Taira;
import com.gotokeep.keep.taira.TairaData;
import com.gotokeep.keep.taira.annotation.ParamField;

public class CalibScale implements TairaData {

    /**
     * 0x0041 1 分度值 厂商值
     * 0x0042 1 小数点 厂商值
     * 0x0043 1 重量单位 厂商值
     * 0x0044 2 满量程值 厂商值
     * 0x0046 1 滤波模式 0
     * 0x0047 1 滤波强度 3
     * 0x0048 1 判稳范围 3
     * 0x0049 1 开机置零范围（FULL%） 20
     * 0x004A 1 手动置零范围（FULL%） 4
     * 0x004B 1 零点跟踪模式 0
     * 0x004C 1 零点跟踪范围 0
     * 0x004D 1 零点跟踪速度 1
     * 0x004E 1 补偿模式 0
     */
    @ParamField(order = 0,length = 2)
    private int div;
    @ParamField(order = 1,length = 2)
    private int point;
    @ParamField(order = 2,length = 2)
    private int unit;
    @ParamField(order = 3,length = 4)
    private int fullScale;
    @ParamField(order = 4,length = 2)
    private int filterMode;
    @ParamField(order = 5,length = 2)
    private int filterLevel;
    @ParamField(order = 6,length = 2)
    private int stableRange;
    @ParamField(order = 7,length = 2)
    private int bootZero;
    @ParamField(order = 8,length = 2)
    private int manualZero;
    @ParamField(order = 9,length = 2)
    private int zeroMode;
    @ParamField(order = 10,length = 2)
    private int zeroRange;
    @ParamField(order = 11,length = 2)
    private int zeroSpeed;
    @ParamField(order = 12,length = 2)
    private int compensate; //补偿

    private static CalibScale instance = null;

    public CalibScale() {
    }

    public static CalibScale info(){
        if (instance == null){
            instance = new CalibScale();
        }
        return instance;
    }

    public void toCalib(byte[] data){
        if (data.length >= 35 * 2){
            CalibScale mCalibScale = Taira.DEFAULT.fromBytes(data, CalibScale.class);
            if (mCalibScale != null){
                this.freshInfo(mCalibScale);
            }
        }
    }

    private void freshInfo(CalibScale mCalibScale){
        setDiv(mCalibScale.getDiv());
        setPoint(mCalibScale.getPoint());
        setUnit(mCalibScale.getUnit());
        setFullScale(mCalibScale.getFullScale());
        setFilterMode(mCalibScale.getFilterMode());
        setFilterLevel(mCalibScale.getFilterLevel());
        setBootZero(mCalibScale.getBootZero());
        setManualZero(mCalibScale.getManualZero());
        setZeroMode(mCalibScale.getZeroMode());
        setZeroRange(mCalibScale.getZeroRange());
        setZeroSpeed(mCalibScale.getZeroSpeed());
        setCompensate(mCalibScale.getCompensate());
    }

    public int getDiv() {
        return div;
    }

    public void setDiv(int div) {
        this.div = div;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public int getFullScale() {
        return fullScale;
    }

    public void setFullScale(int fullScale) {
        this.fullScale = fullScale;
    }

    public int getFilterMode() {
        return filterMode;
    }

    public void setFilterMode(int filterMode) {
        this.filterMode = filterMode;
    }

    public int getFilterLevel() {
        return filterLevel;
    }

    public void setFilterLevel(int filterLevel) {
        this.filterLevel = filterLevel;
    }

    public int getBootZero() {
        return bootZero;
    }

    public void setBootZero(int bootZero) {
        this.bootZero = bootZero;
    }

    public int getManualZero() {
        return manualZero;
    }

    public void setManualZero(int manualZero) {
        this.manualZero = manualZero;
    }

    public int getZeroMode() {
        return zeroMode;
    }

    public void setZeroMode(int zeroMode) {
        this.zeroMode = zeroMode;
    }

    public int getZeroRange() {
        return zeroRange;
    }

    public void setZeroRange(int zeroRange) {
        this.zeroRange = zeroRange;
    }

    public int getZeroSpeed() {
        return zeroSpeed;
    }

    public void setZeroSpeed(int zeroSpeed) {
        this.zeroSpeed = zeroSpeed;
    }

    public int getCompensate() {
        return compensate;
    }

    public void setCompensate(int compensate) {
        this.compensate = compensate;
    }

    @Override
    public String toString() {
        return "CalibScale{" +
                "div=" + div +
                ", point=" + point +
                ", unit=" + unit +
                ", fullScale=" + fullScale +
                ", filterMode=" + filterMode +
                ", filterLevel=" + filterLevel +
                ", bootZero=" + bootZero +
                ", manualZero=" + manualZero +
                ", zeroMode=" + zeroMode +
                ", zeroRange=" + zeroRange +
                ", zeroSpeed=" + zeroSpeed +
                ", compensate=" + compensate +
                '}';
    }
}
