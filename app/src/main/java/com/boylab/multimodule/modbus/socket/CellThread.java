package com.boylab.multimodule.modbus.socket;

import com.boylab.multimodule.modbus.bean.ReqModbus;
import com.boylab.multimodule.modbus.data.WeightInfo;
import com.serotonin.modbus4j.msg.ReadCoilsResponse;
import com.serotonin.modbus4j.msg.ReadHoldingRegistersResponse;
import com.serotonin.modbus4j.msg.ReadInputRegistersResponse;
import com.serotonin.modbus4j.msg.WriteCoilResponse;
import com.serotonin.modbus4j.msg.WriteCoilsResponse;
import com.serotonin.modbus4j.msg.WriteRegisterResponse;
import com.serotonin.modbus4j.msg.WriteRegistersResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * 每个模块通讯的线程
 * 持有ModbusQueue线程对象
 */
public class CellThread extends AbsThread implements OnModbusListener {

    private int slaveId = 1;    //通讯地址
    //private LinkedBlockingDeque<ReqModbus> mQueue = new LinkedBlockingDeque<ReqModbus>();

    private List<ReqModbus> mReqCmd = new ArrayList<>();
    private long timeMills = 0;
    private final long CMD_DELAY = 50;

    private WeightInfo weightInfo = new WeightInfo();
    private OnCellListener onCellListener = null;

    public CellThread(int slaveId) {
        super();
        this.slaveId = slaveId;
    }

    public int getSlaveId() {
        return slaveId;
    }

    public WeightInfo getWeightInfo() {
        return weightInfo;
    }

    public void setOnCellListener(OnCellListener onCellListener) {
        this.onCellListener = onCellListener;
    }

    @Override
    protected void beforeLoop() throws Exception {
        super.beforeLoop();
    }

    @Override
    protected void runInLoopThread() {
        try {
            /*if (mReqCmd.isEmpty()){
                ReqModbus cmd = Command.getCmd(Command.readInfo);
                cmd.setSlaveId(slaveId);
                mReqCmd.add(cmd);
            }*/
            //Thread.sleep(500);   //添加最低硬延时，防止刷新过快
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void loopFinish(Exception e) {

    }

    @Override
    public synchronized void shutdown() {
        super.shutdown();
        onCellListener = null;
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

    public void sendCmd(ReqModbus cmd){
        mReqCmd.add(cmd);
    }

    /**
     * 从队列中取指令
     * @return
     */
    public ReqModbus takeCmd(){
        needDelay();
        if (!mReqCmd.isEmpty()){
            ReqModbus reqModbus = mReqCmd.get(0);
            mReqCmd.remove(0);  //移除
            return reqModbus;
        }else {
            ReqModbus cmd = Command.getCmd(Command.readInfo);
            cmd.setSlaveId(this.slaveId);
            return cmd;
        }
    }

    private void needDelay(){
        if (timeMills != 0){
            long delay = CMD_DELAY - (System.currentTimeMillis() - timeMills);
            if (delay > 10){
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        timeMills = System.currentTimeMillis();
    }

}
