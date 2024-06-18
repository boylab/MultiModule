package com.boylab.multimodule.bean;

import android.content.Context;
import android.util.Log;

import com.gotokeep.keep.taira.Taira;
import com.gotokeep.keep.taira.TairaData;
import com.gotokeep.keep.taira.annotation.ParamField;
import com.gotokeep.keep.taira.exception.TairaAnnotationException;

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

    @ParamField(order = 0,bytes = 2)
    private int div;

    @ParamField(order = 1,bytes = 2)
    private int point;

    @ParamField(order = 2,bytes = 2)
    private int unit;

    @ParamField(order = 3,bytes = 4)
    private int fullScale;

    @ParamField(order = 4,bytes = 2)
    private int filterMode;

    @ParamField(order = 5,bytes = 2)
    private int filterStrength;

    @ParamField(order = 6,bytes = 2)
    private int judgeStable;

    @ParamField(order = 7,bytes = 2)
    private int bootZero;

    @ParamField(order = 8,bytes = 2)
    private int manualZero;

    @ParamField(order = 9,bytes = 2)
    private int zeroTraceMode;

    @ParamField(order = 10,bytes = 2)
    private int zeroTraceRange;

    @ParamField(order = 11,bytes = 2)
    private int zeroTraceSpeed;

    @ParamField(order = 12,bytes = 2)
    private int creepMode;

    @ParamField(order = 13,bytes = 2)
    private int empty;

    @ParamField(order = 14,bytes = 4)
    private int calibZero;

    @ParamField(order = 15,bytes = 4)
    private int calibCoe;

    @ParamField(order = 16,bytes = 4)
    private int gravity;

    @ParamField(order = 17,bytes = 4)
    private int scale1;

    @ParamField(order = 18,bytes = 4)
    private int scale2;

    private int isCreep = 0;    //蠕变补偿（bit0=0：不使用；bit0=1：使用）
    private int isInhibit = 0;  //闪变点抑制（bit1=0：不使用；bit1=1：使用）

    @Override
    public void toPara(byte[] data) {
        if (data != null && data.length >= MIN_REQUIRES){
            try {
                CalibPara calibPara = Taira.DEFAULT.fromBytes(data, CalibPara.class);
                setReaded(calibPara != null);
                if (calibPara != null){
                    freshPara(calibPara);
                    Log.i("___boylab>>>___", "toPara: calibPara = "+calibPara.toString());
                }
            }catch (TairaAnnotationException e){
                e.printStackTrace();
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
            add(0, calibZero);
            add(1, judgeStable);
            add(2, calibCoe);
            add(3, bootZero);
            add(4, div);
            add(5, manualZero);
            add(6, point);
            add(7, zeroTraceMode);
            add(8, unit);
            add(9, zeroTraceRange);
            add(10, fullScale);
            add(11, zeroTraceSpeed);
            add(12, gravity);
            add(13, isCreep);
            add(14, filterMode);
            add(15, scale1);
            add(16, filterStrength);
            add(17, scale2);
            add(18, isInhibit);
        }};
    }

    /**
     * 页面修改后进行设置
     * @param calibList
     */
    public void setcalibParaList(final List<Integer> calibList){
        setCalibZero(calibList.get(0));
        setJudgeStable(calibList.get(1));
        setCalibCoe(calibList.get(2));
        setBootZero(calibList.get(3));
        setDiv(calibList.get(4));
        setManualZero(calibList.get(5));
        setPoint(calibList.get(6));
        setZeroTraceMode(calibList.get(7));
        setUnit(calibList.get(8));
        setZeroTraceRange(calibList.get(9));
        setFullScale(calibList.get(10));
        setZeroTraceSpeed(calibList.get(11));
        setGravity(calibList.get(12));
        setIsCreep(calibList.get(13));
        setFilterMode(calibList.get(14));
        setScale1(calibList.get(15));
        setFilterStrength(calibList.get(16));
        setScale2(calibList.get(17));
        setIsInhibit(calibList.get(18));
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
        setIsCreep(this.creepMode & 0b01);
        setIsInhibit((this.creepMode >> 1) & 0b01);
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

    public int getIsCreep() {
        return isCreep;
    }

    public void setIsCreep(int isCreep) {
        this.isCreep = isCreep;
    }

    public int getIsInhibit() {
        return isInhibit;
    }

    public void setIsInhibit(int isInhibit) {
        this.isInhibit = isInhibit;
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
