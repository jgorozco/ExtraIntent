package com.jgorozco.extraintent.intentextra

import android.content.Intent
import android.os.Parcelable
import java.io.ByteArrayOutputStream
import java.io.Serializable
import java.util.zip.GZIPInputStream
import java.util.zip.GZIPOutputStream

class IntentExtra {
    companion object {
        val instance = IntentExtra()
    }

    fun <T> setExtra(intent:Intent, name: String, it: T, objectClass:Class<T>, serialType: SerialType): Intent {
        return when (serialType){
            SerialType.GSON, SerialType.JACKSON, SerialType.MOSHI -> putSimpleExtra(intent,name,it,objectClass,serialType)
            SerialType.GSON_ZIP, SerialType.JACKSON_ZIP, SerialType.MOSHI_ZIP -> putSimpleZipExtra(intent,name,it,objectClass,serialType)
            SerialType.PARCEL, SerialType.SERIALIZE -> putSerializeExtra(intent,name,it,serialType)
        }
    }

    fun <T> getExtra(intent:Intent, name: String, objectClass:Class<T>, serialType: SerialType): T? {
        return when (serialType){
            SerialType.GSON, SerialType.JACKSON, SerialType.MOSHI -> SerializeHelper.instance.fromJson(intent.getStringExtra(name),objectClass,serialType)
            SerialType.GSON_ZIP, SerialType.JACKSON_ZIP, SerialType.MOSHI_ZIP ->  SerializeHelper.instance.fromJson(ungzip(intent.getByteArrayExtra(name)),objectClass,serialType)
            //  SerialType.PARCEL,SerialType.SERIALIZE ->getSerialExtra(name,objectClass,serialType)
            else -> null
        }
    }


    private fun gzip(content: String): ByteArray {
        val bos = ByteArrayOutputStream()
        GZIPOutputStream(bos).bufferedWriter(Charsets.UTF_8).use { it.write(content) }
        return bos.toByteArray()
    }

    private fun ungzip(content: ByteArray): String =
        GZIPInputStream(content.inputStream()).bufferedReader(Charsets.UTF_8).use { it.readText() }



    private fun <T> putSerializeExtra(intent:Intent,name: String, it: T, serialType: SerialType):Intent {
        return when (it) {
            is Serializable -> {
                intent.putExtra(name,it)
            }
            is Parcelable -> {
                intent.putExtra(name,it)
            }
            else -> intent.putExtra(name,"")
        }
    }

    private fun <T> putSimpleZipExtra(intent:Intent,name: String, it: T,objectClass:Class<T> ,serialType: SerialType):Intent {
        return when (serialType){
            SerialType.GSON_ZIP -> intent.putExtra(name,gzip(
                SerializeHelper.instance.toJson(it,objectClass,
                    SerialType.GSON)))
            SerialType.JACKSON_ZIP -> intent.putExtra(name,gzip(
                SerializeHelper.instance.toJson(it,objectClass,
                    SerialType.JACKSON)))
            SerialType.MOSHI_ZIP -> intent.putExtra(name,gzip(
                SerializeHelper.instance.toJson(it,objectClass,
                    SerialType.MOSHI)))
            else -> intent.putExtra(name,"")
        }

    }

    private fun <T> putSimpleExtra(intent:Intent,name: String, it: T,objectClass:Class<T> , serialType: SerialType):Intent {
        return when (serialType){
            SerialType.GSON -> intent.putExtra(name,
                SerializeHelper.instance.toJson(it,objectClass,
                    SerialType.GSON))
            SerialType.JACKSON -> intent.putExtra(name,
                SerializeHelper.instance.toJson(it,objectClass,
                    SerialType.JACKSON))
            SerialType.MOSHI -> intent.putExtra(name,
                SerializeHelper.instance.toJson(it,objectClass,
                    SerialType.MOSHI))
            else -> intent.putExtra(name,"")
        }
    }

}