package com.boylab.multimodule.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.boylab.multimodule.R;
import com.boylab.multimodule.adapter.CalibParaAdapter;
import com.boylab.multimodule.bean.CalibPara;
import com.boylab.multimodule.modbus.bean.ReqModbus;
import com.boylab.multimodule.modbus.data.WeightInfo;
import com.boylab.multimodule.modbus.socket.CellManager;
import com.boylab.multimodule.modbus.socket.Command;
import com.boylab.multimodule.modbus.socket.OnCellListener;
import com.boylab.multimodule.util.RecyclerUtil;
import com.boylab.multimodule.util.ViewClick;
import com.serotonin.modbus4j.msg.ModbusResponse;
import com.serotonin.modbus4j.msg.ReadHoldingRegistersResponse;
import com.serotonin.modbus4j.msg.WriteRegisterResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CalibParaActivity extends BaseActivity implements View.OnClickListener, OnCellListener {

    private int moduleId = 0, slaveId = 0;
    private CellManager cellManager = CellManager.instance();

    private RecyclerView rv_CalibPara;
    private CalibParaAdapter calibParaAdapter;
    private List<Integer> valueList = new ArrayList<>();

    private Button btn_Read, btn_Save;

    private CalibPara mCalibPara = new CalibPara();

    @Override
    protected int getContentView() {
        return R.layout.activity_calib_para;
    }

    @Override
    protected void initView() {
        rv_CalibPara = findViewById(R.id.rv_CalibPara);
        rv_CalibPara.setLayoutManager(RecyclerUtil.gridLayoutManager(this, 2));
        //rv_CalibPara.addItemDecoration(RecyclerUtil.gridDivider(Color.GRAY));

        valueList.addAll(mCalibPara.calibParaList());
        calibParaAdapter = new CalibParaAdapter(this, valueList);
        rv_CalibPara.setAdapter(calibParaAdapter);

        btn_Read = findViewById(R.id.btn_Read);
        btn_Save = findViewById(R.id.btn_Save);

        btn_Read.setOnClickListener(this);
        btn_Save.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        moduleId = getIntent().getIntExtra("moduleId", 0);
        slaveId = getIntent().getIntExtra("slaveId", 0);

    }

    @Override
    protected void onStart() {
        super.onStart();
        cellManager.setOnCellListener(slaveId, this);

        ReqModbus cmd = Command.ReadCalib;
        cmd.setSlaveId(this.slaveId);
        cellManager.sendCmd(this.slaveId, cmd);
    }

    @Override
    public void onClick(View v) {
        if (ViewClick.isFastClick()){
            return;
        }
        if (v.getId() == R.id.btn_Read){
            ReqModbus cmd = Command.ReadCalib;
            cmd.setSlaveId(this.slaveId);
            cellManager.sendCmd(this.slaveId, cmd);
        }else if (v.getId() == R.id.btn_Save){
            short[] calibData = mCalibPara.toCalibShort();
            ReqModbus cmd = Command.ReadCalib;
            cmd.setSlaveId(this.slaveId);
            cmd.setWriteValue(calibData);
            cellManager.sendCmd(this.slaveId, cmd);
        }
    }

    @Override
    public void onCellFresh(int moduleId, int slaveId, WeightInfo weightInfo) {

    }

    @Override
    public void onCellAction(int moduleId, int slaveId, int what, ModbusResponse modbusResponse) {
        if (this.slaveId == slaveId){
            Log.i(">>>boylab>>>", "onCellAction: "+Integer.toHexString(what));
            if (what == Command.ReadCalib.what()){
                ReadHoldingRegistersResponse mResponse = (ReadHoldingRegistersResponse) modbusResponse;
                byte[] data = mResponse.getData();
                Log.i(">>>boylab>>>", "onCellAction: "+ Arrays.toString(data));
                mCalibPara.toPara(data);

                valueList.clear();
                valueList.addAll(mCalibPara.calibParaList());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeWait();
                        toastSuccess("读取成功");
                        calibParaAdapter.notifyDataSetChanged();
                    }
                });
            }else if (what == Command.WriteCalib.what()){
                WriteRegisterResponse mResponse = (WriteRegisterResponse) modbusResponse;

            }
        }
    }
}