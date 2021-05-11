package com.yutiantina.livedatabus

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.yutiantina.livedatabus.databinding.ActivityMainBinding
import kotlin.concurrent.thread

const val LOG = "DemoEventBus"
const val testObserveInMain = "testObserveInMain"
const val testObserveInThread = "testObserveInThread"
const val testObserveStickyInMain = "testObserveStickyInMain"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main).apply {
            mainAct = this@MainActivity
        }

        observeInMain()
        observeInThread()
        observeStickyInMain()
        observeForever()

        LiveDataBus.get()
            .of(LiveEvents::class.java)
            .ipcEvent()
            .observe(this, Observer {
                toast(it.name)
            })

        observeSecond()

    }

    private fun observeSecond() {
        LiveDataBus.get()
            .of(LiveEvents::class.java)
            .second2Main()
            .observe(this, Observer {
                Log.i(LOG, it)
                toast(it)
            })
    }

    private fun observeInMain(){
        LiveDataBus
            .get()
            .of(LiveEvents::class.java)
            .testObserveInMain()
            .observe(this, Observer {
                Log.i(LOG, it)
                toast(it)
            })
    }

    private fun observeInThread(){
        thread {
            LiveDataBus
                .get()
                .of(LiveEvents::class.java)
                .testObserveInThread()
                .observe(this, Observer {
                    Log.i(LOG, it)
                    toast(it)
                })
        }
    }

    private fun observeStickyInMain(){
        LiveDataBus
            .get()
            .of(LiveEvents::class.java)
            .testObserveStickyInMain()
            .post("sticky1")

//        LiveDataBus
//            .get()
//            .of(LiveEvents::class.java)
//            .testObserveStickyInMain()
//            .observeSticky(this, Observer {
//                Log.i(LOG, it)
//                toast(it)
//            })
    }

    private fun observeForever(){
        LiveDataBus
            .get()
            .of(LiveEvents::class.java)
            .testOberverForever()
            .observeForever(foreverObesrve)
    }

    /**
    * 主线程订阅
     * 发送消息
     */
    fun sendNormalEvent(){
        sendEvent(LiveDataBus.get().of(LiveEvents::class.java).testObserveInMain(), testObserveInMain)
    }

    /**
     * 主线程粘性订阅
     * 发送消息
     */
    fun sendStickyEvent(){
        sendEvent(LiveDataBus.get().of(LiveEvents::class.java).testObserveStickyInMain(), testObserveStickyInMain)
    }

    fun sendStickyEvent2(){
        sendEvent(LiveDataBus.get().of(LiveEvents::class.java).testObserveStickyInMain(), testObserveStickyInMain)
    }

    /**
     * 子线程订阅
     * 发送消息
     */
    fun sendThreadEvent(){
        sendEvent(LiveDataBus.get().of(LiveEvents::class.java).testObserveInThread(), testObserveInThread)
    }

    fun jumpSecond(){
        startActivity(Intent(this, SecondActivity::class.java))
//        finish()
    }

    private fun sendEvent(channel: LiveEventObserver<String>, value: String){
        channel.post(value)
    }

    private fun toast(value: String){
        Toast.makeText(this, value, Toast.LENGTH_SHORT).show()
    }

    private fun sendEvent(){
        LiveDataBus
            .get()
            .of(LiveEvents::class.java)
            .event1()
            .post(true)
    }

    private fun observe(){
        LiveDataBus
            .get()
            .of(LiveEvents::class.java)
            .event1()
            .observe(this, Observer {
                Log.i(LOG, it.toString())
            })
    }

    fun runIpc(){
        startActivity(Intent(this, IPCActivity::class.java))
    }
}

