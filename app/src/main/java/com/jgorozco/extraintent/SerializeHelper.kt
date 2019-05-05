package com.jgorozco.extraintent

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
import com.squareup.moshi.Moshi

class SerializeHelper {
    companion object {
        val instance = SerializeHelper()
    }

    private val gson = Gson()
    private val jackson = ObjectMapper()
    private val moshi =  Moshi.Builder().build()

    fun <T> fromJson(jsonString:String, objectClass:Class<T>, jsonTypes:  SerialType ):T?{
        return when (jsonTypes){
            SerialType.GSON -> gson.fromJson(jsonString,objectClass)
            SerialType.JACKSON ->jackson.readValue(jsonString,objectClass)
            SerialType.MOSHI -> moshi.adapter(objectClass).fromJson(jsonString)
            else -> null
        }
    }

    fun <T>toJson(it:T,objectClass:Class<T>,jsonTypes: SerialType):String{
        return when (jsonTypes){
            SerialType.GSON -> gson.toJson(it)
            SerialType.JACKSON -> jackson.writeValueAsString(it)
            SerialType.MOSHI -> Moshi.Builder().build().adapter(objectClass).toJson(it)
            else -> ""
        }
    }
}