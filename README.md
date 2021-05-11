### 介绍

[LiveDataBus]是基于LiveData开发的事件总线框架,支持androidx, 它区别于EventBus和RxBus, 有以下几个优点:

1. 具备生命周期感知能力
2. 代码量少
3. 具备消息发送和订阅的约束能力

### 使用

| 主要方法                                                     |                                                              |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| post(value: T)                                               | 发送消息内容                                                 |
| observe(lifecycleOwner: LifecycleOwner, observer: Observer<T>) | 消息订阅, `具备生命周期感知能力`, onDestroy的时候回自动取消订阅 |
| observeSticky(lifecycleOwner: LifecycleOwner, observer: Observer<T>) | `粘性`消息订阅, `具备生命周期感知能力`, onDestroy的时候会自动取消订阅 |
| observeForever(observer: Observer<T>)                        | 消息订阅.` 不具备生命周期感知能力`, 取消订阅需要手动执行removeObserver方法 |
| observeStickyForever(observer: Observer<T>)                  | `粘性`消息订阅, `不具备生命周期感知能力`, 取消订阅需要手动执行removeObesrver方法 |
| removeObserver(observer: Observer<T>)                        | 消息以Forever形式订阅的时候, 需要手动调用取消订阅, 防止内存泄漏 |
| broadcast(context: Context, value: T)                        | 跨进程发送事件

#### 最佳体验

建议每个组件定义自己的事件集合, 若是跨组件事件, 可以api形式定义

``` kotlin
/**
* 事件集合
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
}
```

发送对应事件消息

``` kotlin
private fun sendEvent(){
        LiveDataBus
            .get()
            .of(LiveEvents::class.java)
            .event1()
            .post(true)
    }
```

若需要支持多进程通信, 则需要首先进行初始化配置
``` kotlin
class MainApp : Application() {
    override fun onCreate() {
        super.onCreate()
        LiveDataBus.init()
            .supportMultiProcess(this)
    }
}
```
然后发送对应的跨进程事件消息
``` kotlin
fun sendIpc(view: View) {
        LiveDataBus.get()
            .of(LiveEvents::class.java)
            .ipcEvent()
            .broadcast(this, IPCEntity("from IPCActivity"))
    }
```

订阅对应事件消息

如果是以非forever方式订阅, 则无需处理生命周期的解绑动作, LiveDataBus会自行处理. 若是使用forever方式订阅, 则需要手动在onDestroy方法内进行观察者移除动作

``` kotlin
private fun observe(){
        LiveDataBus
            .get()
            .of(LiveEvents::class.java)
            .event1()
            .observe(this, Observer { 
                Log.i(LOG, it.toString())
            })
    }
```