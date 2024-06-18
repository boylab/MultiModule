package com.boylab.multimodule.modbus.bean;

import java.util.Arrays;

public class ReqModbus extends ReadModbus{

    @Override
    public ReqModbus setSlaveId(int slaveId) {
        super.setSlaveId(slaveId);
        return this;
    }

    private short[] writeValue;     //写多个保持寄存器

    private boolean writeBool;      //写单个线圈

    public ReqModbus(int funcCode, int start, int number) {
        super(funcCode, start, number);
    }

    public ReqModbus(int funcCode, int start, short[] writeValue) {
        super(funcCode, start, writeValue.length);
        this.writeValue = writeValue;
    }

    public ReqModbus(int funcCode, int start, boolean writeBool) {
        super(funcCode, start, 1);
        this.writeBool = writeBool;
    }

    public short[] getWriteValue() {
        return writeValue;
    }

    public void setWriteValue(short[] writeValue) {
        this.writeValue = writeValue;
    }

    public boolean getWriteBool() {
        return writeBool;
    }

    public void setWriteBool(boolean writeBool) {
        this.writeBool = writeBool;
    }

    /**
     * 用于会写参数
     * @param shortCode
     * @return
     */
    public ReqModbus modifyShort(short[] shortCode) {
        int length = shortCode.length;
        this.writeValue = new short[length];
        System.arraycopy(shortCode, 0, this.writeValue, 0, length);
        return this;
    }

    /**
     * 用于发送高级命令
     * @param options
     * @return
     */
    public ReqModbus modifyShort(ShortOption... options) {
        for (ShortOption option:options) {
            this.writeValue[option.start()] = option.value();
        }
        return this;
    }

    public ReqModbus modifyBool(boolean boolCode) {
        this.writeBool = boolCode;
        return this;
    }

    @Override
    public String toString() {
        return "ReqModbus{" +
                super.toString() +
                "writeValue=" + Arrays.toString(writeValue) +
                ", writeBool=" + writeBool +
                '}';
    }
}
