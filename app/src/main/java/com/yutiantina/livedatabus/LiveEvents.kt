package com.yutiantina.livedatabus

/**
 *
 * @author yutiantian email: yutiantina@gmail.com
 * @since 2019-09-18
 */
interface LiveEvents {
    fun testObserveInMain(): LiveEventObserver<String>
    fun testObserveInThread(): LiveEventObserver<String>
    fun testObserveStickyInMain(): LiveEventObserver<String>
    fun testOberverForever(): LiveEventObserver<String>

    /**
     * 方法名即事件名
     * @return LiveEventObserver<Boolean> 事件类型
     */
    fun event1(): LiveEventObserver<Boolean>
    fun event2(): LiveEventObserver<MutableList<String>>

    fun ipcEvent() : LiveEventObserver<IPCEntity>

    fun second2Main(): LiveEventObserver<String>
}