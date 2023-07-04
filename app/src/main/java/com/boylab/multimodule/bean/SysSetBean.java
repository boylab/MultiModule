package com.boylab.multimodule.bean;

import com.gotokeep.keep.taira.Taira;
import com.gotokeep.keep.taira.TairaData;
import com.gotokeep.keep.taira.annotation.ParamField;

import java.util.ArrayList;
import java.util.List;

/* 48 0x0030 1 通讯模式
 * 49 0x0031 1 波特率
 * 50 0x0032 1 校验方式
 * 51 0x0033 1 通讯地址
 * 52 0x0034 1 通讯发送延时
 * */
public class SysSetBean extends BasePara<SysSetBean> implements TairaData {

    public static final int MIN_REQUIRES = 5*2;

    @ParamField(order = 0,bytes = 2)
    private int commMode;

    @ParamField(order = 1,bytes = 2)
    private int baud;

    @ParamField(order = 2,bytes = 2)
    private int parity;

    @ParamField(order = 3,bytes = 2)
    private int addr;

    @ParamField(order = 4,bytes = 2)
    private int delay;

    @Override
    public void toPara(byte[] data) {
        if (data != null & data.length >= MIN_REQUIRES){
            SysSetBean sysSetBean = Taira.DEFAULT.fromBytes(data, SysSetBean.class);
            setReaded(sysSetBean != null);
            if (sysSetBean != null){
                freshPara(sysSetBean);
                //Log.i("___boylab>>>___", "toPara: calibPara = "+calibPara.toString());
            }
        }
    }

    @Override
    public void freshPara(SysSetBean sysSetBean) {
        setCommMode(sysSetBean.getCommMode());
        setBaud(sysSetBean.getBaud());
        setParity(sysSetBean.getParity());
        setAddr(sysSetBean.getAddr());
        setDelay(sysSetBean.getDelay());
    }

    /**
     * 转成参数，进行设置
     * @return
     */
    public short[] toSysSetShort() {
        short[] para = new short[]{
                (short) commMode,
                (short) baud,
                (short) parity,
                (short) addr,
                (short) delay
        };
        return para;
    }

    /**
     * 用于页面显示
     * @return
     */
    public List<Integer> sysSetList(){
        return new ArrayList<Integer>(){{
            add(0, commMode);
            add(1, baud);
            add(2, parity);
            add(3, addr);
            add(4, delay);
        }};
    }

    /**
     * 页面修改后进行设置
     * @param sysSetList
     */
    public void setSysSetList(final List<Integer> sysSetList){
        setCommMode(sysSetList.get(0));
        setBaud(sysSetList.get(1));
        setParity(sysSetList.get(2));
        setAddr(sysSetList.get(3));
        setDelay(sysSetList.get(4));
    }

    public int getCommMode() {
        return commMode;
    }

    public void setCommMode(int commMode) {
        this.commMode = commMode;
    }

    public int getBaud() {
        return baud;
    }

    public void setBaud(int baud) {
        this.baud = baud;
    }

    public int getParity() {
        return parity;
    }

    public void setParity(int parity) {
        this.parity = parity;
    }

    public int getAddr() {
        return addr;
    }

    public void setAddr(int addr) {
        this.addr = addr;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    @Override
    public String toString() {
        return "SysSetBean{" +
                "commMode=" + commMode +
                ", baud=" + baud +
                ", parity=" + parity +
                ", addr=" + addr +
                ", delay=" + delay +
                '}';
    }
}
