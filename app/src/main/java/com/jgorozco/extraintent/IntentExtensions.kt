package com.jgorozco.extraintent

import android.content.Intent
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.Gson
import java.io.Serializable
import java.lang.reflect.Type


fun <T> Intent.putSmallObjectExtra(name: String, it: T,serialType: SerialType):Boolean {
    return when (serialType){
        SerialType.GSON,SerialType.JACKSON,SerialType.MOSHI -> putSimpleExtra(name,it,serialType)
        SerialType.GSON_ZIP,SerialType.JACKSON_ZIP,SerialType.MOSHI_ZIP -> putSimpleZipExtra(name,it,serialType)
        SerialType.PARCEL,SerialType.SERIALIZE -> putSerializeExtra(name,it,serialType)
    }
}

private fun <T> Intent.putSerializeExtra(name: String, it: T, serialType: SerialType):Boolean {
    return when (it) {
        is Serializable -> {
            putExtra(name,it)
            true
        }
        is Parcelable -> {
            putExtra(name,it)
            true
        }
        else -> false
    }

}


private fun <T> Intent.putSimpleZipExtra(name: String, it: T, serialType: SerialType):Boolean {

}

private fun <T> Intent.putSimpleExtra(name: String, it: T, serialType: SerialType):Boolean {


}

fun <T> Intent.getSmallObjectExtra(name: String, typeOfObject: Type,serialType: SerialType): T {
    return Gson().fromJson(getStringExtra(name), typeOfObject)
}
