package com.example.smaboy.layouthelper.base;

import androidx.lifecycle.ViewModel;

/**
 * <ur>
 * <li> 类名: BaseViewModel</li>
 * <li> 类作用描述: viewmodel的基类，我们可以在这里添加需要用的公共方法 </li>
 * <li> 页面名称: 页面描述 </li>
 * <li> 作者: <a href="mailto:liyongliang3@ceair.com">Li Yongliang</a></li>
 * <li> 创建时间: 2019/9/27  15:41</li>
 * </ur>
 */
public abstract class BaseViewModel extends ViewModel {


    public BaseViewModel() {

        initViewContent();
    }

    /**
     * 初始化viewmodel的数据
     */
    protected abstract void initViewContent();

}
