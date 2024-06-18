package com.boylab.multimodule.bean;

public class ViewModule {

    private int moduleId = 1;
    private int slaveId = 1;

    public ViewModule(int moduleId, int slaveId) {
        this.moduleId = moduleId;
        this.slaveId = slaveId;
    }

    public int getModuleId() {
        return moduleId;
    }

    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

    public int getSlaveId() {
        return slaveId;
    }

    public void setSlaveId(int slaveId) {
        this.slaveId = slaveId;
    }

    @Override
    public String toString() {
        return "ViewModule{" +
                "moduleId=" + moduleId +
                ", slaveId=" + slaveId +
                '}';
    }
}
