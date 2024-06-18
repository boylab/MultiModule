package com.boylab.multimodule.modbus.bean;

/**
 * Created by pengle on 2020/06/10
 * Email: pengle609@163.com
 */
public class ShortOption {
    private int start;
    private short value;

    public ShortOption(int start) {
        this.start = start;
    }

    public ShortOption(int start, short value) {
        this.start = start;
        this.value = value;
    }

    public int start() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public short value() {
        return value;
    }

    public void setValue(short value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Option{" +
                "start=" + start +
                ", value=" + value +
                '}';
    }
}
