package com.boylab.multimodule.bean;

import java.util.Arrays;

public class ParaItem {

    private String label;
    private int value;
    private boolean canEdit;

    private boolean isSelect;
    private String[] list;

    public ParaItem(String text, boolean canEdit) {
        this.label = text;
        this.canEdit = canEdit;
        this.isSelect = false;
    }

    public ParaItem(String text, String[] list) {
        this.label = text;
        this.list = list;

        this.isSelect = true;
        this.canEdit = true;
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

    public boolean isCanEdit() {
        return canEdit;
    }

    public void setCanEdit(boolean canEdit) {
        this.canEdit = canEdit;
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
