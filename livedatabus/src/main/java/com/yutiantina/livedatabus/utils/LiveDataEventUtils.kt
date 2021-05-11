package com.yutiantina.livedatabus.utils

import android.os.Looper

/**
 *
 * @author yutiantian email: yutiantina@gmail.com
 * @since 2019-09-17
 */
internal fun isMainThread():Boolean{
    return Looper.myLooper() == Looper.getMainLooper()
}