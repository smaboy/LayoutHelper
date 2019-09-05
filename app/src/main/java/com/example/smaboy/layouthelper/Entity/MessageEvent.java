package com.example.smaboy.layouthelper.Entity;

import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

/**
 * <ur>
 * <li> 类名: MessageEvent</li>
 * <li> 类作用描述: java类作用描述 </li>
 * <li> 页面名称: 页面描述 </li>
 * <li> 作者: <a href="mailto:liyongliang3@ceair.com">Li Yongliang</a></li>
 * <li> 创建时间: 2019/9/3 9:59</li>
 * </ur>
 */
public  class MessageEvent {


    private int code;
    private String arg1;
    private String arg2;
    private String arg3;
    private Object object;

    public MessageEvent() {
    }

    public MessageEvent(String arg1) {
        this.arg1 = arg1;
    }

    public MessageEvent(String arg1, String arg2, String arg3) {
        this.arg1 = arg1;
        this.arg2 = arg2;
        this.arg3 = arg3;
    }

    public MessageEvent(String arg1, String arg2, String arg3, Object object) {
        this.arg1 = arg1;
        this.arg2 = arg2;
        this.arg3 = arg3;
        this.object = object;
    }

    public String getArg1() {
        return arg1;
    }

    public void setArg1(String arg1) {
        this.arg1 = arg1;
    }

    public String getArg2() {
        return arg2;
    }

    public void setArg2(String arg2) {
        this.arg2 = arg2;
    }

    public String getArg3() {
        return arg3;
    }

    public void setArg3(String arg3) {
        this.arg3 = arg3;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
