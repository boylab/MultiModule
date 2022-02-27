package com.boylab.multimodule.modbus.socket;

import android.serialport.AbsLoopThread;
import android.util.Log;

import com.boylab.multimodule.modbus.bean.ReqModbus;
import com.serotonin.modbus4j.msg.ModbusResponse;
import com.serotonin.modbus4j.msg.ReadCoilsResponse;
import com.serotonin.modbus4j.msg.ReadHoldingRegistersResponse;
import com.serotonin.modbus4j.msg.ReadInputRegistersResponse;
import com.serotonin.modbus4j.msg.WriteCoilResponse;
import com.serotonin.modbus4j.msg.WriteCoilsResponse;
import com.serotonin.modbus4j.msg.WriteRegisterResponse;
import com.serotonin.modbus4j.msg.WriteRegistersResponse;

/**
 * 每个模块通讯的线程
 * 持有ModbusQueue线程对象
 */
public class CellThread extends AbsLoopThread implements OnModbusListener {

    private int slaveId = 1;    //通讯地址
    private ModbusQueue modbusQueue = null;

    public CellThread(int slaveId, ModbusQueue modbusQueue) {
        super();
        this.slaveId = slaveId;
        this.modbusQueue = modbusQueue;
    }

    /**
     * 去皮
     */
    public void zeroAction(){
        ReqModbus cmd = Command.getCmd(Command.ZERO);
        cmd.setSlaveId(slaveId);
        modbusQueue.addQueue(cmd);
    }

    /**
     * 置零
     */
    public void tareAction(){
        ReqModbus cmd = Command.getCmd(Command.TARE);
        cmd.setSlaveId(slaveId);
        modbusQueue.addQueue(cmd);
    }

    @Override
    protected void beforeLoop() throws Exception {
        super.beforeLoop();
        //设置modbus回调监听
        modbusQueue.setOnModbusListener(slaveId, this);
    }

    @Override
    protected void runInLoopThread() {
        try {
            ReqModbus cmd = Command.getCmd(Command.readInfo);
            cmd.setSlaveId(slaveId);
            modbusQueue.addQueue(cmd);

            Thread.sleep(100);
        }catch (Exception e){
            e.printStackTrace();
            Log.i(">>>>>", "runInLoopThread: CellThread Exception address = "+slaveId);
        }
    }

    @Override
    protected void loopFinish(Exception e) {

    }

    @Override
    public synchronized void shutdown() {
        super.shutdown();
        //清楚modbus回调监听
        modbusQueue.removeOnModbusListener(slaveId);
    }


    @Override
    public void onReadCoils(int slaveId, int what, ReadCoilsResponse coilsResponse) {

    }

    @Override
    public void onReadHoldingRegisters(int slaveId, int what, ReadHoldingRegistersResponse holdingResponse) {

    }

    @Override
    public void onReadInputRegisters(int slaveId, int what, ReadInputRegistersResponse inputResponse) {
        //解析输入寄存器



    }

    @Override
    public void onWriteCoil(int slaveId, int what, WriteCoilResponse writeCoilResponse) {

    }

    @Override
    public void onWriteHoldingRegister(int slaveId, int what, WriteRegisterResponse writeRegisterResponse) {

    }

    @Override
    public void onWriteCoils(int slaveId, int what, WriteCoilsResponse writeCoilsResponse) {

    }

    @Override
    public void onWriteHoldingRegisters(int slaveId, int what, WriteRegistersResponse writeRegistersResponse) {

    }
}
