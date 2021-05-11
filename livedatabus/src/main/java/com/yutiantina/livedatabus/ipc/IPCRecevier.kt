package com.yutiantina.livedatabus.ipc

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.yutiantina.livedatabus.LiveDataBus

internal class IPCRecevier : BroadcastReceiver() {
    private val decoder : IPCDecoder by lazy { IPCDecoder() }
    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent?.action == IPCConst.BROADCAST_ACTION){
            val value = decoder.decode(intent)
            val key = intent.getStringExtra(IPCConst.BROADCAST_KEY)
            LiveDataBus.get()
                .with(key, Any::class.java)
                .post(value)
        }
    }
}