package com.yutiantina.livedatabus

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.yutiantina.livedatabus.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivitySecondBinding>(this, R.layout.activity_second).apply {
            secondAct = this@SecondActivity
        }

        LiveDataBus
            .get()
            .of(LiveEvents::class.java)
            .testObserveStickyInMain()
            .observeSticky(this, Observer {
                Log.i(LOG, it)
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            })
    }

    fun sendToMain(){
        LiveDataBus.get()
            .of(LiveEvents::class.java)
            .second2Main()
            .post("aaa")
    }


    fun sendForever(){
        LiveDataBus.get()
            .of(LiveEvents::class.java)
            .testOberverForever()
            .post("forever")
    }

    fun removeForever(){
        LiveDataBus
            .get()
            .of(LiveEvents::class.java)
            .testOberverForever()
            .removeObserver(foreverObesrve)
    }

    fun jumpToMain(){
        finish()
        startActivity(Intent(this, MainActivity::class.java))
    }
}
