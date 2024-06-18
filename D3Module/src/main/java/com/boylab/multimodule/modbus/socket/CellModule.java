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

/**
 * 每个模块命令生产者
 */
public class CellModule implements OnModbusListener {

    private int moduleId;       //模块的标志
    private int slaveId = 1;    //通讯地址

    private List<ReqModbus> mReqCmd = new ArrayList<>();

    private OnCellListener onCellListener = null;
    private volatile WeightInfo weightInfo = new WeightInfo();

    public CellModule(int moduleId, int slaveId) {
        super();
        this.moduleId = moduleId;
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
    public void onReadCoils(int slaveId, int what, ReadCoilsResponse coilsResponse) {
        if (this.slaveId == slaveId && onCellListener != null){
            onCellListener.onCellAction(this.moduleId, this.slaveId, what, coilsResponse);
        }
    }

    @Override
    public void onReadHoldingRegisters(int slaveId, int what, ReadHoldingRegistersResponse holdingResponse) {
        if (this.slaveId == slaveId && onCellListener != null){
            onCellListener.onCellAction(this.moduleId, this.slaveId, what, holdingResponse);
        }
    }

    @Override
    public void onReadInputRegisters(int slaveId, int what, ReadInputRegistersResponse inputResponse) {
        //解析输入寄存器
        if (this.slaveId == slaveId && inputResponse.getExceptionCode() == -1){
            byte[] data = inputResponse.getData();
            weightInfo.toInfo(data);
            if (onCellListener != null){
                onCellListener.onCellFresh(this.moduleId, this.slaveId, weightInfo);
            }
        }
    }

    @Override
    public void onWriteCoil(int slaveId, int what, WriteCoilResponse writeCoilResponse) {
        if (this.slaveId == slaveId && onCellListener != null){
            onCellListener.onCellAction(this.moduleId, this.slaveId, what, writeCoilResponse);
        }
    }

    @Override
    public void onWriteHoldingRegister(int slaveId, int what, WriteRegisterResponse writeRegisterResponse) {
        if (this.slaveId == slaveId && onCellListener != null){
            onCellListener.onCellAction(this.moduleId, this.slaveId, what, writeRegisterResponse);
        }
    }

    @Override
    public void onWriteCoils(int slaveId, int what, WriteCoilsResponse writeCoilsResponse) {
        if (this.slaveId == slaveId && onCellListener != null){
            onCellListener.onCellAction(this.moduleId, this.slaveId, what, writeCoilsResponse);
        }
    }

    @Override
    public void onWriteHoldingRegisters(int slaveId, int what, WriteRegistersResponse writeRegistersResponse) {
        if (this.slaveId == slaveId && onCellListener != null){
            onCellListener.onCellAction(this.moduleId, this.slaveId, what, writeRegistersResponse);
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
        if (mReqCmd.isEmpty()){
            ReqModbus cmd = Command.READ_INFO.setSlaveId(this.slaveId);
            return cmd;
        }else {
            ReqModbus reqModbus = mReqCmd.get(0);
            mReqCmd.remove(0);  //移除
            return reqModbus;
        }
    }

    /**
     * 不再需要增加延时
     */
    /*private void needDelay(){
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
    }*/
}
