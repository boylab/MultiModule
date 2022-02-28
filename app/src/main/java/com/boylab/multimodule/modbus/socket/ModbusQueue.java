package com.boylab.multimodule.modbus.socket;

import android.serialport.AbsLoopThread;
import android.util.Log;

import com.boylab.multimodule.modbus.bean.ReqModbus;
import com.boylab.multimodule.modbus.master.SerialMaster;
import com.serotonin.modbus4j.code.FunctionCode;
import com.serotonin.modbus4j.msg.ModbusResponse;
import com.serotonin.modbus4j.msg.ReadCoilsResponse;
import com.serotonin.modbus4j.msg.ReadHoldingRegistersResponse;
import com.serotonin.modbus4j.msg.ReadInputRegistersResponse;
import com.serotonin.modbus4j.msg.WriteCoilResponse;
import com.serotonin.modbus4j.msg.WriteCoilsResponse;
import com.serotonin.modbus4j.msg.WriteRegisterResponse;
import com.serotonin.modbus4j.msg.WriteRegistersResponse;

import java.util.HashMap;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * modbus队列、管理命令收发
 */
public class ModbusQueue extends AbsThread {

    private SerialMaster serialMaster;
    private LinkedBlockingDeque<ReqModbus> mQueue = new LinkedBlockingDeque<ReqModbus>();

    //private ModbusResponse mResponse;
    private HashMap<Integer, OnModbusListener> mOnModbusListener = new HashMap<>();

    public ModbusQueue(SerialMaster serialMaster) {
        super();
        this.serialMaster = serialMaster;
        this.mQueue = mQueue;
    }

    public void addQueue(ReqModbus reqModbus) {
        this.mQueue.offer(reqModbus);
    }

    public void setOnModbusListener(int slaveId,OnModbusListener onModbusListener) {
        mOnModbusListener.put(slaveId, onModbusListener);
    }

    public void removeOnModbusListener(int slaveId) {
        if (mOnModbusListener.containsKey(slaveId)){
            mOnModbusListener.remove(slaveId);
        }
    }

    @Override
    protected void beforeLoop() throws Exception {
        super.beforeLoop();
    }

    @Override
    protected void runInLoopThread(){
        try {
            ReqModbus reqModbus = null;
            reqModbus = mQueue.take();
            int funcCode = reqModbus.getFuncCode();
            int slaveId = reqModbus.getSlaveId();
            OnModbusListener onModbusListener = mOnModbusListener.get(slaveId);

            if (funcCode == FunctionCode.READ_COILS){
                ReadCoilsResponse mResponse = serialMaster.readCoils(reqModbus.getSlaveId(), reqModbus.getStart(), reqModbus.getLen());
                if (mResponse !=null && onModbusListener != null){
                    onModbusListener.onReadCoils(slaveId, reqModbus.what(), mResponse);
                }
            }else if (funcCode == FunctionCode.READ_HOLDING_REGISTERS){
                ReadHoldingRegistersResponse mResponse = serialMaster.readHoldingRegisters(reqModbus.getSlaveId(), reqModbus.getStart(), reqModbus.getLen());
                if (mResponse !=null && onModbusListener != null){
                    onModbusListener.onReadHoldingRegisters(slaveId, reqModbus.what(), mResponse);
                }
            }else if (funcCode == FunctionCode.READ_INPUT_REGISTERS){
                ReadInputRegistersResponse mResponse = serialMaster.readInputRegisters(reqModbus.getSlaveId(), reqModbus.getStart(), reqModbus.getLen());
                if (mResponse !=null && onModbusListener != null){
                    onModbusListener.onReadInputRegisters(slaveId, reqModbus.what(), mResponse);
                }
            }else if (funcCode == FunctionCode.WRITE_COIL){
                WriteCoilResponse mResponse = serialMaster.writeCoils(reqModbus.getSlaveId(), reqModbus.getStart(), reqModbus.isWriteBool());
                if (mResponse !=null && onModbusListener != null){
                    onModbusListener.onWriteCoil(slaveId, reqModbus.what(), mResponse);
                }
            }else if (funcCode == FunctionCode.WRITE_REGISTER){
                WriteRegisterResponse mResponse = serialMaster.writeHoldingRegister(reqModbus.getSlaveId(), reqModbus.getStart(), reqModbus.getWriteShort());
                if (mResponse !=null && onModbusListener != null){
                    onModbusListener.onWriteHoldingRegister(slaveId, reqModbus.what(), mResponse);
                }
            }else if (funcCode == FunctionCode.WRITE_COILS){
                WriteCoilsResponse mResponse = serialMaster.writeCoils(reqModbus.getSlaveId(), reqModbus.getStart(), reqModbus.getWriteBools());
                if (mResponse !=null && onModbusListener != null){
                    onModbusListener.onWriteCoils(slaveId, reqModbus.what(), mResponse);
                }
            }else if (funcCode == FunctionCode.WRITE_REGISTERS){
                WriteRegistersResponse mResponse = serialMaster.writeHoldingRegisters(reqModbus.getSlaveId(), reqModbus.getStart(), reqModbus.getWriteValue());
                if (mResponse !=null && onModbusListener != null){
                    onModbusListener.onWriteHoldingRegisters(slaveId, reqModbus.what(), mResponse);
                }
            }
        }catch (InterruptedException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
            Log.i(">>>>>", "runInLoopThread: modbus Exception");
        }
    }

    @Override
    protected void loopFinish(Exception e) {

    }
}
