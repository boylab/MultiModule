package com.boylab.multimodule.modbus.bean;


/**
 *      //线圈
 *     public static final byte READ_COILS  = 1;
 *     //离散量
 *     public static final byte READ_DISCRETE  = 2;
 *     //保持寄存器
 *     public static final byte READ_HOLD  = 3;
 *     //输入寄存器
 *     public static final byte READ_INPUT  = 4;
 *     //写单个线圈
 *     public static final byte WRITE_COIL  = 5;
 *     //写单个保持寄存器
 *     public static final byte WRITE_HOLD = 6;
 *     //写多个线圈
 *     public static final byte WRITE_COILS = 15;
 *     //写多个保持寄存器
 *     public static final byte WRITE_HOLDS = 16;
 */
public abstract class ReadModbus {
    //通讯地址
    private int slaveId;

    /**
     * 命令标识，用来区分命令
     * funcCode + start
     */
    private int what;   //命令标识，

    private int funcCode;   //功能码
    private int start;      //起始地址
    private int number;     //读寄存器个数

    /**
     * 读寄存器
     * @param funcCode
     * @param start
     * @param number
     */
    public ReadModbus(int funcCode, int start, int number) {
        this.what = (funcCode << 16) | start;
        this.funcCode = funcCode;
        this.start = start;
        this.number = number;
    }

    public int getSlaveId() {
        return slaveId;
    }

    public ReqModbus setSlaveId(int slaveId) {
        this.slaveId = slaveId;
        return null;
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

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "ReadCmd{" +
                "what=" + what +
                ", funcCode=" + funcCode +
                ", start=" + start +
                ", number=" + number +
                '}';
    }
}
