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
        InterActivityData.instance.actual.timeCreateSecondActivity = Date().time
        setContentView(R.layout.activity_result)
        extractExtra()
        sendData.setOnClickListener { sendDataToServer() }
    }

    private fun sendDataToServer() {
        Log.d("AAAAAA","SIZE;"+InterActivityData.instance.totalResult.size)



        InterActivityData.instance.totalResult.clear()
    }

    private fun extractExtra() {
        InterActivityData.instance.actual.timeBeforeSetExtra = Date().time

        val serialType = SerialType.valueOf(InterActivityData.instance.actual.serialType)
        when (serialType) {
            SerialType.PARCEL -> extractParcel()
            SerialType.SERIALIZE -> extractSerial()
            else -> extractJson()
        }
        InterActivityData.instance.actual.timeAfterSetExtra = Date().time
        InterActivityData.instance.actual.timeStartSecondActivity = Date().time
        Log.d("AAAAAA", InterActivityData.instance.actual.toString())
        if (InterActivityData.instance.actual.isAutomate) {
            InterActivityData.instance.totalResult.add(InterActivityData.instance.actual.copy())
            finish()
        }
    }

    private fun extractSerial() {
        val resultado:ResponseSerial = intent.getSerializableExtra(EXTRA_NAME) as ResponseSerial
        InterActivityData.instance.actual.timeAfterSetExtra = Date().time
        element_number.text = resultado.results.size.toString()
        is_ok_object.text = (resultado.hashCode() == InterActivityData.instance.actual.objectHash).toString()
        total_time.text =  InterActivityData.instance.actual.totalTime().toString()    }

    private fun extractParcel() {
        val resultado:ResponseParcel = intent.getParcelableExtra(EXTRA_NAME)
        InterActivityData.instance.actual.timeAfterSetExtra = Date().time
        element_number.text = resultado.results.size.toString()
        is_ok_object.text = (resultado.hashCode() == InterActivityData.instance.actual.objectHash).toString()
        total_time.text =  InterActivityData.instance.actual.totalTime().toString()
    }

    private fun extractJson() {
        val resultado:Response= IntentExtra.instance.getExtra(intent,EXTRA_NAME, Response::class.java,
            SerialType.valueOf(InterActivityData.instance.actual.serialType))!!
        element_number.text = resultado.results.size.toString()
        is_ok_object.text = (resultado.hashCode() == InterActivityData.instance.actual.objectHash).toString()
        total_time.text =  InterActivityData.instance.actual.totalTime().toString()

    }
}
