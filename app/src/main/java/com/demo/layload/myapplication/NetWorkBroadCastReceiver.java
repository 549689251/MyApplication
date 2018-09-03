package com.demo.layload.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * @author gongdongdong
 * @date 2018/9/3
 * <p>
 * 网络监控的广播
 */

public class NetWorkBroadCastReceiver extends BroadcastReceiver {

    private OnNetWorkWatchListener listener;

    public NetWorkBroadCastReceiver(OnNetWorkWatchListener listenser) {
        this.listener = listenser;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            if (intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")) {

                Utils.NetState state = Utils.networkState(context);
                if (listener != null) {
                    listener.onNetWorkChanged(state);
                }
            }

        }
    }

    public interface OnNetWorkWatchListener {
        /**
         * 网络状态
         *
         * @param state
         */
        void onNetWorkChanged(Utils.NetState state);
    }


}
