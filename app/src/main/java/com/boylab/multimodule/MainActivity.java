package com.boylab.multimodule;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.boylab.multimodule.modbus.socket.ModbusEngine;
import com.boylab.multimodule.modbus.socket.ModbusListener;
import com.boylab.multimodule.view.D3View;
import com.serotonin.modbus4j.msg.ModbusResponse;

public class MainActivity extends AppCompatActivity implements D3View.OnViewCallBack {

    private  ModbusEngine modbusEngine =null;
    private D3View d3View;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        d3View = findViewById(R.id.d3View);
        d3View.setOnViewCallBack(this);

        modbusEngine = ModbusEngine.instance();
        modbusEngine.startEngine();
        modbusEngine.setOnModbusResponse(new ModbusListener() {
            @Override
            public void onResponse(int what, ModbusResponse response) {

            }
        });


    }

    @Override
    public void onViewTare() {

    }

    @Override
    public void onViewZero() {

    }

    @Override
    public void onViewEdit() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        modbusEngine.stopEngine();
    }
}