package com.boylab.multimodule.modbus.master;

import android.serialport.Device;
import android.serialport.SerialPortManager;

import com.serotonin.modbus4j.serial.SerialPortWrapper;

import java.io.InputStream;
import java.io.OutputStream;

public class SerialWrapper implements SerialPortWrapper {

    private Device device;
    private SerialPortManager serialPortManager;

    public SerialPortManager getSerialPortManager() {
        return serialPortManager;
    }

    @Override
    public void close() {
        if (serialPortManager != null){
            serialPortManager.closeSerialPort();
            serialPortManager = null;
        }
    }

    /**
     * 不需要主动调用，modbus系统调用
     */
    @Override
    public void open(){
        if(serialPortManager!= null) {
            serialPortManager.closeSerialPort();
        }
        device= new Device("/dev/ttyMT1");
        device.setSpeed(38400);
        device.setParity('n');
        serialPortManager = new SerialPortManager(device, false);   //不开启读线程，交给modbus
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
        return device.getSpeed();
    }

    @Override
    public int getDataBits() {
        return device.getDataBits();
    }

    @Override
    public int getStopBits() {
        return device.getStopBits();
    }

    @Override
    public int getParity() {
        return device.getParityInt();
    }
}
