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

    fun <T> fromJson(jsonString:String,jsonTypes: JsonTypes):T?{
        return null
    }

    fun <T>toJson(it:T,jsonTypes: JsonTypes):String?{
        return null
    }
}