package com.boylab.multimodule.modbus.socket;

import android.serialport.Device;

import com.boylab.multimodule.modbus.bean.ReqModbus;
import com.boylab.multimodule.modbus.master.SerialMaster;

import java.util.ArrayList;
import java.util.List;

public class ModbusEngine {

    private List<Device> devices = new ArrayList<>();       //存放DS-D3数据
    private volatile SerialMaster serialMaster = null;      //平板串口管理
    private volatile QueueManager queueManager = null;      //串口通讯线程

    private static ModbusEngine instance = null;

    private ModbusEngine(){

    }

    /**
     * 单例模式
     * @return
     */
    public static ModbusEngine instance(){
        if (instance == null){
            instance = new ModbusEngine();
        }
        return instance;
    }

    public boolean isStart() {
        if (queueManager!=null){
            return !queueManager.isShutdown();
        }
        return false;
    }

    /**
     * 开启
     */
    public void startEngine(){
        if (isStart()){
            return;
        }
        /**
         * 启动串口
         */
        serialMaster = SerialMaster.newInstance();
        serialMaster.startSerial();

        /**
         * 启动队列线程
         */
        queueManager = new QueueManager();
        queueManager.start();
    }

    public void setOnModbusResponse(ModbusListener modbusListener) {
        if (queueManager != null){
            queueManager.setModbusListener(modbusListener);
        }
    }

    /**
     * 关闭队列
     */
    public void stopEngine(){
        if (queueManager != null){
            queueManager.shutdown();
            queueManager = null;
        }

        serialMaster = SerialMaster.newInstance();
        serialMaster.stopSerial();
    }
}
