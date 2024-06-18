package com.boylab.multimodule.modbus.socket;

import com.boylab.multimodule.modbus.bean.ReqModbus;
import com.boylab.multimodule.modbus.bean.ShortOption;

import java.util.HashMap;

public class Command {

    /**
     * 操作输入寄存器
     */
    public static final ReqModbus READ_INFO = new ReqModbus(0x04, 0x0000, 8);
    public static final ReqModbus READ_INFO2 = new ReqModbus(0x04, 0x0008, 35); //其他信息

    /**
     * 操作线圈
     */
    public static final ReqModbus TARE    = new ReqModbus(0x05, 0x0008, true);
    public static final ReqModbus ZERO    = new ReqModbus(0x05, 0x0009, true);
    public static final ReqModbus Lock    = new ReqModbus(0x05, 0x000A, true);
    public static final ReqModbus UnLock  = new ReqModbus(0x05, 0x000B, true);
    public static final ReqModbus Sum     = new ReqModbus(0x05, 0x000C, true);
    public static final ReqModbus UnSum   = new ReqModbus(0x05, 0x000D, true);

    /**
     * 操作保持寄存器
     */
    public static final ReqModbus ReadSys = new ReqModbus(0x03, 0x0030, 5);
    public static final ReqModbus WriteSys = new ReqModbus(0x0F, 0x0030, new short[]{});
    public static final ReqModbus ReadCalib = new ReqModbus(0x03, 0x0041, 25);
    public static final ReqModbus WriteCalib = new ReqModbus(0x0F, 0x0041, new short[]{});

    /*0x0101	置零命令
    0x0202	去皮命令
    0x0303	重量锁定命令
    0x0404	累计命令
    0x3030	零点标定
    0x3131	加载点标定
    0x3232	重力加速度修正
    0x3333	标定系数设定
    0x3434	用户参数写保护存储
    0x4040	标率切换
    0x4141	初始化当前标率*/
    public static final ReqModbus C_ZERO = new ReqModbus(0x0F, 0x1000, new short[]{0x4743, 0x0202, (short) 0xFDFD, 0x0001});
    public static final ReqModbus C_TARE = new ReqModbus(0x0F, 0x1000, new short[]{0x4743, 0x0202, (short) 0xFDFD, 0x0001});
    public static final ReqModbus C_LOCK = new ReqModbus(0x0F, 0x1000, new short[]{0x4743, 0x0202, (short) 0xFDFD, 0x0001});
    public static final ReqModbus C_Accrued = new ReqModbus(0x0F, 0x1000, new short[]{0x4743, 0x0202, (short) 0xFDFD, 0x0001});
    public static final ReqModbus C_CalibZero = new ReqModbus(0x0F, 0x1000, new short[]{0x4743, 0x0202, (short) 0xFDFD, 0x0001});
    public static final ReqModbus C_CalibWeigh = new ReqModbus(0x0F, 0x1000, new short[]{0x4743, 0x0202, (short) 0xFDFD, 0x0001});
    public static final ReqModbus C_GravityFixes = new ReqModbus(0x0F, 0x1000, new short[]{0x4743, 0x0202, (short) 0xFDFD, 0x0001});
    public static final ReqModbus C_CalibCoe= new ReqModbus(0x0F, 0x1000, new short[]{0x4743, 0x0202, (short) 0xFDFD, 0x0001});
    public static final ReqModbus C_WriteSafe= new ReqModbus(0x0F, 0x1000, new short[]{0x4743, 0x0202, (short) 0xFDFD, 0x0001});
    public static final ReqModbus C_CalibRate= new ReqModbus(0x0F, 0x1000, new short[]{0x4743, 0x0202, (short) 0xFDFD, 0x0001});
    public static final ReqModbus C_InitRate= new ReqModbus(0x0F, 0x1000, new short[]{0x4743, 0x0202, (short) 0xFDFD, 0x0001});


    public static final short MODE_JUDGE = 0x00;    //0x00：不判稳定
    public static final short MODE_UNJUDGE = 0x01;  //0x01：判稳定
    public static final short ZERO_SAVE = 0x00;     //0x00：保存关机零点
    public static final short ZERO_UNSAVE = 0x01;   //0x01：不保存关机零点

    public static final short WEIGH_LOCKBACK = 0x00;//0x00：锁定状态取反
    public static final short WEIGH_LOCK = 0x01;    //0x01：锁定
    public static final short WEIGH_UNLOCK = 0x02;  //0x02: 解除锁定

    public static final short WEIGH_SUM = 0x00;    //0x00：累计
    public static final short WEIGH_UNSUM = 0x01;  //0x01: 累清

    public static final short RATE_NEW = 0x00;  //0：使用新标率作为当前标率
    public static final short RATE_OLD = 0x01;  //1：当前标率不变（用于标率复制）

    public ReqModbus zeroCmd(short mode, short zero){
        ShortOption optionMode = new ShortOption(0x04 + 0x00, mode);
        ShortOption optionZero = new ShortOption(0x04 + 0x01, zero);
        return C_ZERO.modifyShort(optionMode, optionZero);
    }
    public ReqModbus tareCmd(short mode){
        ShortOption optionMode = new ShortOption(0x04 + 0x00, mode);
        return C_TARE.modifyShort(optionMode);
    }
    public ReqModbus lockCmd(short weigh){
        ShortOption optionWeigh = new ShortOption(0x04 + 0x00, weigh);
        return C_LOCK.modifyShort(optionWeigh);
    }
    public ReqModbus accruedCmd(short sum, short mode){
        ShortOption optionSum = new ShortOption(0x04 + 0x00, sum);
        ShortOption optionMode = new ShortOption(0x04 + 0x01, mode);
        return C_Accrued.modifyShort(optionSum, optionMode);
    }

    /**
     * 零点标定 - times
     * times = 0:实时响应
     * times>=1 && times<=100:采样times次取平均值
     *
     * @param times
     * @return
     */
    public ReqModbus calibZeroCmd(short times){
        ShortOption optionTimes = new ShortOption(0x04 + 0x00, times);
        return C_CalibZero.modifyShort(optionTimes);
    }

    /**
     * times同零点标定类似
     * @param weight
     * @param times
     * @return
     */
    public ReqModbus calibWeighCmd(int weight, short times){
        ShortOption weightHigh = new ShortOption(0x04 + 0x00, (short) ((weight>>16) & 0xFFFF));
        ShortOption weightLow = new ShortOption(0x04 + 0x01, (short) (weight & 0xFFFF));
        ShortOption optionTimes = new ShortOption(0x04 + 0x02, times);
        return C_CalibWeigh.modifyShort(weightHigh, weightLow, optionTimes);
    }

    /**
     * 重力加速度修正
     * 注：重力加速度值 = 重力加速度×1000000（即定点六位小数）
     * @param gravity 一般 g=9.807 m/s2
     * @return
     */
    public ReqModbus gravityFixesCmd(double gravity){
        long mGravity = (long) (gravity * 1000000);
        ShortOption gravityHigh = new ShortOption(0x04 + 0x00, (short) ((mGravity>>16) & 0xFFFF));
        ShortOption gravityLow = new ShortOption(0x04 + 0x01, (short) (mGravity & 0xFFFF));
        return C_GravityFixes.modifyShort(gravityHigh, gravityLow);
    }

    /**
     * 标定系数设定
     * 注：标定系数值 = 标定系数×1000000（即定点六位小数）
     * @param calibCoe
     * @return
     */
    public ReqModbus calibCoeCmd(double calibCoe){
        long mCalibCoe = (long) (calibCoe * 1000000);
        ShortOption calibCoeHigh = new ShortOption(0x04 + 0x00, (short) ((mCalibCoe>>16) & 0xFFFF));
        ShortOption calibCoeLow = new ShortOption(0x04 + 0x01, (short) (mCalibCoe & 0xFFFF));
        return C_CalibCoe.modifyShort(calibCoeHigh, calibCoeLow);
    }


    public ReqModbus calibRateCmd(short rateGroup, short rateMode){
        ShortOption optionGroup = new ShortOption(0x04 + 0x00, rateGroup);
        ShortOption optionMode = new ShortOption(0x04 + 0x01, rateMode);
        return C_CalibRate.modifyShort(optionGroup, optionMode);
    }

    /**
     * 应答状态值 含义
     *      0xFFFF 无命令
     *      0x0000 命令执行中
     *      0x1000 命令执行成功
     *      0x20xx 命令执行失败，xx 为失败标识
     *     xx=0x00：命令错误
     *     xx=0x01：命令参数错误
     *     xx=0x02：未打开写保护开关
     *     xx=0x03：未关闭写保护开关
     *     xx=0x04：重量不稳定
     *     xx=0x05：不符合操作条件
     *     xx=0x80：硬件故障
     *     xx=0x81：参数保存失败
     */
     public static final int CodeNULL = 0xFFFF;
     public static final int Codeing = 0x0000;
     public static final int CodeOK = 0x1000;
     public static final int Error00 = 0x2000;
     public static final int Error01 = 0x2001;
     public static final int Error02 = 0x2002;
     public static final int Error03 = 0x2003;
     public static final int Error04 = 0x2004;
     public static final int Error05 = 0x2005;
     public static final int Error80 = 0x2080;
     public static final int Error81 = 0x2081;
     private static final HashMap<Integer, String> AnswerCode = new HashMap<Integer, String>(){{
        put(CodeNULL, "无命令");
        put(Codeing, "命令执行中");
        put(CodeOK, "命令执行成功");
        put(Error00, "命令错误");
        put(Error01, "命令参数错误");
        put(Error02, "未打开写保护开关");
        put(Error03, "未关闭写保护开关");
        put(Error04, "重量不稳定");
        put(Error05, "不符合操作条件");
        put(Error80, "硬件故障");
        put(Error81, "参数保存失败");
     }};

     public String showAnswer(int code){
        if (AnswerCode.containsKey(code)){
            return AnswerCode.get(code);
        }
        return "未知code = "+code;
    }

}
