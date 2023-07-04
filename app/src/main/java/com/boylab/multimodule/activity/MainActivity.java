package com.boylab.multimodule.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.boylab.multimodule.R;
import com.boylab.multimodule.modbus.data.WeighSign1;
import com.boylab.multimodule.modbus.data.WeightInfo;
import com.boylab.multimodule.modbus.socket.CellManager;
import com.boylab.multimodule.modbus.socket.Command;
import com.boylab.multimodule.modbus.socket.OnCellListener;
import com.boylab.multimodule.util.PhoneUtil;
import com.boylab.multimodule.view.D3View;
import com.kongzue.dialogx.DialogX;
import com.kongzue.dialogx.style.MIUIStyle;
import com.serotonin.modbus4j.msg.ModbusResponse;

public class MainActivity extends BaseActivity implements OnCellListener {

    private CellManager cellManager = CellManager.instance();

    private D3View d3View01, d3View02;
    private final int slaveId_01 = 1,  slaveId_02 = 2;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        d3View01 = findViewById(R.id.d3View01);
        d3View02 = findViewById(R.id.d3View02);

        /**
         * 1、程序启动，开启模块读写
         */
        cellManager.startEngine();

        /**
         * 2、第一个模块
         */
        d3View01.setOnViewCallBack(new D3View.OnActionListener() {

            @Override
            public void onD3Start() {
                //第一个模块通讯地址为1
                cellManager.createCell(slaveId_01);
                cellManager.setOnCellListener(slaveId_01, MainActivity.this);
            }

            @Override
            public void onD3Stop() {
                cellManager.removeCell(slaveId_01);
            }

            @Override
            public void onD3Tare() {
                cellManager.tare(slaveId_01);
            }

            @Override
            public void onD3Zero() {
                cellManager.zero(slaveId_01);
            }

            @Override
            public void onD3Calib() {
                Intent intent = new Intent(MainActivity.this, CalibParaActivity.class);
                intent.putExtra("slaveId", slaveId_01);
                startActivity(intent);
            }

            @Override
            public void onD3SysSet() {
                Intent intent = new Intent(MainActivity.this, SysSetActivity.class);
                intent.putExtra("slaveId", slaveId_01);
                startActivity(intent);
            }
        });

        /**
         * 第二个模块
         */
        d3View02.setOnViewCallBack(new D3View.OnActionListener() {
            @Override
            public void onD3Start() {
                //第二个模块通讯地址为2
                cellManager.createCell(slaveId_02);
                cellManager.setOnCellListener(slaveId_02, MainActivity.this);
            }

            @Override
            public void onD3Stop() {
                cellManager.removeCell(slaveId_02);
            }

            @Override
            public void onD3Tare() {
                cellManager.tare(slaveId_02);
            }

            @Override
            public void onD3Zero() {
                cellManager.zero(slaveId_02);
            }

            @Override
            public void onD3Calib() {
                Intent intent = new Intent(MainActivity.this, CalibParaActivity.class);
                intent.putExtra("slaveId", slaveId_02);
                startActivity(intent);
            }

            @Override
            public void onD3SysSet() {
                Intent intent = new Intent(MainActivity.this, SysSetActivity.class);
                intent.putExtra("slaveId", slaveId_02);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initData() {
        DialogX.globalStyle = MIUIStyle.style();
    }

    @Override
    protected void onStart() {
        super.onStart();
        cellManager.setOnCellListener(slaveId_01, this);
        cellManager.setOnCellListener(slaveId_02, this);
    }

    @Override
    public void onCellFresh(int slaveId, WeightInfo weightInfo) {
        if (slaveId == slaveId_01){
            //Log.i(">>>boylab>>>", "run: "+weightInfo.toString());
            String net = weightInfo.netFormat();
            String gross = weightInfo.grossFormat();
            String tare = weightInfo.tareFormat();
            d3View01.setWeight(net, gross, tare);

            WeighSign1 weighSign1 = weightInfo.weighSign1();
            boolean isStable = weighSign1.isStable();
            boolean isTare = weighSign1.isTare();
            boolean isZero = weighSign1.isZero();
            d3View01.setSign(isStable, isTare, isZero);
        }else if (slaveId == slaveId_02){
            String net = weightInfo.netFormat();
            String gross = weightInfo.grossFormat();
            String tare = weightInfo.tareFormat();
            d3View02.setWeight(net, gross, tare);

            WeighSign1 weighSign1 = weightInfo.weighSign1();
            boolean isStable = weighSign1.isStable();
            boolean isTare = weighSign1.isTare();
            boolean isZero = weighSign1.isZero();
            d3View02.setSign(isStable, isTare, isZero);
        }
    }

    @Override
    public void onCellAction(int slaveId, int what, ModbusResponse modbusResponse) {
        //Log.i(">>>boylab>>>", "onCellAction: "+Integer.toHexString(what));
        if (what == Command.TARE){

            if (slaveId == slaveId_01){

            }else if (slaveId == slaveId_02){

            }
        }else if (what == Command.ZERO){
            if (slaveId == slaveId_01){

            }else if (slaveId == slaveId_02){

            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cellManager.stopEngine();
    }

}