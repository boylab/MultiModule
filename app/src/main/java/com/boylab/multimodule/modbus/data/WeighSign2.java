package com.boylab.multimodule.modbus.data;

public class WeighSign2 {

    /**
     *  bit15~8    bit7 bit6 bit5 bit4 bit3 bit2 bit1 bit0
     * 分度值代号   备用 备用 备用 备用  备用 备用 备用 累计
     */

    private int sign;

    private boolean isSum;
    private int divCode;

    public WeighSign2() {
    }

    public synchronized void setSign(int sign) {
        this.sign = sign;
        isSum = (sign & 0x01) != 0;
        divCode = (sign >> 8) & 0xFF;
    }

    public boolean isSum() {
        return isSum;
    }

    public void setSum(boolean sum) {
        isSum = sum;
    }

    public int getDivCode() {
        return divCode;
    }

    public void setDivCode(int divCode) {
        this.divCode = divCode;
    }

    @Override
    public String toString() {
        return "WeighSign2{" +
                "sign=" + sign +
                ", isSum=" + isSum +
                ", divCode=" + divCode +
                '}';
    }
}
