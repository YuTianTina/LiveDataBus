package com.yutiantina.livedatabus

import android.app.Application

class MainApp : Application() {
    override fun onCreate() {
        super.onCreate()
        LiveDataBus.init()
            .supportMultiProcess(this)
    }
}