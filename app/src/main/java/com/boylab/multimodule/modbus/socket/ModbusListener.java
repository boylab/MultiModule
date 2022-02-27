package com.boylab.multimodule.modbus.socket;

import com.serotonin.modbus4j.msg.ModbusResponse;

public interface ModbusListener {

    void onResponse(int what, ModbusResponse response);
}
