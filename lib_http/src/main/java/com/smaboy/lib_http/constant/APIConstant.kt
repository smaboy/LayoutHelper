package com.smaboy.lib_http.constant

/**
 * 类名: APIConstant

 * 类作用描述: 常量类，用于存放api相关常量

 * 作者: liyongliang3

 * 创建时间: 2021/1/22 1:39 PM
 *
 */
object APIConstant {

    /**
     * BaseUrl常量
     */
    enum class BaseUrlType{

        /**
         * 默认的BaseUrl
         */
        DEF_BASE_URL() {
            override fun url() = "https://www.wanandroid.com"
            override fun value() = BASE_URL_DEF
        },
        /**
         * 支付的BaseUrl
         */
        PAY_BASE_URL() {
            override fun url() = "https://www.pay.com"
            override fun value() = BASE_URL_PAY
        };

        abstract fun url(): String
        abstract fun value(): String
    }

    /**
     * 用于放在请求头来标示当前请求的类型，作为key
     */
    const val BASE_URL_NAME = "base_url_name"

    /**
     * 用于放在请求头来标示当前请求的类型，作为value
     */
    const val BASE_URL_PAY = "pay"

    /**
     * 用于放在请求头来标示当前请求的类型，作为value
     */
    const val BASE_URL_DEF = "def"



}