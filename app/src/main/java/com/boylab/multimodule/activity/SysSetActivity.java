package com.boylab.multimodule.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.boylab.multimodule.R;
import com.boylab.multimodule.adapter.SysSetAdapter;
import com.boylab.multimodule.util.RecyclerUtil;

import java.util.ArrayList;
import java.util.List;

public class SysSetActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView rv_SysSet;
    private SysSetAdapter SysSetAdapter;
    private List<String> valueList = new ArrayList<>();

    private Button btn_Read, btn_Save;
    private int slaveId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sys_set);

        rv_SysSet = findViewById(R.id.rv_SysSet);
        rv_SysSet.setLayoutManager(RecyclerUtil.gridLayoutManager(this, 2));
        //rv_SysSet.addItemDecoration(RecyclerUtil.gridDivider(Color.GRAY));

        SysSetAdapter = new SysSetAdapter(this, valueList);
        rv_SysSet.setAdapter(SysSetAdapter);

        btn_Read = findViewById(R.id.btn_Read);
        btn_Save = findViewById(R.id.btn_Save);

        btn_Read.setOnClickListener(this);
        btn_Save.setOnClickListener(this);

        slaveId = getIntent().getIntExtra("slaveId", 0);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_Read){

        }else if (v.getId() == R.id.btn_Save){

        }
    }
}