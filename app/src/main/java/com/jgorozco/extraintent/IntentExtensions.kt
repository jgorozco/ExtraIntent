package com.jgorozco.extraintent

import android.content.Intent
import android.os.Parcelable
import java.io.ByteArrayOutputStream
import java.io.Serializable
import java.util.zip.GZIPInputStream
import java.util.zip.GZIPOutputStream
import kotlin.text.Charsets.UTF_8


fun <T> Intent.putSmallObjectExtra(name: String, it: T,objectClass:Class<T> ,serialType: SerialType):Intent {
    return when (serialType){
        SerialType.GSON,SerialType.JACKSON,SerialType.MOSHI -> putSimpleExtra(name,it,objectClass,serialType)
        SerialType.GSON_ZIP,SerialType.JACKSON_ZIP,SerialType.MOSHI_ZIP -> putSimpleZipExtra(name,it,objectClass,serialType)
        SerialType.PARCEL,SerialType.SERIALIZE -> putSerializeExtra(name,it,serialType)
    }
}

fun <T> Intent.getSmallObjectExtra(name: String, objectClass:Class<T> ,serialType: SerialType): T? {
    return when (serialType){
        SerialType.GSON,SerialType.JACKSON,SerialType.MOSHI -> SerializeHelper.instance.fromJson(getStringExtra(name),objectClass,serialType)
        SerialType.GSON_ZIP,SerialType.JACKSON_ZIP,SerialType.MOSHI_ZIP ->  SerializeHelper.instance.fromJson(ungzip(getByteArrayExtra(name)),objectClass,serialType)
      //  SerialType.PARCEL,SerialType.SERIALIZE ->getSerialExtra(name,objectClass,serialType)
        else -> null
    }
}


private fun <T> Intent.putSerializeExtra(name: String, it: T, serialType: SerialType):Intent {
    return when (it) {
        is Serializable -> {
            putExtra(name,it)
        }
        is Parcelable -> {
            putExtra(name,it)
        }
        else -> putExtra(name,"")
    }
}


private fun <T> Intent.putSimpleZipExtra(name: String, it: T,objectClass:Class<T> ,serialType: SerialType):Intent {
    return when (serialType){
        SerialType.GSON_ZIP -> putExtra(name,gzip(SerializeHelper.instance.toJson(it,objectClass, SerialType.GSON)))
        SerialType.JACKSON_ZIP -> putExtra(name,gzip(SerializeHelper.instance.toJson(it,objectClass, SerialType.JACKSON)))
        SerialType.MOSHI_ZIP -> putExtra(name,gzip(SerializeHelper.instance.toJson(it,objectClass, SerialType.MOSHI)))
        else -> putExtra(name,"")
    }

}

private fun <T> Intent.putSimpleExtra(name: String, it: T,objectClass:Class<T> , serialType: SerialType):Intent {
    return when (serialType){
        SerialType.GSON -> putExtra(name,SerializeHelper.instance.toJson(it,objectClass,SerialType.GSON))
        SerialType.JACKSON -> putExtra(name,SerializeHelper.instance.toJson(it,objectClass,SerialType.JACKSON))
        SerialType.MOSHI -> putExtra(name,SerializeHelper.instance.toJson(it,objectClass,SerialType.MOSHI))
        else -> putExtra(name,"")
    }
}

fun gzip(content: String): ByteArray {
    val bos = ByteArrayOutputStream()
    GZIPOutputStream(bos).bufferedWriter(UTF_8).use { it.write(content) }
    return bos.toByteArray()
}

fun ungzip(content: ByteArray): String =
    GZIPInputStream(content.inputStream()).bufferedReader(UTF_8).use { it.readText() }
