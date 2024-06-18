package com.boylab.multimodule.activity;

import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.boylab.multimodule.R;
import com.boylab.multimodule.adapter.SysSetAdapter;
import com.boylab.multimodule.bean.SysSetBean;
import com.boylab.multimodule.modbus.bean.ReqModbus;
import com.boylab.multimodule.modbus.data.WeightInfo;
import com.boylab.multimodule.modbus.socket.CellManager;
import com.boylab.multimodule.modbus.socket.Command;
import com.boylab.multimodule.modbus.socket.OnCellListener;
import com.boylab.multimodule.util.RecyclerUtil;
import com.boylab.multimodule.util.ViewClick;
import com.kongzue.dialogx.dialogs.TipDialog;
import com.serotonin.modbus4j.msg.ModbusResponse;
import com.serotonin.modbus4j.msg.ReadHoldingRegistersResponse;
import com.serotonin.modbus4j.msg.WriteRegisterResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SysSetActivity extends BaseActivity implements View.OnClickListener, OnCellListener {

    private CellManager cellManager = CellManager.instance();

    private RecyclerView rv_SysSet;
    private SysSetAdapter sysSetAdapter;
    private List<Integer> valueList = new ArrayList<>();

    private Button btn_Read, btn_Save;
    private int moduleId = 0, slaveId = 0;
    private SysSetBean mSysSetBean = new SysSetBean();

    @Override
    protected int getContentView() {
        return R.layout.activity_sys_set;
    }

    @Override
    protected void initView() {
        rv_SysSet = findViewById(R.id.rv_SysSet);
        rv_SysSet.setLayoutManager(RecyclerUtil.gridLayoutManager(this, 2));
        //rv_SysSet.addItemDecoration(RecyclerUtil.gridDivider(Color.GRAY));

        valueList.addAll(mSysSetBean.sysSetList());
        sysSetAdapter = new SysSetAdapter(this, valueList);
        rv_SysSet.setAdapter(sysSetAdapter);

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

        ReqModbus cmd = Command.ReadSys;
        cmd.setSlaveId(this.slaveId);
        cellManager.sendCmd(this.slaveId, cmd);
    }

    @Override
    public void onClick(View v) {
        if (ViewClick.isFastClick()){
            return;
        }
        if (v.getId() == R.id.btn_Read){
            TipDialog.show("点击读");
            ReqModbus cmd = Command.ReadSys;
            cmd.setSlaveId(this.slaveId);
            cellManager.sendCmd(this.slaveId, cmd);
        }else if (v.getId() == R.id.btn_Save){
            short[] calibData = mSysSetBean.toSysSetShort();
            ReqModbus cmd = Command.WriteSys;
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
            if (what == Command.ReadSys.what()){
                ReadHoldingRegistersResponse mResponse = (ReadHoldingRegistersResponse) modbusResponse;
                byte[] data = mResponse.getData();
                //Log.i(">>>boylab>>>", "onCellAction: "+Arrays.toString(data));
                mSysSetBean.toPara(data);

                valueList.clear();
                valueList.addAll(mSysSetBean.sysSetList());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeWait();
                        toastSuccess("读取成功");
                        sysSetAdapter.notifyDataSetChanged();
                    }
                });
            }else if (what == Command.WriteSys.what()){
                WriteRegisterResponse mResponse = (WriteRegisterResponse) modbusResponse;

            }
        }
    }
}