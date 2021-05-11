package com.yutiantina.livedatabus.ipc

import android.content.Intent
import android.os.Parcelable
import com.google.gson.Gson
import java.io.Serializable

internal class IPCEncoder{
    private val gson : Gson by lazy { Gson() }
    /**
     * value解析
     * @param intent Intent
     * @param value T
     */
    @Throws(IPCException::class)
    fun encode(intent: Intent, value: Any?){
        if(null == value){
            throw IPCException("value is null")
        }
        when(value){
            is String -> {
                intent.putExtra(IPCConst.BROADCAST_VALUE_KEY, value)
                intent.putExtra(IPCConst.BROADCAST_VALUE_TYPE, ValueType.String.ordinal)
            }
            is Boolean -> {
                intent.putExtra(IPCConst.BROADCAST_VALUE_KEY, value)
                intent.putExtra(IPCConst.BROADCAST_VALUE_TYPE, ValueType.Boolean.ordinal)
            }
            is Int -> {
                intent.putExtra(IPCConst.BROADCAST_VALUE_KEY, value)
                intent.putExtra(IPCConst.BROADCAST_VALUE_TYPE, ValueType.Int.ordinal)
            }
            is Long -> {
                intent.putExtra(IPCConst.BROADCAST_VALUE_KEY, value)
                intent.putExtra(IPCConst.BROADCAST_VALUE_TYPE, ValueType.Long.ordinal)
            }
            is Float -> {
                intent.putExtra(IPCConst.BROADCAST_VALUE_KEY, value)
                intent.putExtra(IPCConst.BROADCAST_VALUE_TYPE, ValueType.Float.ordinal)
            }
            is Double -> {
                intent.putExtra(IPCConst.BROADCAST_VALUE_KEY, value)
                intent.putExtra(IPCConst.BROADCAST_VALUE_TYPE, ValueType.Double.ordinal)
            }
            is Parcelable -> {
                intent.putExtra(IPCConst.BROADCAST_VALUE_KEY, value)
                intent.putExtra(IPCConst.BROADCAST_VALUE_TYPE, ValueType.Parcelable.ordinal)
            }
            is Serializable -> {
                intent.putExtra(IPCConst.BROADCAST_VALUE_KEY, value)
                intent.putExtra(IPCConst.BROADCAST_VALUE_TYPE, ValueType.Seralizable.ordinal)
            }
            else -> {
                val valueGson = gson.toJson(value)
                intent.putExtra(IPCConst.BROADCAST_VALUE_KEY, valueGson)
                intent.putExtra(IPCConst.BROADCAST_VALUE_CLASS_NAME, value::class.java.canonicalName)
                intent.putExtra(IPCConst.BROADCAST_VALUE_TYPE, ValueType.Gson.ordinal)
            }
        }
    }
}