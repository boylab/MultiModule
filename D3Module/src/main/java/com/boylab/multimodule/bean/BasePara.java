package com.boylab.multimodule.bean;

import com.serotonin.modbus4j.base.ModbusUtils;

import java.util.List;

/**
 * 参数读取父类
 * @param <T>
 */
public abstract class BasePara<T> {

    private boolean isReaded = false;

    public boolean isReaded() {
        return isReaded;
    }

    public void setReaded(boolean readed) {
        isReaded = readed;
    }

    /**
     * 字节转对象
     * @param data
     */
    public abstract void toPara(byte[] data);

    /**
     * 刷新单列对象
     * @param t
     */
    public abstract void freshPara(T t);

    /**
     * list集合赋值
     * @param src
     * @param dest
     */
    protected void arrayCopy(List src, List dest){
        src.clear();
        if (dest != null && !dest.isEmpty()){
            for (int i = 0; i < dest.size(); i++) {
                src.add(i, dest.get(i));
            }
        }
    }

    protected short[] convertToShorts(byte[] data) {
        short[] sdata = new short[data.length / 2];
        for (int i = 0; i < sdata.length; i++)
            sdata[i] = ModbusUtils.toShort(data[i * 2], data[i * 2 + 1]);
        return sdata;
    }
}

