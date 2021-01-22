package com.smaboy.lib_http.interceptor

import com.smaboy.lib_http.constant.APIConstant
import okhttp3.Interceptor
import okhttp3.Response

/**
 * 类名: BaseUrlInterceptor
 *
 * 类作用描述: BaseUrl拦截器，通过在请求体设置 base_url_name ：$value
 *
 * 来处理baseUrl
 *
 * 作者: liyongliang3
 *
 * 创建时间: 2021/1/22 1:30 PM
 *
 */
class BaseUrlInterceptor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        //获取request
        val request = chain.request()
        //获取builder
        val newBuilder = request.newBuilder()
        val headers = request.headers(APIConstant.BASE_URL_NAME)
        if (!headers.isNullOrEmpty()&&headers.size > 0){
            //如果有这个header，先将配置的header删除，因此header仅用作app和okhttp之间使用
            newBuilder.removeHeader(APIConstant.BASE_URL_NAME)
            //匹配获得新的BaseUrl(这里我们取第一个)
            val headerValue = headers[0]
            if (headerValue == APIConstant.BaseUrlType.PAY_BASE_URL.value()){
                //支付
                newBuilder.url(APIConstant.BaseUrlType.PAY_BASE_URL.url())

            }else{
                //默认
                newBuilder.url(APIConstant.BaseUrlType.DEF_BASE_URL.url())
            }

            return chain.proceed(newBuilder.build())

        }else{
            return chain.proceed(request)
        }
    }
}