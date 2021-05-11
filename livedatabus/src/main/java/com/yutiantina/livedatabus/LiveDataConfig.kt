package com.yutiantina.livedatabus

import android.content.Context
import android.content.IntentFilter
import com.yutiantina.livedatabus.ipc.IPCConst
import com.yutiantina.livedatabus.ipc.IPCRecevier

class LiveDataConfig {
    /**
     * 支持多进程通信
     * @param context Context
     */
    fun supportMultiProcess(context : Context){
        context.applicationContext
            .registerReceiver(IPCRecevier(),
                IntentFilter(IPCConst.BROADCAST_ACTION)
            )
    }

}
