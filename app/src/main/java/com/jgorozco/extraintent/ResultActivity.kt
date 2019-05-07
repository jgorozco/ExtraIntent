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
        InterActivityData.actual.timeCreateSecondActivity = Date().time

        setContentView(R.layout.activity_result)
        InterActivityData.actual.timeBeforeSetExtra = Date().time

        val serialType = SerialType.valueOf(InterActivityData.actual.serialType)
        when (serialType){
            SerialType.PARCEL -> extractParcel()
            SerialType.SERIALIZE -> extractSerial()
            else -> extractJson()
        }
        InterActivityData.actual.timeAfterSetExtra = Date().time
        InterActivityData.actual.timeStartSecondActivity = Date().time
        Log.d("AAAAAA",InterActivityData.actual.toString())

    }

    private fun extractSerial() {
        val resultado:ResponseSerial = intent.getSerializableExtra(EXTRA_NAME) as ResponseSerial
        InterActivityData.actual.timeAfterSetExtra = Date().time
        element_number.text = resultado.results.size.toString()
        is_ok_object.text = (resultado.hashCode() == InterActivityData.actual.objectHash).toString()
        total_time.text =  InterActivityData.actual.totalTime().toString()    }

    private fun extractParcel() {
        val resultado:ResponseParcel = intent.getParcelableExtra(EXTRA_NAME)
        InterActivityData.actual.timeAfterSetExtra = Date().time
        element_number.text = resultado.results.size.toString()
        is_ok_object.text = (resultado.hashCode() == InterActivityData.actual.objectHash).toString()
        total_time.text =  InterActivityData.actual.totalTime().toString()
    }

    private fun extractJson() {
        val resultado:Response= IntentExtra.instance.getExtra(intent,EXTRA_NAME, Response::class.java,
            SerialType.valueOf(InterActivityData.actual.serialType))!!
        element_number.text = resultado.results.size.toString()
        is_ok_object.text = (resultado.hashCode() == InterActivityData.actual.objectHash).toString()
        total_time.text =  InterActivityData.actual.totalTime().toString()

    }
}
