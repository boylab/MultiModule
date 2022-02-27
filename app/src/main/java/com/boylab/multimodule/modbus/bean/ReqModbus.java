package com.boylab.multimodule.modbus.bean;

import java.util.Arrays;

public class ReqModbus {

    private int what;   //命令标记

    private int funcCode;   //用于区分是什么命令

    private int slaveId = 1;
    private int start;

    private int len;    //读寄存器个数

    private short writeShort;       //写单个保持寄存器
    private boolean writeBool;      //写单个线圈

    private short[] writeValue;    //写多个保持寄存器
    private boolean[] writeBools;  //写多个线圈

    /**
     * 读寄存器命令
     * @param funcCode
     * @param start
     * @param len
     */
    public ReqModbus(int what, int funcCode, int start, int len) {
        this.what = what;
        this.funcCode = funcCode;
        this.start = start;
        this.len = len;
    }

    /**
     * 写单个保持寄存器
     * @param funcCode
     * @param start
     * @param writeShort
     */
    public ReqModbus(int what, int funcCode, int start, short writeShort) {
        this.what = what;
        this.funcCode = funcCode;
        this.start = start;
        this.writeShort = writeShort;
    }

    /**
     * 写多个保持寄存器
     * @param funcCode
     * @param start
     * @param writeValue
     */
    public ReqModbus(int what, int funcCode, int start, short[] writeValue) {
        this.what = what;
        this.funcCode = funcCode;
        this.start = start;
        this.writeValue = writeValue;
    }

    /**
     * 写单个线圈
     * @param funcCode
     * @param start
     * @param writeBool
     */
    public ReqModbus(int what, int funcCode, int start, boolean writeBool) {
        this.what = what;
        this.funcCode = funcCode;
        this.start = start;
        this.writeBool = writeBool;
    }

    /**
     * 写多个线圈
     * @param funcCode
     * @param start
     * @param writeBools
     */
    public ReqModbus(int what, int funcCode, int start, boolean[] writeBools) {
        this.what = what;
        this.funcCode = funcCode;
        this.start = start;
        this.writeBools = writeBools;
    }

    public int what() {
        return what;
    }

    public void setWhat(int what) {
        this.what = what;
    }

    public int getFuncCode() {
        return funcCode;
    }

    public void setFuncCode(int funcCode) {
        this.funcCode = funcCode;
    }

    public int getSlaveId() {
        return slaveId;
    }

    public void setSlaveId(int slaveId) {
        this.slaveId = slaveId;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public short getWriteShort() {
        return writeShort;
    }

    public void setWriteShort(short writeShort) {
        this.writeShort = writeShort;
    }

    public boolean isWriteBool() {
        return writeBool;
    }

    public void setWriteBool(boolean writeBool) {
        this.writeBool = writeBool;
    }

    public short[] getWriteValue() {
        return writeValue;
    }

    public void setWriteValue(short[] writeValue) {
        this.writeValue = writeValue;
    }

    public boolean[] getWriteBools() {
        return writeBools;
    }

    public void setWriteBools(boolean[] writeBools) {
        this.writeBools = writeBools;
    }

    @Override
    public String toString() {
        return "ReqModbus{" +
                "funcCode=" + funcCode +
                ", slaveId=" + slaveId +
                ", start=" + start +
                ", readLen=" + len +
                ", writeShort=" + writeShort +
                ", writeBool=" + writeBool +
                ", writeValue=" + Arrays.toString(writeValue) +
                ", writeBools=" + Arrays.toString(writeBools) +
                '}';
    }
}
