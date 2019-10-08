package com.example.smaboy.layouthelper.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import butterknife.ButterKnife
import butterknife.Unbinder

/**
 *
 * <ur>
 *     <li> 类名: BaseFragment</li>
 *     <li> 类作用描述: java类作用描述 </li>
 *     <li> 页面名称: 页面描述 </li>
 *     <li> 作者: <a href="mailto:liyongliang3@ceair.com">Li Yongliang</a></li>
 *     <li> 创建时间: 2019/8/27 11:12</li>
 * </ur>
 *
 */
abstract class BaseFragment : Fragment() {
    lateinit var mRootView : View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //获取内容view
        mRootView = inflater.inflate(getContentViewId(), container, false)

        //黄油刀绑定
        ButterKnife.bind(this, mRootView)

        //初始化操作
        init(savedInstanceState)

        return mRootView

    }

    override fun onDestroyView() {
        super.onDestroyView()

    }

    /**
     * 获取fragment的view
     */
    abstract fun getContentViewId(): Int

    /**
     * 初始化操作
     */
    abstract fun init(savedInstanceState: Bundle?)

    /**
     * 设置数据
     */
    abstract fun setData()
}