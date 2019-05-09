package com.jgorozco.extraintent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.jgorozco.extraintent.data.InterActivityData
import com.jgorozco.extraintent.data.Response
import com.jgorozco.extraintent.data.ResponseParcel
import com.jgorozco.extraintent.data.ResponseSerial
import com.jgorozco.extraintent.intentextra.IntentExtra
import com.jgorozco.extraintent.intentextra.SerialType
import kotlinx.android.synthetic.main.activity_result.*
import java.util.*

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        InterActivityData.instance.timeCreateSecondActivity = Date().time

        setContentView(R.layout.activity_result)
        InterActivityData.instance.timeBeforeSetExtra = Date().time

        val serialType = SerialType.valueOf(InterActivityData.instance.serialType)
        when (serialType){
            SerialType.PARCEL -> extractParcel()
            SerialType.SERIALIZE -> extractSerial()
            else -> extractJson()
        }
        InterActivityData.instance.timeAfterSetExtra = Date().time
        InterActivityData.instance.timeStartSecondActivity = Date().time
        Log.d("AAAAAA",InterActivityData.instance.toString())
        if (InterActivityData.instance.isAutomate){
            finish()
        }

    }

    private fun extractSerial() {
        val resultado:ResponseSerial = intent.getSerializableExtra(EXTRA_NAME) as ResponseSerial
        InterActivityData.instance.timeAfterSetExtra = Date().time
        element_number.text = resultado.results.size.toString()
        is_ok_object.text = (resultado.hashCode() == InterActivityData.instance.objectHash).toString()
        total_time.text =  InterActivityData.instance.totalTime().toString()    }

    private fun extractParcel() {
        val resultado:ResponseParcel = intent.getParcelableExtra(EXTRA_NAME)
        InterActivityData.instance.timeAfterSetExtra = Date().time
        element_number.text = resultado.results.size.toString()
        is_ok_object.text = (resultado.hashCode() == InterActivityData.instance.objectHash).toString()
        total_time.text =  InterActivityData.instance.totalTime().toString()
    }

    private fun extractJson() {
        val resultado:Response= IntentExtra.instance.getExtra(intent,EXTRA_NAME, Response::class.java,
            SerialType.valueOf(InterActivityData.instance.serialType))!!
        element_number.text = resultado.results.size.toString()
        is_ok_object.text = (resultado.hashCode() == InterActivityData.instance.objectHash).toString()
        total_time.text =  InterActivityData.instance.totalTime().toString()

    }
}
