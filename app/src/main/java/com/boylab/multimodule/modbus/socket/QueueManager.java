package com.boylab.multimodule.modbus.socket;

import android.serialport.AbsLoopThread;

import com.boylab.multimodule.modbus.bean.ReqModbus;

import java.util.concurrent.LinkedBlockingDeque;

/**
 * 管理modbus发送队列
 */
public class QueueManager extends AbsLoopThread {

    private LinkedBlockingDeque<ReqModbus> mQueue = new LinkedBlockingDeque<ReqModbus>();

    private volatile QueueThread queueThread;

    public void setModbusListener(ModbusListener modbusListener) {
        queueThread.setModbusListener(modbusListener);
    }

    @Override
    protected void beforeLoop() throws Exception {
        super.beforeLoop();
        queueThread = new QueueThread();
        queueThread.setQueue(mQueue);
        queueThread.start();
    }

    @Override
    protected void runInLoopThread() throws Exception {
        /**
         * 100毫秒运行一次
         */
        if (mQueue.isEmpty()){



            ReqModbus reqModbus = Command.getCmd(Command.readInfo);
            mQueue.add(reqModbus);
        }
    }

    @Override
    protected void loopFinish(Exception e) {
        queueThread.shutdown();
        queueThread = null;
    }


}
