package com.boylab.multimodule.modbus.master;

import android.serialport.SerialBean;
import android.serialport.SerialPortManager;

import com.serotonin.modbus4j.serial.SerialPortWrapper;

import java.io.InputStream;
import java.io.OutputStream;

public class SerialWrapper implements SerialPortWrapper {

    private SerialBean serialBean;
    private SerialPortManager serialPortManager;

    public SerialPortManager getSerialPortManager() {
        return serialPortManager;
    }

    @Override
    public void close() {
        if (serialPortManager != null){
            serialPortManager.close();
            serialPortManager = null;
        }
    }

    /**
     * 不需要主动调用，modbus系统调用
     */
    @Override
    public void open(){
        if(serialPortManager!= null) {
            serialPortManager.close();
        }
        serialBean = new SerialBean("/dev/ttyMT1", 38400, 0);
        serialPortManager = new SerialPortManager(serialBean, false);   //不开启读线程，交给modbus
    }

    @Override
    public InputStream getInputStream() {
        return serialPortManager.getInputStream();
    }

    @Override
    public OutputStream getOutputStream() {
        return serialPortManager.getOutputStream();
    }

    @Override
    public int getBaudRate() {
        return serialBean.getBaudrate();
    }

    @Override
    public int getDataBits() {
        return serialBean.getDataBits();
    }

    @Override
    public int getStopBits() {
        return serialBean.getStopBits();
    }

    @Override
    public int getParity() {
        return serialBean.getParity();
    }
}
