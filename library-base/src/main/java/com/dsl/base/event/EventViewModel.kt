package me.hgj.jetpackmvvm.demo.app.event

import com.dsl.base.viewmodel.BaseViewModel
import com.dsl.callback.livedata.event.EventLiveData


/**
 * 作者　: hegaojian
 * 时间　: 2019/5/2
 * 描述　:APP全局的ViewModel，可以在这里发送全局通知替代EventBus，LiveDataBus等
 */
class EventViewModel : BaseViewModel() {

    //全局收藏，在任意一个地方收藏或取消收藏，监听该值的界面都会收到消息

    //分享文章通知
    val shareArticleEvent = EventLiveData<Boolean>()

    //添加TODO通知
    val todoEvent = EventLiveData<Boolean>()

}