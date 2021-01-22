package com.example.smaboy.layouthelper.Entity.responses
import com.google.gson.annotations.SerializedName

/**
 * 类名: HomeArticleListResp

 * 类作用描述: java类作用描述

 * 作者: liyongliang3

 * 创建时间: 2021/1/21 3:45 PM
 *
 */
data class HomeArticleListResp(
    @SerializedName("data")
    var `data`: Data,
    @SerializedName("errorCode")
    var errorCode: Int,
    @SerializedName("errorMsg")
    var errorMsg: String
)

data class Data(
    @SerializedName("curPage")
    var curPage: Int,
    @SerializedName("datas")
    var datas: List<DataX>,
    @SerializedName("offset")
    var offset: Int,
    @SerializedName("over")
    var over: Boolean,
    @SerializedName("pageCount")
    var pageCount: Int,
    @SerializedName("size")
    var size: Int,
    @SerializedName("total")
    var total: Int
)

data class DataX(
    @SerializedName("apkLink")
    var apkLink: String,
    @SerializedName("audit")
    var audit: Int,
    @SerializedName("author")
    var author: String,
    @SerializedName("canEdit")
    var canEdit: Boolean,
    @SerializedName("chapterId")
    var chapterId: Int,
    @SerializedName("chapterName")
    var chapterName: String,
    @SerializedName("collect")
    var collect: Boolean,
    @SerializedName("courseId")
    var courseId: Int,
    @SerializedName("desc")
    var desc: String,
    @SerializedName("descMd")
    var descMd: String,
    @SerializedName("envelopePic")
    var envelopePic: String,
    @SerializedName("fresh")
    var fresh: Boolean,
    @SerializedName("host")
    var host: String,
    @SerializedName("id")
    var id: Int,
    @SerializedName("link")
    var link: String,
    @SerializedName("niceDate")
    var niceDate: String,
    @SerializedName("niceShareDate")
    var niceShareDate: String,
    @SerializedName("origin")
    var origin: String,
    @SerializedName("prefix")
    var prefix: String,
    @SerializedName("projectLink")
    var projectLink: String,
    @SerializedName("publishTime")
    var publishTime: Long,
    @SerializedName("realSuperChapterId")
    var realSuperChapterId: Int,
    @SerializedName("selfVisible")
    var selfVisible: Int,
    @SerializedName("shareDate")
    var shareDate: Long,
    @SerializedName("shareUser")
    var shareUser: String,
    @SerializedName("superChapterId")
    var superChapterId: Int,
    @SerializedName("superChapterName")
    var superChapterName: String,
    @SerializedName("tags")
    var tags: List<Tag>,
    @SerializedName("title")
    var title: String,
    @SerializedName("type")
    var type: Int,
    @SerializedName("userId")
    var userId: Int,
    @SerializedName("visible")
    var visible: Int,
    @SerializedName("zan")
    var zan: Int
)

data class Tag(
    @SerializedName("name")
    var name: String,
    @SerializedName("url")
    var url: String
)