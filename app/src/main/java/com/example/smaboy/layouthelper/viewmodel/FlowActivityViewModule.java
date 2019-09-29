package com.example.smaboy.layouthelper.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.example.smaboy.layouthelper.base.BaseViewModel;

/**
 * <ur>
 * <li> 类名: FlowActivityViewModule</li>
 * <li> 类作用描述: java类作用描述 </li>
 * <li> 页面名称: 页面描述 </li>
 * <li> 作者: <a href="mailto:liyongliang3@ceair.com">Li Yongliang</a></li>
 * <li> 创建时间: 2019/9/27  15:44</li>
 * </ur>
 */
public class FlowActivityViewModule extends BaseViewModel {

    private MutableLiveData<String> test;

    public MutableLiveData<String> getTest() {
        if(null == test) {
            test = new MutableLiveData<>();
        }
        return test;
    }

    /**
     * 测试
     * @param str
     */
    public void getTestInfo(String str){

        test.postValue(str);
    }

    @Override
    protected void initViewContent() {
        test = new MutableLiveData<>();
    }
}
