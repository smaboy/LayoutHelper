package com.example.smaboy.layouthelper.Entity

/**
 *
 * <ur>
 *     <li> 类名: MessageEvent</li>
 *     <li> 类作用描述: java类作用描述 </li>
 *     <li> 页面名称: 页面描述 </li>
 *     <li> 作者: <a href="mailto:liyongliang3@ceair.com">Li Yongliang</a></li>
 *     <li> 创建时间: 2019/9/5 16:28</li>
 * </ur>
 *
 */
data class MessageEvent @JvmOverloads constructor(
    var what: Int = 0,
    var arg: Int = 0,
    var str: String = "",
    var obj: Any? = null
)