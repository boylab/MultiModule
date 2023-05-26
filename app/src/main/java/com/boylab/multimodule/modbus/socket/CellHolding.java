package com.boylab.multimodule.modbus.socket;


import android.util.Log;

import com.boylab.multimodule.modbus.master.SerialMaster;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * 管理模块
 */
class CellHolding extends ModbusQueue{

    private HashMap<Integer, CellThread> mThreadHold = new HashMap<>();

    public CellHolding(SerialMaster serialMaster) {
        super(serialMaster);
    }

    public CellThread getCellThread(int slaveId) {
        CellThread cellThread = null;
        if (mThreadHold.containsKey(slaveId)) {
            cellThread = mThreadHold.get(slaveId);
        }
        return cellThread;
    }
    
    public void createCell(int slaveId){
        if (!mThreadHold.containsKey(slaveId)){
            CellThread cellThread = new CellThread(slaveId);
            cellThread.start();
            mThreadHold.put(slaveId, cellThread);

            /**
             * 添加到ModbusQueue，Modbus命令收发队列
             */
            addQueue(cellThread);
        }
    }

    public void removeCell(int slaveId){
        if (mThreadHold.containsKey(slaveId)){
            CellThread cellThread = mThreadHold.get(slaveId);
            if (!cellThread.isShutdown()){
                cellThread.shutdown();
            }
            mThreadHold.remove(slaveId);
        }else {
            Log.e(">>>>>", "createCell: 不存在此称重模块！");
        }
    }

    public void removeAll(){
        if (!mThreadHold.isEmpty()){
            Set<Integer> keySet = mThreadHold.keySet();
            List<Integer> keyList = new ArrayList<>(keySet);
            for (int i = 0; i < keyList.size(); i++) {
                int slaveId = keyList.get(i);
                CellThread cellThread = mThreadHold.get(slaveId);

                if (!cellThread.isShutdown()){
                    cellThread.shutdown();
                }
                mThreadHold.remove(slaveId);
            }
        }
    }










}
