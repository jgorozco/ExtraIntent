package com.jgorozco.extraintent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.jgorozco.extraintent.data.InterActivityData
import com.jgorozco.extraintent.data.Response
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

        val resultado:Response= IntentExtra.instance.getExtra(intent,EXTRA_NAME, Response::class.java,
            SerialType.valueOf(InterActivityData.actual.serialType))!!
        InterActivityData.actual.timeAfterSetExtra = Date().time

        element_number.text = resultado.results.size.toString()
        is_ok_object.text = (resultado.hashCode() == InterActivityData.actual.objectHash).toString()
        total_time.text =  InterActivityData.actual.totalTime().toString()
        InterActivityData.actual.timeStartSecondActivity = Date().time
        Log.d("AAAAAA",InterActivityData.actual.toString())

    }
}
