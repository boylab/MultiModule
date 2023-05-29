package com.boylab.multimodule.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
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
import com.serotonin.modbus4j.msg.ModbusResponse;
import com.serotonin.modbus4j.msg.ReadHoldingRegistersResponse;
import com.serotonin.modbus4j.msg.WriteRegisterResponse;

import java.util.ArrayList;
import java.util.List;

public class CalibParaActivity extends AppCompatActivity implements View.OnClickListener, OnCellListener {

    private CellManager cellManager = CellManager.instance();

    private RecyclerView rv_CalibPara;
    private CalibParaAdapter calibParaAdapter;
    private List<Integer> valueList = new ArrayList<>();

    private Button btn_Read, btn_Save;
    private int slaveId = 0;
    private CalibPara mCalibPara = new CalibPara();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calib_para);
        rv_CalibPara = findViewById(R.id.rv_CalibPara);
        rv_CalibPara.setLayoutManager(RecyclerUtil.gridLayoutManager(this, 2));
        //rv_CalibPara.addItemDecoration(RecyclerUtil.gridDivider(Color.GRAY));

        calibParaAdapter = new CalibParaAdapter(this, valueList);
        rv_CalibPara.setAdapter(calibParaAdapter);

        btn_Read = findViewById(R.id.btn_Read);
        btn_Save = findViewById(R.id.btn_Save);

        btn_Read.setOnClickListener(this);
        btn_Save.setOnClickListener(this);

        slaveId = getIntent().getIntExtra("slaveId", 0);
    }

    @Override
    protected void onStart() {
        super.onStart();
        cellManager.setOnCellListener(slaveId, this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_Read){
            ReqModbus cmd = Command.getCmd(Command.ReadCalib);
            cmd.setSlaveId(this.slaveId);
            cellManager.sendCmd(this.slaveId, cmd);
        }else if (v.getId() == R.id.btn_Save){
            short[] calibData = mCalibPara.toCalibShort();
            ReqModbus cmd = Command.getCmd(Command.ReadCalib);
            cmd.setSlaveId(this.slaveId);
            cmd.setWriteValue(calibData);
            cellManager.sendCmd(this.slaveId, cmd);
        }
    }

    @Override
    public void onCellFresh(int slaveId, WeightInfo weightInfo) {

    }

    @Override
    public void onCellAction(int slaveId, int what, ModbusResponse modbusResponse) {
        if (this.slaveId == slaveId){
            if (what == Command.ReadCalib){
                ReadHoldingRegistersResponse mResponse = (ReadHoldingRegistersResponse) modbusResponse;
                byte[] data = mResponse.getData();
                mCalibPara.toPara(data);

                valueList.clear();
                valueList.addAll(mCalibPara.calibParaList());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(CalibParaActivity.this, "读取成功", Toast.LENGTH_SHORT).show();
                        calibParaAdapter.notifyDataSetChanged();
                    }
                });
            }else if (what == Command.WriteCalib){
                WriteRegisterResponse mResponse = (WriteRegisterResponse) modbusResponse;

            }
        }
    }
}