package com.example.smaboy.layouthelper.base

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentActivity
import butterknife.ButterKnife
import butterknife.Unbinder
import com.example.smaboy.layouthelper.util.DisplayUtils
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
abstract class BaseActivity : FragmentActivity() {
    lateinit var bar: Bar
    lateinit var mUnbinder: Unbinder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //设置内容
        setContentView(getLayoutViewId())

        //控件绑定
        mUnbinder = ButterKnife.bind(this)

        //初始化内容
        init(savedInstanceState)


        //设置沉浸式状态栏
        bar = Sofia.with(this)
            .statusBarDarkFont()//状态栏深色字体
            .statusBarBackgroundAlpha(0)//状态栏透明度为0
            .navigationBarBackgroundAlpha(0)//导航栏透明度为0
//            .invasionStatusBar()//内容入侵状态栏
//            .invasionNavigationBar()//内容入侵导航栏


        //设置数据
        setData()

    }

    //添加布局
    abstract fun getLayoutViewId(): Int

    //初始化view
    abstract fun init(savedInstanceState: Bundle?)

    //设置数据
    abstract fun setData()


    override fun onDestroy() {
        super.onDestroy()

        //解绑
        mUnbinder.unbind()
    }


}