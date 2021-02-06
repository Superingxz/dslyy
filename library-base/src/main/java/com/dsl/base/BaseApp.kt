package com.dsl.base

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.multidex.MultiDexApplication
import com.alibaba.android.arouter.launcher.ARouter
import com.dsl.loadCallBack.EmptyCallback
import com.dsl.loadCallBack.ErrorCallback
import com.dsl.loadCallBack.LoadingCallback
import com.kingja.loadsir.callback.SuccessCallback
import com.kingja.loadsir.core.LoadSir
import com.tencent.mmkv.MMKV

/**
 * @author dsl-abben
 * on 2020/02/20.
 */
open class BaseApp : MultiDexApplication(), ViewModelStoreOwner {
    private lateinit var mAppViewModelStore: ViewModelStore

    private var mFactory: ViewModelProvider.Factory? = null

    override fun getViewModelStore(): ViewModelStore {
        return mAppViewModelStore
    }

    companion object {
        private var appInstance: BaseApp? = null

        var bindJPush = false
        var imConnected: Boolean = false

        /**
         * 是否正处于视频聊天的activity，为视频提醒悬浮窗的显示
         */
        var isInVideoActivity = false

        /**
         * 悬浮窗点击跳转的参数
         */
        var windowOrderId = 0L
        var windowRoomId = ""
        var userCancelVideoOrderId = mutableListOf<String>()

        fun getInstance(): BaseApp {
            if (appInstance == null) {
                throw IllegalStateException("Configure Application class in AndroidManifest.xml")
            }
            return appInstance!!
        }
    }

    override fun onCreate() {
        super.onCreate()
        mAppViewModelStore = ViewModelStore()
        appInstance = this
        MMKV.initialize(this.filesDir.absolutePath + "/mmkv")
        // 界面加载管理 初始化
        LoadSir.beginBuilder()
            .addCallback(LoadingCallback()) // 加载
            .addCallback(ErrorCallback()) // 错误
            .addCallback(EmptyCallback()) // 空
            .setDefaultCallback(SuccessCallback::class.java) // 设置默认加载状态页
            .commit()
        // 判断是否是在主线程
//        if (SessionWrapper.isMainProcess(applicationContext)) {
//            //初始化腾讯云IM
//            val sdkAppId = if (BuildConfig.BUILD_VARIANTS_RELEASES) 1400320074 else 1400340742
//            val config = TIMSdkConfig(sdkAppId)
//                .enableLogPrint(true)
//                .setLogLevel(TIMLogLevel.DEBUG)
//                .setLogPath(Environment.getExternalStorageDirectory().path + "/dsldoctorplus_im/")
//            TIMManager.getInstance().init(applicationContext, config)
//            BackgroundTasks.initInstance()
//        }
//        if (BuildConfig.BUILD_VARIANTS_RELEASES) {
//            //初始化Bugly
//            CrashReport.initCrashReport(applicationContext, "?", false)
//            //初始化友盟数据统计
//            UMConfigure.init(this, "?", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "")
//            MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO)
//        }
//        //初始化极光推送
//        JPushUPSManager.registerToken(this, "?", null, "") {
//            //状态码为0则说明调用成功，其它值均为失败
//            DebugLog.i("onCreate初始化极光推送结果: ${it.returnCode}")
//        }

        if (BuildConfig.DEBUG) { // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog() // 打印日志
            ARouter.openDebug() // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this) // 尽可能早，推荐在Application中初始化
    }

    /**
     * 获取一个全局的ViewModel
     */
    fun getAppViewModelProvider(): ViewModelProvider {
        return ViewModelProvider(this, this.getAppFactory())
    }

    private fun getAppFactory(): ViewModelProvider.Factory {
        if (mFactory == null) {
            mFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(this)
        }
        return mFactory as ViewModelProvider.Factory
    }
}
