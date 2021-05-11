package com.yutiantina.livedatabus.ipc

internal object IPCConst {
    const val BROADCAST_ACTION = "intent.action.livedatabus.recevier"
    const val BROADCAST_KEY = "livedataevent_key"
    const val BROADCAST_VALUE_KEY = "livedataevent_value"
    const val BROADCAST_VALUE_TYPE = "livedataevent_value_type"
    const val BROADCAST_VALUE_CLASS_NAME = "livedataevent_value_class_name"
}

internal enum class ValueType{
    String,
    Boolean,
    Int,
    Long,
    Float,
    Double,
    Parcelable,
    Seralizable,
    Gson
}