package com.boylab.multimodule.modbus.socket;

import android.serialport.AbsLoopThread;
import android.serialport.Device;
import android.util.Log;
import android.widget.Toast;

import com.boylab.multimodule.modbus.bean.ReqModbus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * 管理模块
 */
class CellHolding {

    private HashMap<Integer, CellThread> mSlaveThread = new HashMap<>();

    public CellThread getCellThread(int slaveId) {
        CellThread cellThread = null;
        if (mSlaveThread.containsKey(slaveId)) {
            cellThread = mSlaveThread.get(slaveId);
        }
        return cellThread;
    }

    /*public List<Integer> d3List(){
        Set<Integer> keySet = mSlaveThread.keySet();
        List<Integer> slaveList = new ArrayList<>(keySet);
        return slaveList;
    }*/

    public void createCell(int slaveId, ModbusQueue modbusQueue){
        if (mSlaveThread.containsKey(slaveId)){
            Log.e(">>>>>", "createCell: 不能重复添加称重模块！");
        }else {
            CellThread cellThread = new CellThread(slaveId, modbusQueue);
            cellThread.start();
            mSlaveThread.put(slaveId, cellThread);
        }
    }

    public void removeCell(int slaveId){
        if (mSlaveThread.containsKey(slaveId)){
            CellThread cellThread = mSlaveThread.get(slaveId);
            if (!cellThread.isShutdown()){
                cellThread.shutdown();
            }
            mSlaveThread.remove(slaveId);
        }else {
            Log.e(">>>>>", "createCell: 不存在此称重模块！");
        }
    }

    public void removeAll(){
        if (!mSlaveThread.isEmpty()){
            Set<Integer> keySet = mSlaveThread.keySet();
            List<Integer> keyList = new ArrayList<>(keySet);
            for (int i = 0; i < keyList.size(); i++) {
                int slaveId = keyList.get(i);
                CellThread cellThread = mSlaveThread.get(slaveId);

                if (!cellThread.isShutdown()){
                    cellThread.shutdown();
                }
                mSlaveThread.remove(slaveId);
            }
        }
    }










}
