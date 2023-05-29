package com.boylab.multimodule.bean;

import android.content.Context;

import com.gotokeep.keep.taira.Taira;
import com.gotokeep.keep.taira.TairaData;
import com.gotokeep.keep.taira.annotation.ParamField;

import java.util.ArrayList;
import java.util.List;

/**
 * 一共25个寄存器
 *
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
 *
 * 79 0x004F 1 (说明书省掉了，定义成empty)
 *
 * 80 0x0050 2 用户标定零点
 * 82 0x0052 2 用户标定系数
 * 84 0x0054 2 用户重力加速度
 * 86 0x0056 2 分度值切换点 1
 * 88 0x0058 2 分度值切换点 2
 *  */
public class CalibPara extends BasePara<CalibPara> implements TairaData {

    public static final int MIN_REQUIRES = 25*2;

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
    private int filterStrength;

    @ParamField(order = 6,length = 2)
    private int judgeStable;

    @ParamField(order = 7,length = 2)
    private int bootZero;

    @ParamField(order = 8,length = 2)
    private int manualZero;

    @ParamField(order = 9,length = 2)
    private int zeroTraceMode;

    @ParamField(order = 10,length = 2)
    private int zeroTraceRange;

    @ParamField(order = 11,length = 2)
    private int zeroTraceSpeed;

    @ParamField(order = 12,length = 2)
    private int creepMode;

    @ParamField(order = 13,length = 2)
    private int empty;

    @ParamField(order = 14,length = 4)
    private int calibZero;

    @ParamField(order = 15,length = 4)
    private int calibCoe;

    @ParamField(order = 16,length = 4)
    private int gravity;

    @ParamField(order = 17,length = 4)
    private int scale1;

    @ParamField(order = 18,length = 4)
    private int scale2;

    @Override
    public void toPara(byte[] data) {
        if (data != null & data.length >= MIN_REQUIRES){
            CalibPara calibPara = Taira.DEFAULT.fromBytes(data, CalibPara.class);
            setReaded(calibPara != null);
            if (calibPara != null){
                freshPara(calibPara);
                //Log.i("___boylab>>>___", "toPara: calibPara = "+calibPara.toString());
            }
        }
    }

    @Override
    public void freshPara(CalibPara calibPara) {
        setDiv(calibPara.getDiv());
        setPoint(calibPara.getPoint());
        setUnit(calibPara.getUnit());
        setFullScale(calibPara.getFullScale());
        setFilterMode(calibPara.getFilterMode());
        setFilterStrength(calibPara.getFilterStrength());
        setJudgeStable(calibPara.getJudgeStable());
        setBootZero(calibPara.getBootZero());
        setManualZero(calibPara.getManualZero());
        setZeroTraceMode(calibPara.getZeroTraceMode());
        setZeroTraceRange(calibPara.getZeroTraceRange());
        setZeroTraceSpeed(calibPara.getZeroTraceSpeed());
        setCreepMode(calibPara.getCreepMode());
        setEmpty(calibPara.getEmpty());
        setCalibZero(calibPara.getCalibZero());
        setCalibCoe(calibPara.getCalibCoe());
        setGravity(calibPara.getGravity());
        setScale1(calibPara.getScale1());
        setScale2(calibPara.getScale2());
    }

    /**
     * 转成参数，进行设置
     * @return
     */
    public short[] toCalibShort() {
        short[] para = new short[]{
                (short) div,
                (short) point,
                (short) unit,
                (short) (fullScale >> 16 & 0xFFFF),
                (short) (fullScale & 0xFFFF),
                (short) filterMode,
                (short) filterStrength,
                (short) judgeStable,
                (short) bootZero,
                (short) manualZero,
                (short) zeroTraceMode,
                (short) zeroTraceRange,
                (short) zeroTraceSpeed,
                (short) creepMode,
                (short) empty
        };
        return para;
    }

    /**
     * 用于页面显示
     * @return
     */
    public List<Integer> calibParaList(){
        return new ArrayList<Integer>(){{
            add(0, div);
            add(1, point);
            add(2, unit);
            add(3, fullScale);
            add(4, filterMode);
            add(5, filterStrength);
            add(6, judgeStable);
            add(7, bootZero);
            add(8, manualZero);
            add(9, zeroTraceMode);
            add(10, zeroTraceRange);
            add(11, zeroTraceSpeed);
            add(12, creepMode);
            add(13, empty);
            add(14, calibZero);
            add(15, calibCoe);
            add(16, gravity);
            add(17, scale1);
            add(18, scale2);
        }};
    }

    /**
     * 页面修改后进行设置
     * @param calibList
     */
    public void setcalibParaList(final List<Integer> calibList){
        setDiv(calibList.get(0));
        setPoint(calibList.get(1));
        setUnit(calibList.get(2));
        setFullScale(calibList.get(3));
        setFilterMode(calibList.get(4));
        setFilterStrength(calibList.get(5));
        setJudgeStable(calibList.get(6));
        setBootZero(calibList.get(7));
        setManualZero(calibList.get(8));
        setZeroTraceMode(calibList.get(9));
        setZeroTraceRange(calibList.get(10));
        setZeroTraceSpeed(calibList.get(11));
        setCreepMode(calibList.get(12));
        setEmpty(calibList.get(13));
        setCalibZero(calibList.get(14));
        setCalibCoe(calibList.get(15));
        setGravity(calibList.get(16));
        setScale1(calibList.get(17));
        setScale2(calibList.get(18));
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

    public int getFilterStrength() {
        return filterStrength;
    }

    public void setFilterStrength(int filterStrength) {
        this.filterStrength = filterStrength;
    }

    public int getJudgeStable() {
        return judgeStable;
    }

    public void setJudgeStable(int judgeStable) {
        this.judgeStable = judgeStable;
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

    public int getZeroTraceMode() {
        return zeroTraceMode;
    }

    public void setZeroTraceMode(int zeroTraceMode) {
        this.zeroTraceMode = zeroTraceMode;
    }

    public int getZeroTraceRange() {
        return zeroTraceRange;
    }

    public void setZeroTraceRange(int zeroTraceRange) {
        this.zeroTraceRange = zeroTraceRange;
    }

    public int getZeroTraceSpeed() {
        return zeroTraceSpeed;
    }

    public void setZeroTraceSpeed(int zeroTraceSpeed) {
        this.zeroTraceSpeed = zeroTraceSpeed;
    }

    public int getCreepMode() {
        return creepMode;
    }

    public void setCreepMode(int creepMode) {
        this.creepMode = creepMode;
    }

    public int getEmpty() {
        return empty;
    }

    public void setEmpty(int empty) {
        this.empty = empty;
    }

    public int getCalibZero() {
        return calibZero;
    }

    public void setCalibZero(int calibZero) {
        this.calibZero = calibZero;
    }

    public int getCalibCoe() {
        return calibCoe;
    }

    public void setCalibCoe(int calibCoe) {
        this.calibCoe = calibCoe;
    }

    public int getGravity() {
        return gravity;
    }

    public void setGravity(int gravity) {
        this.gravity = gravity;
    }

    public int getScale1() {
        return scale1;
    }

    public void setScale1(int scale1) {
        this.scale1 = scale1;
    }

    public int getScale2() {
        return scale2;
    }

    public void setScale2(int scale2) {
        this.scale2 = scale2;
    }

    @Override
    public String toString() {
        return "CalibPara{" +
                "div=" + div +
                ", point=" + point +
                ", unit=" + unit +
                ", fullScale=" + fullScale +
                ", filterMode=" + filterMode +
                ", filterStrength=" + filterStrength +
                ", judgeStable=" + judgeStable +
                ", bootZero=" + bootZero +
                ", manualZero=" + manualZero +
                ", zeroTraceMode=" + zeroTraceMode +
                ", zeroTraceRange=" + zeroTraceRange +
                ", zeroTraceSpeed=" + zeroTraceSpeed +
                ", creepMode=" + creepMode +
                ", empty=" + empty +
                ", calibZero=" + calibZero +
                ", calibCoe=" + calibCoe +
                ", gravity=" + gravity +
                ", scale1=" + scale1 +
                ", scale2=" + scale2 +
                '}';
    }
}
