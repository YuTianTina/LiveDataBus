package com.yutiantina.livedatabus

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

/**
 *
 * LiveData代理类
 * @author yutiantian email: yutiantina@gmail.com
 * @since 2019-09-11
 */
interface LiveEventObserver<T>{
    /**
     * 发送消息
     * @param value T
     */
    fun post(value: T)

    /**
     * 跨进程发送消息
     * @param value T
     */
    fun broadcast(context : Context, value: T)

    /**
     * 订阅
     * @param lifecycleOwner LifecycleOwner
     * @param observer Observer<T>
     */
    fun observe(lifecycleOwner: LifecycleOwner, observer: Observer<T>)
    /**
     * 粘性订阅
     * @param lifecycleOwner LifecycleOwner
     * @param observer Observer<T>
     */
    fun observeSticky(lifecycleOwner: LifecycleOwner, observer: Observer<T>)

    /**
     * Adds the given observer to the observers list. This call is similar to
     * {@link LiveData#observe(LifecycleOwner, Observer)} with a LifecycleOwner, which
     * is always active. This means that the given observer will receive all events and will never
     * be automatically removed. You should manually call {@link #removeObserver(Observer)} to stop
     * observing this LiveData.
     * @param observer Observer<T>
     */
    fun observeForever(observer: Observer<T>)

    /**
     * 支持粘性
     * @param observer Observer<T>
     */
    fun observeStickyForever(observer: Observer<T>)

    /**
     * Removes the given observer from the observers list.
     * @param observer Observer<T>
     */
    fun removeObserver(observer: Observer<T>)
}