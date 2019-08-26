package com.example.smaboy.layouthelper.base

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.fragment.app.FragmentActivity
import com.example.smaboy.layouthelper.R
import com.yanzhenjie.sofia.Bar
import com.yanzhenjie.sofia.Sofia

/**
 *
 * <ur>
 *     <li> 类名: BaseActivity</li>
 *     <li> 类作用描述: java类作用描述 </li>
 *     <li> 页面名称: 页面描述 </li>
 *     <li> 作者: <a href="mailto:liyongliang3@ceair.com">Li Yongliang</a></li>
 *     <li> 创建时间: 2019/8/26 15:29</li>
 * </ur>
 *
 */
abstract class BaseActivity : FragmentActivity() {
    lateinit var bar :Bar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //设置内容
        setContentView(getLayoutViewId())

        //初始化内容
        initViewId()


        //设置沉浸式状态栏
        bar = Sofia.with(this)
            .statusBarDarkFont()//状态栏深色字体
            .statusBarBackgroundAlpha(0)//状态栏透明度为0
            .navigationBarBackgroundAlpha(0)//导航栏透明度为0
            .invasionStatusBar()//内容入侵状态栏
            .invasionNavigationBar()//内容入侵导航栏


        //设置数据
        setData()
    }

    //添加布局
    abstract fun getLayoutViewId() : Int
    //初始化view
    abstract fun initViewId()

    //设置数据
    abstract fun setData()


}