package com.boylab.multimodule.activity;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.widget.Toast;

import com.boylab.multimodule.R;
import com.boylab.multimodule.bean.ViewModule;
import com.boylab.multimodule.modbus.data.WeighSign1;
import com.boylab.multimodule.modbus.data.WeightInfo;
import com.boylab.multimodule.modbus.socket.CellManager;
import com.boylab.multimodule.modbus.socket.Command;
import com.boylab.multimodule.modbus.socket.OnCellListener;
import com.boylab.multimodule.util.GridDecoration;
import com.boylab.multimodule.view.D3View;
import com.kongzue.dialogx.DialogX;
import com.kongzue.dialogx.style.MIUIStyle;
import com.serotonin.modbus4j.msg.ModbusResponse;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements OnCellListener {

    private CellManager cellManager = CellManager.instance();

    private D3View d3View01, d3View02, d3View03, d3View04, d3View05, d3View06, d3View07, d3View08;
    private final int module01 = 1, slaveId01 = 1;
    private final int module02 = 2, slaveId02 = 2;
    private final int module03 = 3, slaveId03 = 1;
    private final int module04 = 4, slaveId04 = 2;
    private final int module05 = 5, slaveId05 = 1;
    private final int module06 = 6, slaveId06 = 2;
    private final int module07 = 7, slaveId07 = 1;
    private final int module08 = 8, slaveId08 = 2;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        d3View01 = findViewById(R.id.d3View01);
        d3View02 = findViewById(R.id.d3View02);
        d3View03 = findViewById(R.id.d3View03);
        d3View04 = findViewById(R.id.d3View04);
        d3View05 = findViewById(R.id.d3View05);
        d3View06 = findViewById(R.id.d3View06);
        d3View07 = findViewById(R.id.d3View07);
        d3View08 = findViewById(R.id.d3View08);

        d3View01.setModule(module01, slaveId01);
        d3View02.setModule(module02, slaveId02);
        d3View03.setModule(module03, slaveId03);
        d3View04.setModule(module04, slaveId04);
        d3View05.setModule(module05, slaveId05);
        d3View06.setModule(module06, slaveId06);
        d3View07.setModule(module07, slaveId07);
        d3View08.setModule(module08, slaveId08);

        /**
         * 1、程序启动，开启模块读写
         */
        cellManager.startEngine();

        onD3ViewClick(d3View01);
        onD3ViewClick(d3View02);
        /*onD3ViewClick(d3View03);
        onD3ViewClick(d3View04);
        onD3ViewClick(d3View05);
        onD3ViewClick(d3View06);
        onD3ViewClick(d3View07);
        onD3ViewClick(d3View08);*/
    }

    @Override
    protected void initData() {
        DialogX.globalStyle = MIUIStyle.style();
    }

    @Override
    protected void onStart() {
        super.onStart();
        cellManager.setOnCellListener(slaveId01, this);
        cellManager.setOnCellListener(slaveId02, this);
    }

    private void onD3ViewClick(D3View d3View){
        d3View.setOnViewCallBack(new D3View.OnActionListener() {

            @Override
            public void onD3Start(int moduleId, int slaveId) {
                //第一个模块通讯地址为1
                cellManager.createCell(moduleId, slaveId);
                cellManager.setOnCellListener(moduleId, MainActivity.this);
            }

            @Override
            public void onD3Stop(int moduleId, int slaveId) {
                cellManager.removeCell(moduleId);
            }

            @Override
            public void onD3Tare(int moduleId, int slaveId) {
                cellManager.sendCmd(moduleId, Command.TARE.setSlaveId(slaveId));
            }

            @Override
            public void onD3Zero(int moduleId, int slaveId) {
                cellManager.sendCmd(moduleId, Command.ZERO.setSlaveId(slaveId));
            }

            @Override
            public void onD3Calib(int moduleId, int slaveId) {
                Intent intent = new Intent(MainActivity.this, CalibParaActivity.class);
                intent.putExtra("moduleId", moduleId);
                intent.putExtra("slaveId", slaveId);
                startActivity(intent);
            }

            @Override
            public void onD3SysSet(int moduleId, int slaveId) {
                Intent intent = new Intent(MainActivity.this, SysSetActivity.class);
                intent.putExtra("moduleId", moduleId);
                intent.putExtra("slaveId", slaveId);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onCellFresh(int moduleId, int slaveId, WeightInfo weightInfo) {
        //Log.i(">>>boylab>>>", "run: "+weightInfo.toString());
        if(moduleId == d3View01.moduleId()){
            onFreshWeight(d3View01, weightInfo);
        }else if(moduleId == d3View02.moduleId()){
            onFreshWeight(d3View02, weightInfo);
        }else if(moduleId == d3View03.moduleId()){
            //Log.i(">>>boylab>>>", "run: "+weightInfo.toString());
            onFreshWeight(d3View03, weightInfo);
        }else if(moduleId == d3View04.moduleId()){
            onFreshWeight(d3View04, weightInfo);
        }else if(moduleId == d3View05.moduleId()){
            onFreshWeight(d3View05, weightInfo);
        }else if(moduleId == d3View06.moduleId()){
            onFreshWeight(d3View06, weightInfo);
        }else if(moduleId == d3View07.moduleId()){
            onFreshWeight(d3View07, weightInfo);
        }else if(moduleId == d3View08.moduleId()){
            onFreshWeight(d3View08, weightInfo);
        }
    }

    /**
     * 刷新称重信息
     * @param d3View
     * @param weightInfo
     */
    private void onFreshWeight(D3View d3View, WeightInfo weightInfo){
        String net = weightInfo.netFormat();
        String gross = weightInfo.grossFormat();
        String tare = weightInfo.tareFormat();
        d3View.setWeight(net, gross, tare);

        WeighSign1 weighSign1 = weightInfo.weighSign1();
        boolean isStable = weighSign1.isStable();
        boolean isTare = weighSign1.isTare();
        boolean isZero = weighSign1.isZero();
        d3View.setSign(isStable, isTare, isZero);
    }

    @Override
    public void onCellAction(int moduleId, int slaveId, int what, ModbusResponse modbusResponse) {
        if (what == Command.TARE.what()){
            Toast.makeText(this, "去皮操作", Toast.LENGTH_SHORT).show();
            if(moduleId == d3View01.moduleId()){

            }else if(moduleId == d3View02.moduleId()){

            }else if(moduleId == d3View03.moduleId()){

            }else if(moduleId == d3View04.moduleId()){

            }else if(moduleId == d3View05.moduleId()){

            }else if(moduleId == d3View06.moduleId()){

            }else if(moduleId == d3View07.moduleId()){

            }else if(moduleId == d3View08.moduleId()){

            }
        }else if (what == Command.ZERO.what()){
            Toast.makeText(this, "置零操作", Toast.LENGTH_SHORT).show();
            if(moduleId == d3View01.moduleId()){

            }else if(moduleId == d3View02.moduleId()){

            }else if(moduleId == d3View03.moduleId()){

            }else if(moduleId == d3View04.moduleId()){

            }else if(moduleId == d3View05.moduleId()){

            }else if(moduleId == d3View06.moduleId()){

            }else if(moduleId == d3View07.moduleId()){

            }else if(moduleId == d3View08.moduleId()){

            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cellManager.stopEngine();
    }

}