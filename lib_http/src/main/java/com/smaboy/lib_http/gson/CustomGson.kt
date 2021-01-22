package com.smaboy.lib_http.gson

import com.google.gson.*
import java.lang.ClassCastException
import java.lang.reflect.Type
import kotlin.jvm.Throws

/**
 * 类名: CustomGson

 * 类作用描述: java类作用描述

 * 作者: liyongliang3

 * 创建时间: 2021/1/21 1:30 PM
 *
 */
object CustomGson {


    /**
     * 获取gson实例
     */
    fun build(): Gson = Gson().newBuilder().apply {
        registerTypeAdapter(Boolean::class.java, BooleanDefAdapter())
        registerTypeAdapter(Boolean::class.javaPrimitiveType, BooleanDefAdapter())
        registerTypeAdapter(Int::class.java, IntDefAdapter())
        registerTypeAdapter(Int::class.javaPrimitiveType, IntDefAdapter())
        registerTypeAdapter(Long::class.java, LongDefAdapter())
        registerTypeAdapter(Long::class.javaPrimitiveType, LongDefAdapter())
        registerTypeAdapter(Float::class.java, FloatDefAdapter())
        registerTypeAdapter(Float::class.javaPrimitiveType, FloatDefAdapter())
        registerTypeAdapter(Double::class.java, DoubleDefAdapter())
        registerTypeAdapter(Double::class.javaPrimitiveType, DoubleDefAdapter())
        registerTypeAdapter(String::class.java, StringDefAdapter())
    }.create()
}

//注册boolen、int、double、long、float、string的默认适配器

/**
 * Boolean默认适配器
 * 如果后台返回的是null或者空字符串，我们将它转化为false
 */
class BooleanDefAdapter : JsonDeserializer<Boolean>, JsonSerializer<Boolean> {

    /**
     * 反序列化
     */
    @Throws(
        ClassCastException::class,
        IllegalAccessException::class,
        JsonParseException::class,
        JsonSyntaxException::class
    )
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Boolean {
        //定义为Boolean类型,如果后台返回""或者null,则返回false
        return if ("" == json?.asString || "null" == json?.asString) {
            false
        } else {
            json?.asBoolean ?: false
        }
    }

    /**
     * 序列化
     */
    override fun serialize(
        src: Boolean?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src)
    }


}

/**
 * int默认适配器
 * 如果后台返回的是null或者空字符串，我们将它转化为0
 */
class IntDefAdapter : JsonDeserializer<Int>, JsonSerializer<Int> {

    /**
     * 反序列化
     */
    @Throws(
        ClassCastException::class,
        IllegalAccessException::class,
        JsonParseException::class,
        JsonSyntaxException::class
    )
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Int {
        //定义为Boolean类型,如果后台返回""或者null,则返回false
        return if ("" == json?.asString || "null" == json?.asString) {
            0
        } else {
            json?.asInt ?: 0
        }
    }

    /**
     * 序列化
     */
    override fun serialize(
        src: Int?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src)
    }


}

/**
 * Long默认适配器
 * 如果后台返回的是null或者空字符串，我们将它转化为0L
 */
class LongDefAdapter : JsonDeserializer<Long>, JsonSerializer<Long> {

    /**
     * 反序列化
     */
    @Throws(
        ClassCastException::class,
        IllegalAccessException::class,
        JsonParseException::class,
        JsonSyntaxException::class
    )
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Long {
        //定义为Boolean类型,如果后台返回""或者null,则返回false
        return if ("" == json?.asString || "null" == json?.asString) {
            0L
        } else {
            json?.asLong ?: 0L
        }
    }

    /**
     * 序列化
     */
    override fun serialize(
        src: Long?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src)
    }


}

/**
 * Float默认适配器
 * 如果后台返回的是null或者空字符串，我们将它转化为0f
 */
class FloatDefAdapter : JsonDeserializer<Float>, JsonSerializer<Float> {

    /**
     * 反序列化
     */
    @Throws(
        ClassCastException::class,
        IllegalAccessException::class,
        JsonParseException::class,
        JsonSyntaxException::class
    )
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Float {
        //定义为Boolean类型,如果后台返回""或者null,则返回false
        return if ("" == json?.asString || "null" == json?.asString) {
            0f
        } else {
            json?.asFloat ?: 0f
        }
    }

    /**
     * 序列化
     */
    override fun serialize(
        src: Float?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src)
    }


}

/**
 * Double默认适配器
 * 如果后台返回的是null或者空字符串，我们将它转化为0.00
 */
class DoubleDefAdapter : JsonDeserializer<Double>, JsonSerializer<Double> {

    /**
     * 反序列化
     */
    @Throws(
        ClassCastException::class,
        IllegalAccessException::class,
        JsonParseException::class,
        JsonSyntaxException::class
    )
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Double {
        //定义为Boolean类型,如果后台返回""或者null,则返回false
        return if ("" == json?.asString || "null" == json?.asString) {
            0.00
        } else {
            json?.asDouble ?: 0.00
        }
    }

    /**
     * 序列化
     */
    override fun serialize(
        src: Double?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src)
    }
}


/**
 * String默认适配器
 * 如果后台返回的是null，我们将它转化为""
 */
class StringDefAdapter : JsonDeserializer<String>, JsonSerializer<String> {

    /**
     * 反序列化
     */
    @Throws(
        ClassCastException::class,
        IllegalAccessException::class,
        JsonParseException::class,
        JsonSyntaxException::class
    )
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): String {
        //定义为Boolean类型,如果后台返回""或者null,则返回false
        return if ("null" == json?.asString) {
            ""
        } else {
            json?.asString ?: ""
        }
    }

    /**
     * 序列化
     */
    override fun serialize(
        src: String?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src)
    }


}
