package com.yutiantina.livedatabus.ipc

import android.content.Intent
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException

internal class IPCDecoder {
    private val gson : Gson by lazy { Gson() }
    @Throws(IPCException::class)
    fun decode(intent : Intent) : Any{
        val type = intent.getIntExtra(IPCConst.BROADCAST_VALUE_TYPE, -1)
        if( 0 > type){
            throw IPCException("decode error")
        }
        val valueType = ValueType.values()[type]
        when(valueType){
            ValueType.String ->{
                return intent.getStringExtra(IPCConst.BROADCAST_VALUE_KEY)
            }
            ValueType.Boolean -> {
                return intent.getBooleanExtra(IPCConst.BROADCAST_VALUE_KEY, false)
            }
            ValueType.Int -> {
                return intent.getIntExtra(IPCConst.BROADCAST_VALUE_KEY, -1)
            }
            ValueType.Long -> {
                return intent.getLongExtra(IPCConst.BROADCAST_VALUE_KEY, -1)
            }
            ValueType.Float -> {
                return intent.getFloatExtra(IPCConst.BROADCAST_VALUE_KEY, (-1).toFloat())
            }
            ValueType.Double -> {
                return intent.getDoubleExtra(IPCConst.BROADCAST_VALUE_KEY, (-1).toDouble())
            }
            ValueType.Seralizable -> {
                return intent.getSerializableExtra(IPCConst.BROADCAST_VALUE_KEY)
            }
            ValueType.Parcelable -> {
                return intent.getParcelableExtra(IPCConst.BROADCAST_VALUE_KEY)
            }
            ValueType.Gson -> {
                try{
                    val className = intent.getStringExtra(IPCConst.BROADCAST_VALUE_CLASS_NAME)
                    val valueGson = intent.getStringExtra(IPCConst.BROADCAST_VALUE_KEY)
                    return gson.fromJson(valueGson, Class.forName(className))
                }catch (e : JsonSyntaxException){
                    throw IPCException(e.message)
                }
            }
        }
    }
}