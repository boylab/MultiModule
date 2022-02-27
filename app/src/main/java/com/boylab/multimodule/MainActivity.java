package com.boylab.multimodule;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.boylab.multimodule.modbus.socket.CellManager;
import com.boylab.multimodule.view.D3View;

public class MainActivity extends AppCompatActivity {

    private CellManager cellManager = CellManager.instance();

    private Button btn_Start;
    private D3View d3View01, d3View02;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_Start = findViewById(R.id.btn_Start);
        d3View01 = findViewById(R.id.d3View01);
        d3View02 = findViewById(R.id.d3View02);

        btn_Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cellManager.startEngine();
            }
        });

        d3View01.setOnViewCallBack(new D3View.OnViewCallBack() {

            @Override
            public void onViewAdd() {
                //第一个模块通讯地址为1
                cellManager.createCell(1);
            }

            @Override
            public void onViewRemove() {
                cellManager.removeCell(1);
            }

            @Override
            public void onViewTare() {
                cellManager.tare(1);
            }

            @Override
            public void onViewZero() {
                cellManager.zero(1);
            }
        });

        d3View02.setOnViewCallBack(new D3View.OnViewCallBack() {
            @Override
            public void onViewAdd() {
                //第二个模块通讯地址为2
                cellManager.createCell(2);
            }

            @Override
            public void onViewRemove() {
                cellManager.removeCell(2);
            }

            @Override
            public void onViewTare() {
                cellManager.tare(2);
            }

            @Override
            public void onViewZero() {
                cellManager.zero(2);
            }
        });


        /**
         * 第一种：主动取刷新的数据
         *
         * 第二种：被动刷新显示
         *
         */








    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cellManager.stopEngine();
    }
}