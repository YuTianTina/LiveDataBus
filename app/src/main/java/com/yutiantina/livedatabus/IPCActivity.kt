package com.yutiantina.livedatabus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class IPCActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ipc)
    }

    fun sendIpc(view: View) {
        LiveDataBus.get()
            .of(LiveEvents::class.java)
            .ipcEvent()
            .broadcast(this, IPCEntity("from IPCActivity"))
    }
}
