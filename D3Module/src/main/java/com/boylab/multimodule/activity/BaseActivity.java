package com.boylab.multimodule.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.kongzue.dialogx.dialogs.TipDialog;
import com.kongzue.dialogx.dialogs.WaitDialog;

public abstract class BaseActivity  extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        initView();
        initData();
    }
    protected abstract int getContentView();
    protected abstract void initView();
    protected abstract void initData();


    protected void showWait(String msg){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                WaitDialog.show(msg);
            }
        });
    }

    protected void closeWait(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                WaitDialog.dismiss();
            }
        });
    }

    protected void toastSuccess(String msg){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TipDialog.show(msg, WaitDialog.TYPE.SUCCESS);
            }
        });
    }

    protected void toastError(String msg){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TipDialog.show(msg, WaitDialog.TYPE.ERROR);
            }
        });
    }
}
