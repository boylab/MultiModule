package com.boylab.multimodule.modbus.socket;

import com.boylab.multimodule.modbus.bean.ReqModbus;
import com.boylab.multimodule.modbus.data.WeightInfo;
import com.boylab.multimodule.modbus.master.SerialMaster;

/**
 *
 */
public class CellManager {

    private volatile SerialMaster serialMaster = null;      //平板串口管理
    private volatile CellHolding cellHolding = null;      //串口通讯线程

    private static CellManager instance = null;
    private CellManager(){
    }

    /**
     * 单例模式
     * @return
     */
    public static CellManager instance(){
        if (instance == null){
            instance = new CellManager();
        }
        return instance;
    }

    /**
     * 开启模块通讯
     */
    public void startEngine(){
        if (serialMaster == null && cellHolding == null){
            /**
             * 启动串口
             */
            serialMaster = SerialMaster.newInstance();
            serialMaster.startSerial();

            /**
             * 启动底层modbus队列
             */
            cellHolding = new CellHolding(serialMaster);
            cellHolding.start();
        }
    }

    /**
     * 关闭模块通讯
     */
    public void stopEngine(){
        if (cellHolding != null){
            cellHolding.removeAll();
        }

        if (cellHolding != null && !cellHolding.isShutdown()){
            cellHolding.shutdown();
            cellHolding = null;
        }

        serialMaster = SerialMaster.newInstance();
        serialMaster.stopSerial();
    }

    /**
     * 创建模块通讯
     * @param slaveId
     */
    public void createCell(int slaveId){
        cellHolding.createCell(slaveId);
    }

    /**
     * 移除指定模块
     * @param slaveId
     */
    public void removeCell(int slaveId){
        cellHolding.removeCell(slaveId);
    }

    /**
     * 移除指定模块
     */
    public void removeAll(){
        cellHolding.removeAll();
    }

    /**
     * 对指定模块进行监听
     * @param slaveId
     * @param onCellListener
     */
    public void setOnCellListener(int slaveId, OnCellListener onCellListener){
        CellThread cellThread = cellHolding.getCellThread(slaveId);
        if (cellThread != null && !cellThread.isShutdown()){
            cellThread.setOnCellListener(onCellListener);
        }
    }

    /**
     * 获取置零模块的称重数据
     * @param slaveId
     */
    public WeightInfo getWeightInfo(int slaveId){
        WeightInfo weightInfo = null;
        CellThread cellThread = cellHolding.getCellThread(slaveId);
        if (cellThread != null && !cellThread.isShutdown()){
            weightInfo = cellThread.getWeightInfo();
        }
        return weightInfo;
    }

    /**
     * 置零指令
     */
    public void zero(int slaveId){
        CellThread cellThread = cellHolding.getCellThread(slaveId);
        if (cellThread != null && !cellThread.isShutdown()){
            ReqModbus cmd = Command.getCmd(Command.ZERO);
            cmd.setSlaveId(slaveId);
            cellThread.sendCmd(cmd);
        }
    }

    /**
     * 去皮指令
     */
    public void tare(int slaveId){
        CellThread cellThread = cellHolding.getCellThread(slaveId);
        if (cellThread != null && !cellThread.isShutdown()){
            ReqModbus cmd = Command.getCmd(Command.TARE);
            cmd.setSlaveId(slaveId);
            cellThread.sendCmd(cmd);
        }
    }

    public void sendCmd(int slaveId, ReqModbus cmd){
        CellThread cellThread = cellHolding.getCellThread(slaveId);
        if (cellThread != null && !cellThread.isShutdown()){
            cellThread.sendCmd(cmd);
        }
    }

}
