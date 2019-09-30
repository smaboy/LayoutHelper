package com.example.smaboy.layouthelper.base

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import butterknife.ButterKnife
import butterknife.Unbinder
import com.example.smaboy.layouthelper.R
import com.trello.rxlifecycle3.components.support.RxFragmentActivity
import com.yanzhenjie.sofia.Bar
import com.yanzhenjie.sofia.Sofia

/**
 *
 *
 *     <li> 类名: BaseActivity</li>
 *     <li> 类作用描述: java类作用描述 </li>
 *     <li> 页面名称: 页面描述 </li>
 *     <li> 作者: <a href="mailto:liyongliang3@ceair.com">Li Yongliang</a></li>
 *     <li> 创建时间: 2019/8/26 15:29</li>
 *
 *     注意：1.沉浸式状态栏默认样式为：状态栏透明，状态栏深色字体，导航栏透明
 *           2.如需有其它设置，请通过属性[bar]去设置
 *
 *
 *
 */
abstract class BaseActivity<T : BaseViewModel> : RxFragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //设置内容
        setContentView(getLayoutViewId())

        //注解控件绑定
        ButterKnife.bind(this)

        //初始化内容
        init(savedInstanceState)

        //设置数据
        setData()

        //设置观察者
        setObserver()

    }


    //添加布局
    abstract fun getLayoutViewId(): Int

    //初始化view
    abstract fun init(savedInstanceState: Bundle?)

    //设置数据
    abstract fun setData()

    //初始化ViewModel,子类如果传入的类型不是BaseViewModel的子类，则不去初始化
    abstract fun initViewModel(): Class<T>

    //设置观察者
    protected open fun setObserver() {}


    /**
     *  由于startactivity也会调用该方法，故将启动动画的方法放在此处理
     */
    override fun startActivityForResult(intent: Intent?, requestCode: Int) {
        super.startActivityForResult(intent, requestCode)
        overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit_fade)
    }


    /**
     * 添加activity结束时的动画
     */
    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.activity_enter_fade, R.anim.activity_exit)
    }

    /**
     * 获取viewmodel
     */
    fun getViewModel(): T {

        return if (initViewModel() is BaseViewModel) {
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
                .create(initViewModel())
        } else {
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
                .create(initViewModel())
        }
    }

    fun getBar(): Bar {

        return Sofia.with(this)
            .statusBarDarkFont()//状态栏深色字体
            .statusBarBackgroundAlpha(0)//状态栏透明度为0
            .navigationBarBackgroundAlpha(0)//导航栏透明度为0
//            .invasionStatusBar()//内容入侵状态栏
//            .invasionNavigationBar()//内容入侵导航栏
    }

}