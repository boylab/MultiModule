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
    public void createCell(int moduleId, int slaveId){
        cellHolding.createCell(moduleId, slaveId);
    }

    /**
     * 移除指定模块
     * @param moduleId
     */
    public void removeCell(int moduleId){
        cellHolding.removeCell(moduleId);
    }

    /**
     * 移除指定模块
     */
    public void removeAll(){
        cellHolding.removeAll();
    }

    /**
     * 对指定模块进行监听
     * @param moduleId
     * @param onCellListener
     */
    public void setOnCellListener(int moduleId, OnCellListener onCellListener){
        if (cellHolding == null){
            return;
        }
        CellModule cellModule = cellHolding.getCellModule(moduleId);
        if (cellModule != null){
            cellModule.setOnCellListener(onCellListener);
        }
    }

    /**
     * 获取置零模块的称重数据
     * @param moduleId
     */
    public WeightInfo getWeightInfo(int moduleId){
        WeightInfo weightInfo = null;
        CellModule cellModule = cellHolding.getCellModule(moduleId);
        if (cellModule != null){
            weightInfo = cellModule.getWeightInfo();
        }
        return weightInfo;
    }

    public void sendCmd(int moduleId, ReqModbus cmd){
        CellModule cellModule = cellHolding.getCellModule(moduleId);
        if (cellModule != null){
            cellModule.sendCmd(cmd);
        }
    }

}
