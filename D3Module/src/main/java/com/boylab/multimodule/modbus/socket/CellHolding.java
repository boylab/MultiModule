package com.boylab.multimodule.modbus.socket;


import android.util.Log;

import com.boylab.multimodule.modbus.master.SerialMaster;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * 管理模块
 * 特别注意：
 * moduleId 是模块的标识，适用于区别不同模块而定义的，不是串口通讯地址
 */
class CellHolding extends ModbusQueue{

    private HashMap<Integer, CellModule> mThreadHold = new HashMap<>();

    public CellHolding(SerialMaster serialMaster) {
        super(serialMaster);
    }

    public CellModule getCellModule(int moduleId) {
        CellModule cellThread = null;
        if (mThreadHold.containsKey(moduleId)) {
            cellThread = mThreadHold.get(moduleId);
        }
        return cellThread;
    }
    
    public void createCell(int moduleId, int slaveId){
        if (!mThreadHold.containsKey(moduleId)){
            CellModule cellThread = new CellModule(moduleId, slaveId);
            mThreadHold.put(moduleId, cellThread);

            /**
             * 添加到ModbusQueue，Modbus命令收发队列
             */
            addQueue(cellThread);
        }
    }

    public void removeCell(int moduleId){
        if (mThreadHold.containsKey(moduleId)){
            CellModule cellThread = mThreadHold.get(moduleId);
            mThreadHold.remove(moduleId);

            remove(cellThread);
        }else {
            Log.e(">>>>>", "createCell: 不存在此称重模块！");
        }
    }

    public void removeAll(){
        if (!mThreadHold.isEmpty()){
            Set<Integer> keySet = mThreadHold.keySet();
            List<Integer> keyList = new ArrayList<>(keySet);
            for (int i = 0; i < keyList.size(); i++) {
                int moduleId = keyList.get(i);
                CellModule cellThread = mThreadHold.get(moduleId);

                mThreadHold.remove(moduleId);
                remove(cellThread);
            }
        }
    }
}
