package com.smaboy.lib_http.entity.responses

import com.google.gson.annotations.SerializedName

/**
 * 类名: HomeArticleListResp

 * 类作用描述: java类作用描述

 * 作者: liyongliang3

 * 创建时间: 2021/1/21 3:45 PM
 *
 */
data class HomeArticleListResp(@SerializedName("errorCode") var errorCode : Int = 0)
