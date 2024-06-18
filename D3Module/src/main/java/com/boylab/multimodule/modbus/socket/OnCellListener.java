package com.boylab.multimodule.modbus.socket;

import com.boylab.multimodule.modbus.data.WeightInfo;
import com.serotonin.modbus4j.msg.ModbusResponse;

public interface OnCellListener {

    void onCellFresh(int moduleId, int slaveId, WeightInfo weightInfo);

    void onCellAction(int moduleId, int slaveId, int what, ModbusResponse modbusResponse);

}
