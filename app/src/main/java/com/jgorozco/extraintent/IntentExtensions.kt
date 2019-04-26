package com.jgorozco.extraintent

import android.content.Intent
import com.google.gson.Gson
import java.lang.reflect.Type

fun <T> Intent.putSmallObjectExtra(name: String, anyObject: T) {
    putExtra(name, Gson().toJson(anyObject))
}

fun <T> Intent.getSmallObjectExtra(name: String, typeOfObject: Type): T {
    return Gson().fromJson(getStringExtra(name), typeOfObject)
}
