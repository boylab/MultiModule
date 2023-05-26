package com.boylab.multimodule;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.boylab.multimodule.modbus.data.WeighSign1;
import com.boylab.multimodule.modbus.data.WeightInfo;
import com.boylab.multimodule.modbus.socket.CellManager;
import com.boylab.multimodule.modbus.socket.OnCellListener;
import com.boylab.multimodule.view.D3View;
import com.serotonin.modbus4j.msg.ModbusResponse;

public class MainActivity extends AppCompatActivity {

    private CellManager cellManager = CellManager.instance();

    private D3View d3View01, d3View02;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        d3View01 = findViewById(R.id.d3View01);
        d3View02 = findViewById(R.id.d3View02);

        /**
         * 1、程序启动，开启模块读写
         */
        cellManager.startEngine();

        /**
         * 2、第一个模块
         */
        int slaveId_01 = 1;
        d3View01.setOnViewCallBack(new D3View.OnActionListener() {

            @Override
            public void onViewAdd() {
                //第一个模块通讯地址为1
                cellManager.createCell(slaveId_01);
                cellManager.setOnCellListener(slaveId_01, new OnCellListener() {
                    @Override
                    public void onCellFresh(int slaveId, WeightInfo weightInfo) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.i(">>>boylab>>>", "run: "+weightInfo.toString());
                                String net = weightInfo.netFormat();
                                String gross = weightInfo.grossFormat();
                                String tare = weightInfo.tareFormat();
                                d3View01.setWeight(net, gross, tare);

                                WeighSign1 weighSign1 = weightInfo.weighSign1();
                                boolean isStable = weighSign1.isStable();
                                boolean isTare = weighSign1.isTare();
                                boolean isZero = weighSign1.isZero();
                                d3View01.setSign(isStable, isTare, isZero);
                            }
                        });
                    }

                    @Override
                    public void onCellAction(int slaveId, int what, ModbusResponse modbusResponse) {

                    }
                });
            }

            @Override
            public void onViewRemove() {
                cellManager.removeCell(slaveId_01);
            }

            @Override
            public void onViewTare() {
                //Toast.makeText(MainActivity.this, "待完成", Toast.LENGTH_SHORT).show();
                cellManager.tare(slaveId_01);
            }

            @Override
            public void onViewZero() {
//                Toast.makeText(MainActivity.this, "待完成", Toast.LENGTH_SHORT).show();
                cellManager.zero(slaveId_01);
            }
        });

        /**
         * 第二个模块
         */
        int slaveId_02 = 2;
        d3View02.setOnViewCallBack(new D3View.OnActionListener() {
            @Override
            public void onViewAdd() {

                //第二个模块通讯地址为2
                cellManager.createCell(slaveId_02);
                cellManager.setOnCellListener(slaveId_02, new OnCellListener() {
                    @Override
                    public void onCellFresh(int slaveId, WeightInfo weightInfo) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
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
                        });
                    }

                    @Override
                    public void onCellAction(int slaveId, int what, ModbusResponse modbusResponse) {

                    }
                });
            }

            @Override
            public void onViewRemove() {
                cellManager.removeCell(slaveId_02);
            }

            @Override
            public void onViewTare() {
                //Toast.makeText(MainActivity.this, "待完成", Toast.LENGTH_SHORT).show();
                cellManager.tare(slaveId_02);
            }

            @Override
            public void onViewZero() {
                //Toast.makeText(MainActivity.this, "待完成", Toast.LENGTH_SHORT).show();
                cellManager.zero(slaveId_02);
            }
        });


        /**
         * 第一种：被动实时刷新显示
         *  cellManager.setOnCellListener(1, OnCellListener)接收
         *
         *  第二种：主动取刷新的数据
         */


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cellManager.stopEngine();
    }
}