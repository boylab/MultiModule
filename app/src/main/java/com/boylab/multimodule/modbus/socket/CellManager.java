package com.boylab.multimodule.modbus.socket;

import com.boylab.multimodule.modbus.master.SerialMaster;

/**
 *
 *
 *
 *
 *
 *
 *
 *
 */
public class CellManager {

    private volatile SerialMaster serialMaster = null;      //平板串口管理
    private ModbusQueue modbusQueue = null;

    private volatile CellHolding cellHolding = new CellHolding();      //串口通讯线程

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
        if (serialMaster == null && modbusQueue == null){
            /**
             * 启动串口
             */
            serialMaster = SerialMaster.newInstance();
            serialMaster.startSerial();

            /**
             * 启动底层modbus队列
             */
            modbusQueue = new ModbusQueue(serialMaster);
            modbusQueue.start();
        }
    }

    /**
     * 关闭模块通讯
     */
    public void stopEngine(){
        if (cellHolding != null){
            cellHolding.removeAll();
        }

        if (modbusQueue != null && !modbusQueue.isShutdown()){
            modbusQueue.shutdown();
            modbusQueue = null;
        }

        serialMaster = SerialMaster.newInstance();
        serialMaster.stopSerial();
    }

    /**
     * 创建模块通讯
     * @param slaveId
     */
    public void createCell(int slaveId){
        cellHolding.createCell(slaveId, modbusQueue);
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

    public void zero(int slaveId){
        CellThread cellThread = cellHolding.getCellThread(slaveId);
        if (cellThread != null && !cellThread.isShutdown()){
            cellThread.zeroAction();
        }
    }

    public void tare(int slaveId){
        CellThread cellThread = cellHolding.getCellThread(slaveId);
        if (cellThread != null && !cellThread.isShutdown()){
            cellThread.tareAction();
        }
    }

}
