package com.yutiantina.livedatabus

import android.content.Context
import android.content.Intent
import androidx.annotation.MainThread
import androidx.lifecycle.ExternalNewLiveData
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.yutiantina.livedatabus.ipc.IPCConst
import com.yutiantina.livedatabus.ipc.IPCEncoder
import com.yutiantina.livedatabus.utils.isMainThread
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 *
 * LiveDataEvent内部实现类
 * @author yutiantian email: yutiantina@gmail.com
 * @since 2019-09-12
 */
internal class LiveDataEvent<T>(val key: String) : LiveEventObserver<T>{
    private val liveData by lazy { ExternalNewLiveData<T>(key) }
    private val encoder : IPCEncoder by lazy { IPCEncoder() }
    override fun post(value: T) {
        postInternal(value)
    }

    override fun broadcast(context: Context, value: T) {
        GlobalScope.launch (Dispatchers.Main){
            broadcastInternal(context.applicationContext, value)
        }
    }

    private fun broadcastInternal(context: Context, value: T) {
        context.sendBroadcast(Intent(IPCConst.BROADCAST_ACTION).also {
            it.putExtra(IPCConst.BROADCAST_KEY, key)
            encoder.encode(it, value)
        })
    }

    override fun observe(lifecycleOwner: LifecycleOwner, observer: Observer<T>){
        GlobalScope.launch(Dispatchers.Main) {
            observeInternal(lifecycleOwner, observer)
        }
    }

    override fun observeSticky(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
        GlobalScope.launch(Dispatchers.Main){
            observeStickyInternal(lifecycleOwner, observer)
        }
    }

    override fun observeForever(observer: Observer<T>) {
        GlobalScope.launch(Dispatchers.Main){
            observeForeverInternal(observer)
        }
    }

    override fun observeStickyForever(observer: Observer<T>) {
        GlobalScope.launch (Dispatchers.Main){
            observeStickyForeverInternal(observer)
        }
    }

    override fun removeObserver(observer: Observer<T>) {
        GlobalScope.launch(Dispatchers.Main){
            removeObserverInternal(observer)
        }
    }

    private fun postInternal(value: T){
        if(isMainThread()){
            liveData.value = value
        }else{
            liveData.postValue(value)
        }
    }

    @MainThread
    private fun observeInternal(lifecycleOwner: LifecycleOwner, observer: Observer<T>){
        liveData.observe(lifecycleOwner, observer)
    }

    @MainThread
    private fun observeStickyInternal(lifecycleOwner: LifecycleOwner, observer: Observer<T>){
        liveData.observeSticky(lifecycleOwner, observer)
    }

    @MainThread
    private fun removeObserverInternal(observer: Observer<T>) {
        liveData.removeObserver(observer)
    }

    @MainThread
    private fun observeForeverInternal(observer: Observer<T>) {
        liveData.observeForever(observer)
    }

    @MainThread
    private fun observeStickyForeverInternal(observer: Observer<T>) {
        liveData.observeStickyForever(observer)
    }
}