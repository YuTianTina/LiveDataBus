package com.yutiantina.livedatabus

import android.util.Log
import androidx.lifecycle.Observer

/**
 *
 * @author yutiantian email: yutiantina@gmail.com
 * @since 2019-09-17
 */
val foreverObesrve = Observer<String>{
    Log.i(LOG, "forever Observe")
}