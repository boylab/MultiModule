package com.boylab.multimodule.modbus.socket;

import com.boylab.multimodule.modbus.bean.ReqModbus;

import java.util.HashMap;

public class Command {

    public static final int readInfo   = 0x01;
    /*public static final int readCiols  = 0x02;
    public static final int writeCiol  = 0x03;
    public static final int writeCiols = 0x04;
    public static final int readCalib  = 0x05;
    public static final int writeCalib = 0x06;*/

    public static final int ZERO       = 0x07;
    public static final int TARE       = 0x08;
    public static final int LockWeigh  = 0x09;
    public static final int SUM        = 0x0A;
    /*public static final int calibZero  = 0x0B;
    public static final int calibWeigh = 0x0C;
    public static final int switchRate = 0x0D;
    public static final int clearRate  = 0x0E;*/

    private static HashMap<Integer, ReqModbus> CMD = new HashMap<Integer, ReqModbus>(){{
        put(readInfo,   new ReqModbus(readInfo,0x04, 0x0000, 8));
       /* put(readCiols,  new ReqModbus(readCiols,0x01, 0x0000, 0));
        put(writeCiol,  new ReqModbus(writeCiol,0x05, 0x0000, 0));
        put(writeCiols, new ReqModbus(writeCiols,0x0F, 0x0000, 0));
        put(readCalib,  new ReqModbus(readCalib,0x03, 0x0041, 9));
        put(writeCalib, new ReqModbus(writeCalib,0x10, 0x0041, new short[9]));*/

        put(ZERO,       new ReqModbus(ZERO, 0x10, 0x4743, new short[]{0x4743, 0x0101, (short) 0xFEFE, 0x0002, 0x0000, 0x0000}));
        put(TARE,       new ReqModbus(TARE, 0x10, 0x4743, new short[]{0x4743, 0x0202, (short) 0xFEFE, 0x0001, 0x0000}));
        put(LockWeigh,  new ReqModbus(LockWeigh, 0x10, 0x4743, new short[]{0x4743, 0x0303, (short) 0xFEFE, 0x0001, 0x0000}));
        put(SUM,        new ReqModbus(SUM, 0x10, 0x4743, new short[]{0x4743, 0x0404, (short) 0xFEFE, 0x0002, 0x0000, 0x0000}));
        /*put(calibZero,  new ReqModbus(calibZero, 0x10, 0x4743, new short[]{0x4743, 0x3030, (short) 0xCFCF, 0x0001, 0x0000}));
        put(calibWeigh, new ReqModbus(calibWeigh, 0x10, 0x4743, new short[]{0x4743, 0x3131, (short) 0xCECE, 0x0003, 0x0000, 0x0000}));
        put(switchRate, new ReqModbus(switchRate, 0x10, 0x4743, new short[]{0x4743, 0x4040, (short) 0xBFBF, 0x0002, 0x0000, 0x0000}));
        put(clearRate,  new ReqModbus(clearRate, 0x10, 0x4743, new short[]{0x4743, 0x4141, (short) 0xBEBE, 0x0000}));*/
    }};

    public static ReqModbus getCmd(int funcCode){
        if (CMD.containsKey(funcCode)){
            return CMD.get(funcCode);
        }
        return CMD.get(readInfo);
    }

    /**
     * 应答状态值 含义
     * 0xFFFF 无命令
     * 0x0000 命令执行中
     * 0x1000 命令执行成功
     * 0x20xx 命令执行失败，xx 为失败标识
     *     xx=0x00：命令错误
     *     xx=0x01：命令参数错误
     *     xx=0x02：未打开写保护开关
     *     xx=0x03：未关闭写保护开关
     *     xx=0x04：重量不稳定
     *     xx=0x05：不符合操作条件
     *     xx=0x80：硬件故障
     *     xx=0x81：参数保存失败
     *
     */
     public static final int answerNULL = 0x1000;
     public static final int answering = 0x1000;
     public static final int answerOK = 0x1000;
     public static final int Error00 = 0x2000;
     public static final int Error01 = 0x2001;
     public static final int Error02 = 0x2002;
     public static final int Error03 = 0x2003;
     public static final int Error04 = 0x2004;
     public static final int Error05 = 0x2005;
     public static final int Error80 = 0x2080;
     public static final int Error81 = 0x2081;

}
