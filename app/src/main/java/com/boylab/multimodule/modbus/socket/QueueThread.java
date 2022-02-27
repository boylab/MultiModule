package com.boylab.multimodule.modbus.socket;

import android.serialport.AbsLoopThread;

import com.boylab.multimodule.modbus.bean.ReqModbus;
import com.boylab.multimodule.modbus.master.SerialMaster;
import com.serotonin.modbus4j.code.FunctionCode;
import com.serotonin.modbus4j.msg.ModbusResponse;

import java.util.concurrent.LinkedBlockingDeque;

public class QueueThread extends AbsLoopThread {

    private LinkedBlockingDeque<ReqModbus> mQueue = new LinkedBlockingDeque<ReqModbus>();

    private SerialMaster serialMaster;
    private ModbusResponse mResponse;
    private ModbusListener modbusListener = null;

    public void setQueue(LinkedBlockingDeque<ReqModbus> mQueue) {
        this.mQueue = mQueue;
    }

    public void setModbusListener(ModbusListener modbusListener) {
        this.modbusListener = modbusListener;
    }

    @Override
    protected void beforeLoop() throws Exception {
        super.beforeLoop();
        serialMaster = SerialMaster.newInstance();
    }

    @Override
    protected void runInLoopThread() {
        ReqModbus reqModbus = null;
        try {
            reqModbus = mQueue.take();
            int funcCode = reqModbus.getFuncCode();
            if (funcCode == FunctionCode.READ_COILS){
                mResponse = serialMaster.readCoils(reqModbus.getSlaveId(), reqModbus.getStart(), reqModbus.getLen());
            }else if (funcCode == FunctionCode.READ_HOLDING_REGISTERS){
                mResponse = serialMaster.readHoldingRegisters(reqModbus.getSlaveId(), reqModbus.getStart(), reqModbus.getLen());
            }else if (funcCode == FunctionCode.READ_INPUT_REGISTERS){
                mResponse = serialMaster.readInputRegisters(reqModbus.getSlaveId(), reqModbus.getStart(), reqModbus.getLen());
            }else if (funcCode == FunctionCode.WRITE_COIL){
                mResponse = serialMaster.writeCoils(reqModbus.getSlaveId(), reqModbus.getStart(), reqModbus.isWriteBool());
            }else if (funcCode == FunctionCode.WRITE_REGISTER){
                mResponse = serialMaster.writeHoldingRegister(reqModbus.getSlaveId(), reqModbus.getStart(), reqModbus.getWriteShort());
            }else if (funcCode == FunctionCode.WRITE_COILS){
                mResponse = serialMaster.writeCoils(reqModbus.getSlaveId(), reqModbus.getStart(), reqModbus.getWriteBools());
            }else if (funcCode == FunctionCode.WRITE_REGISTERS){
                mResponse = serialMaster.writeHoldingRegisters(reqModbus.getSlaveId(), reqModbus.getStart(), reqModbus.getWriteValue());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (mResponse !=null && modbusListener != null){
            modbusListener.onResponse(reqModbus.what(), mResponse);
        }
    }

    @Override
    protected void loopFinish(Exception e) {

    }
}
