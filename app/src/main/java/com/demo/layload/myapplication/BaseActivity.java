package com.demo.layload.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;

/**
 * @author gongdongdong
 * @date 2018/9/3
 */

public abstract class BaseActivity extends FragmentActivity implements NetWorkBroadCastReceiver.OnNetWorkWatchListener {
    private static final String TAG = BaseActivity.class.getSimpleName();
    private NetWorkBroadCastReceiver mNetWorkBroadCastReceiver;

    private AlertDialog mAlertDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(initView());
        initData();
    }


    protected abstract int initView();

    protected void initData() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        mNetWorkBroadCastReceiver = new NetWorkBroadCastReceiver(this);
        registerReceiver(mNetWorkBroadCastReceiver, intentFilter);

    }


    @Override
    public void onNetWorkChanged(Utils.NetState state) {
        Toast.makeText(this, state + "-" + TAG, Toast.LENGTH_SHORT).show();
        if (state == Utils.NetState.NET_MOBILE) {
            showNetDialog("正在使用数据流量，是否打开Wi-Fi？", state);
        } else if (state == Utils.NetState.NET_NOE) {
            showNetDialog("请打开网络！", state);
        }

    }

    /**
     * 弹出设置网络框
     */
    private void showNetDialog(String msg, final Utils.NetState state) {
        if (mAlertDialog == null) {
            mAlertDialog = new AlertDialog.Builder(this).setCancelable(false).setMessage(msg).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mAlertDialog.dismiss();
                }
            }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (state == Utils.NetState.NET_MOBILE) {
                        Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(Settings.ACTION_SETTINGS);
                        startActivity(intent);
                    }
                    mAlertDialog = null;
                }
            }).create();
        }
        mAlertDialog.show();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mNetWorkBroadCastReceiver);
    }
}
