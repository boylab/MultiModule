package com.boylab.multimodule.bean;

import java.util.Arrays;

public class ParaItem {

    private String label;
    private int value;

    private boolean isSelect;
    private String[] list;

    public ParaItem(String text) {
        this.label = text;
        this.isSelect = false;
    }

    public ParaItem(String text, String[] list) {
        this.label = text;
        this.isSelect = true;
        this.list = list;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String[] getList() {
        return list;
    }

    public void setList(String[] list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "ParaBean{" +
                "label='" + label + '\'' +
                ", value=" + value +
                ", isSelect=" + isSelect +
                ", list=" + Arrays.toString(list) +
                '}';
    }
}
