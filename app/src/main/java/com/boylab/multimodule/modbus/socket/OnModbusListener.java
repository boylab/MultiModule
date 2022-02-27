package com.boylab.multimodule.modbus.socket;

import com.serotonin.modbus4j.msg.ReadCoilsResponse;
import com.serotonin.modbus4j.msg.ReadHoldingRegistersResponse;
import com.serotonin.modbus4j.msg.ReadInputRegistersResponse;
import com.serotonin.modbus4j.msg.WriteCoilResponse;
import com.serotonin.modbus4j.msg.WriteCoilsResponse;
import com.serotonin.modbus4j.msg.WriteRegisterResponse;
import com.serotonin.modbus4j.msg.WriteRegistersResponse;

interface OnModbusListener {

    void onReadCoils(int slaveId, int what, ReadCoilsResponse coilsResponse);
    void onReadHoldingRegisters(int slaveId, int what, ReadHoldingRegistersResponse holdingResponse);
    void onReadInputRegisters(int slaveId, int what, ReadInputRegistersResponse inputResponse);
    void onWriteCoil(int slaveId, int what, WriteCoilResponse writeCoilResponse);
    void onWriteHoldingRegister(int slaveId, int what, WriteRegisterResponse writeRegisterResponse);
    void onWriteCoils(int slaveId, int what, WriteCoilsResponse writeCoilsResponse);
    void onWriteHoldingRegisters(int slaveId, int what, WriteRegistersResponse writeRegistersResponse);

}
