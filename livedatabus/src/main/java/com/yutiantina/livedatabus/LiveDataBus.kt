package com.yutiantina.livedatabus

import java.lang.IllegalArgumentException
import java.lang.reflect.InvocationHandler
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Proxy

/**
 * 门户
 * @author yutiantian email: yutiantina@gmail.com
 * @since 2019-09-05
 */
class LiveDataBus {
    internal val config by lazy { LiveDataConfig() }
    internal val liveDatas by lazy { mutableMapOf<String, LiveEventObserver<*>>() }

    @Synchronized
    private fun <T>bus(channel: String): LiveEventObserver<T>{
        return liveDatas.getOrPut(channel){
            LiveDataEvent<T>(channel)
        } as LiveEventObserver<T>
    }

    /**
     * kt 使用
     * @param channel String
     * @return LiveEventObserver<T>
     */
    internal fun <T> with(channel: String): LiveEventObserver<T>{
        return bus(channel)
    }

    /**
     * 提供给Java使用的
     * @param channel String
     * @param clz Class<T>
     * @return LiveEventObserver<T>
     */
    internal fun <T> with(channel: String, clz: Class<T>): LiveEventObserver<T>{
        return bus(channel)
    }

    fun <E> of(clz: Class<E>): E {
        if(!clz.isInterface){
            throw IllegalArgumentException("API declarations must be interfaces.")
        }
        if(0 < clz.interfaces.size){
            throw IllegalArgumentException("API interfaces must not extend other interfaces.")
        }
        return Proxy.newProxyInstance(clz.classLoader, arrayOf(clz), InvocationHandler { _, method, _->
            return@InvocationHandler get().with(
                // 事件名以集合类名_事件方法名定义
                // 可以保证渠道的唯一性
                "${clz.canonicalName}_${method.name}",
                (method.genericReturnType as ParameterizedType).actualTypeArguments[0].javaClass)
        }) as E
    }


    companion object{
        private val INSTANCE by lazy { LiveDataBus() }
        @JvmStatic
        fun get() = INSTANCE

        fun init() : LiveDataConfig{
            return get().config
        }
    }
}


