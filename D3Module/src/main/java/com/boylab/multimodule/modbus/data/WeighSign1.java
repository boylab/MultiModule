package com.boylab.multimodule.modbus.data;

public class WeighSign1 {

    /**bit15~12  bit11  bit10~8  bit7  bit6  bit5  bit4  bit3  bit2  bit1  bit0
     * 单位        0    小数点    I/O   锁定  异常  开机  超载   去皮  稳定  零位*/

    private int sign;

    private boolean isZero;
    private boolean isStable;
    private boolean isTare;
    private boolean isBoot;

    private boolean isADError;
    private boolean islock;
    private boolean isIO;
    private int point;
    private int unit;

    public WeighSign1() {
    }

    public synchronized void setSign(int sign) {
        this.sign = sign;
        isZero = (sign & 0x01) != 0;
        isStable = (sign & 0x02) != 0;
        isTare = (sign & 0x04) != 0;
        isBoot = (sign & 0x08) != 0;

        isADError = (sign & 0x10) != 0;
        islock = (sign & 0x20) != 0;
        isIO = (sign & 0x40) != 0;

        point = (sign >> 8) & 0x07;
        unit = (sign >> 12) & 0x0F;
    }

    public boolean isZero() {
        return isZero;
    }

    public void setZero(boolean zero) {
        isZero = zero;
    }

    public boolean isStable() {
        return isStable;
    }

    public void setStable(boolean stable) {
        isStable = stable;
    }

    public boolean isTare() {
        return isTare;
    }

    public void setTare(boolean tare) {
        isTare = tare;
    }

    public boolean isBoot() {
        return isBoot;
    }

    public void setBoot(boolean boot) {
        isBoot = boot;
    }

    public boolean isADError() {
        return isADError;
    }

    public void setADError(boolean ADError) {
        isADError = ADError;
    }

    public boolean isIslock() {
        return islock;
    }

    public void setIslock(boolean islock) {
        this.islock = islock;
    }

    public boolean isIO() {
        return isIO;
    }

    public void setIO(boolean IO) {
        isIO = IO;
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

    @Override
    public String toString() {
        return "WeighSign1{" +
                "sign=" + sign +
                ", unit=" + unit +
                ", point=" + point +
                ", isIO=" + isIO +
                ", islock=" + islock +
                ", isException=" + isADError +
                ", isBoot=" + isBoot +
                ", isTare=" + isTare +
                ", isStable=" + isStable +
                ", isZero=" + isZero +
                '}';
    }
}
