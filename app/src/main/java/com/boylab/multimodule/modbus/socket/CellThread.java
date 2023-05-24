package com.boylab.multimodule.modbus.socket;

import android.util.Log;

import com.boylab.multimodule.modbus.bean.ReqModbus;
import com.boylab.multimodule.modbus.data.WeightInfo;
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
public class CellThread extends AbsThread implements OnModbusListener {

    private int slaveId = 1;    //通讯地址
    private ModbusQueue modbusQueue = null;

    private WeightInfo weightInfo = new WeightInfo();
    private OnCellListener onCellListener = null;

    public CellThread(int slaveId, ModbusQueue modbusQueue) {
        super();
        this.slaveId = slaveId;
        this.modbusQueue = modbusQueue;
    }

    public WeightInfo getWeightInfo() {
        return weightInfo;
    }

    public void setOnCellListener(OnCellListener onCellListener) {
        this.onCellListener = onCellListener;
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

            Thread.sleep(160); //按波特率38400下，接三十模块的余量 10ms * 16 = 160ms
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
        onCellListener = null;
        //清楚modbus回调监听
        modbusQueue.removeOnModbusListener(slaveId);
    }


    @Override
    public void onReadCoils(int slaveId, int what, ReadCoilsResponse coilsResponse) {
        if (this.slaveId == slaveId && onCellListener != null){
            onCellListener.onCellAction(this.slaveId, what, coilsResponse);
        }
    }

    @Override
    public void onReadHoldingRegisters(int slaveId, int what, ReadHoldingRegistersResponse holdingResponse) {
        if (this.slaveId == slaveId && onCellListener != null){
            onCellListener.onCellAction(this.slaveId, what, holdingResponse);
        }
    }

    @Override
    public void onReadInputRegisters(int slaveId, int what, ReadInputRegistersResponse inputResponse) {
        //解析输入寄存器
        if (this.slaveId == slaveId && inputResponse.getExceptionCode() == -1){
            byte[] data = inputResponse.getData();
            weightInfo.toInfo(data);
            if (onCellListener != null){
                onCellListener.onCellFresh(this.slaveId, weightInfo);
            }
        }
    }

    @Override
    public void onWriteCoil(int slaveId, int what, WriteCoilResponse writeCoilResponse) {
        if (this.slaveId == slaveId && onCellListener != null){
            onCellListener.onCellAction(this.slaveId, what, writeCoilResponse);
        }
    }

    @Override
    public void onWriteHoldingRegister(int slaveId, int what, WriteRegisterResponse writeRegisterResponse) {
        if (this.slaveId == slaveId && onCellListener != null){
            onCellListener.onCellAction(this.slaveId, what, writeRegisterResponse);
        }
    }

    @Override
    public void onWriteCoils(int slaveId, int what, WriteCoilsResponse writeCoilsResponse) {
        if (this.slaveId == slaveId && onCellListener != null){
            onCellListener.onCellAction(this.slaveId, what, writeCoilsResponse);
        }
    }

    @Override
    public void onWriteHoldingRegisters(int slaveId, int what, WriteRegistersResponse writeRegistersResponse) {
        if (this.slaveId == slaveId && onCellListener != null){
            onCellListener.onCellAction(this.slaveId, what, writeRegistersResponse);
        }
    }
}
