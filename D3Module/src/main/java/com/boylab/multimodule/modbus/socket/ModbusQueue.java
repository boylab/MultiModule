package com.boylab.multimodule.modbus.socket;

import android.util.Log;

import com.boylab.multimodule.modbus.bean.ListQueue;
import com.boylab.multimodule.modbus.bean.ReqModbus;
import com.boylab.multimodule.modbus.master.SerialMaster;
import com.serotonin.modbus4j.code.FunctionCode;
import com.serotonin.modbus4j.msg.ReadCoilsResponse;
import com.serotonin.modbus4j.msg.ReadHoldingRegistersResponse;
import com.serotonin.modbus4j.msg.ReadInputRegistersResponse;
import com.serotonin.modbus4j.msg.WriteCoilResponse;
import com.serotonin.modbus4j.msg.WriteRegistersResponse;

import java.util.concurrent.LinkedBlockingDeque;

/**
 * modbus队列、管理命令收发
 */
public class ModbusQueue extends AbsThread {
    private SerialMaster serialMaster;
    //private LinkedBlockingDeque<CellModule> mThreadQueue = new LinkedBlockingDeque<CellModule>();
    private ListQueue<CellModule> mThreadQueue = new ListQueue<>();
    private volatile CellModule cellThread = null;
    private volatile ReqModbus reqModbus = null;

    public ModbusQueue(SerialMaster serialMaster) {
        super();
        this.serialMaster = serialMaster;
    }

    public void addQueue(CellModule cellThread) {
        this.mThreadQueue.offer(cellThread);
    }

    public void remove(CellModule cellThread) {
        this.mThreadQueue.remove(cellThread);
    }

    @Override
    protected void beforeLoop() throws Exception {
        super.beforeLoop();
    }

    @Override
    protected void runInLoopThread(){
        try {
            cellThread = mThreadQueue.take();

            reqModbus = cellThread.takeCmd();

            int slaveId = cellThread.getSlaveId();
            int funcCode = reqModbus.getFuncCode();
                /*Log.i(">>>boylab>>>", "runInLoopThread: slaveId = "+slaveId);
                Log.i(">>>boylab>>>", "runInLoopThread: reqModbus.getSlaveId() = "+reqModbus.getSlaveId());*/
            if (funcCode == FunctionCode.READ_INPUT_REGISTERS){
                ReadInputRegistersResponse mResponse = serialMaster.readInputRegisters(reqModbus.getSlaveId(), reqModbus.getStart(), reqModbus.getNumber());
                if (mResponse !=null){
                    cellThread.onReadInputRegisters(slaveId, reqModbus.what(), mResponse);
                }
                 /*long delay = System.currentTimeMillis() - time;
                 Log.i("___boylab>>>___", "运行时长: delay = "+delay);*/
            }else if (funcCode == FunctionCode.READ_HOLDING_REGISTERS){
                ReadHoldingRegistersResponse mResponse = serialMaster.readHoldingRegisters(reqModbus.getSlaveId(), reqModbus.getStart(), reqModbus.getNumber());
                if (mResponse !=null){
                    cellThread.onReadHoldingRegisters(slaveId, reqModbus.what(), mResponse);
                }
            }else if (funcCode == FunctionCode.WRITE_REGISTERS){
                WriteRegistersResponse mResponse = serialMaster.writeHoldingRegisters(reqModbus.getSlaveId(), reqModbus.getStart(), reqModbus.getWriteValue());
                if (mResponse !=null){
                    cellThread.onWriteHoldingRegisters(slaveId, reqModbus.what(), mResponse);
                }
            }else if (funcCode == FunctionCode.READ_COILS){
                ReadCoilsResponse mResponse = serialMaster.readCoils(reqModbus.getSlaveId(), reqModbus.getStart(), reqModbus.getNumber());
                if (mResponse !=null){
                    cellThread.onReadCoils(slaveId, reqModbus.what(), mResponse);
                }
            }else if (funcCode == FunctionCode.WRITE_COIL){
                WriteCoilResponse mResponse = serialMaster.writeCoils(reqModbus.getSlaveId(), reqModbus.getStart(), reqModbus.getWriteBool());
                if (mResponse !=null){
                    cellThread.onWriteCoil(slaveId, reqModbus.what(), mResponse);
                }
            }/*else if (funcCode == FunctionCode.WRITE_COILS){
                    WriteCoilsResponse mResponse = serialMaster.writeCoils(reqModbus.getSlaveId(), reqModbus.getStart(), reqModbus.getWriteBools());
                    if (mResponse !=null){
                        cellThread.onWriteCoils(slaveId, reqModbus.what(), mResponse);
                    }
                }*/
        }catch (InterruptedException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
            Log.i(">>>>>", "runInLoopThread: modbus Exception");
        }finally {

        }
    }

    @Override
    protected void loopFinish(Exception e) {

    }

}
