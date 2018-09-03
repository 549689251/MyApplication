package com.demo.layload.myapplication;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * @author gongdongdong
 * @date 2018/9/3
 */

public class Utils {

    enum NetState {
        /**
         * Wi-Fi
         */
        NET_WIFI,
        /**
         * 移动蜂窝网
         */
        NET_MOBILE,
        /**
         * 异常
         */
        NET_NOE
    }

    public static NetState networkState(Context context) {


        //得到连接管理器对象
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager
                    .getActiveNetworkInfo();
            //如果网络连接，判断该网络类型
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                if (activeNetworkInfo.getType() == (ConnectivityManager.TYPE_WIFI)) {
                    return NetState.NET_WIFI;
                } else if (activeNetworkInfo.getType() == (ConnectivityManager.TYPE_MOBILE)) {
                    return NetState.NET_MOBILE;
                }
            } else {
                //网络异常
                return NetState.NET_NOE;
            }
        }

        return NetState.NET_NOE;

    }
}
