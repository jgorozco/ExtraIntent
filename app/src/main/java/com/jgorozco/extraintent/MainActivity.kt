package com.jgorozco.extraintent

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.jgorozco.extraintent.data.InterActivityData
import com.jgorozco.extraintent.data.Response
import com.jgorozco.extraintent.intentextra.IntentExtra
import com.jgorozco.extraintent.intentextra.SerialType
import com.jgorozco.extraintent.intentextra.SerializeHelper
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

const val EXTRA_NAME ="object_extra_name"
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sendData.setOnClickListener { clickSendData(it) }
        initAdapters()
    }

    private fun initAdapters() {
        spSerializeType.adapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,
            SerialType.values())
        spDataToSend.adapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,DataToSend.values())
    }

    fun clickSendData(view: View){
        val dataToSend:DataToSend = DataToSend.valueOf(spDataToSend.selectedItem.toString())
        val json_string = application.assets.open(dataToSend.jsonFile+".json").bufferedReader().use{
            it.readText()
        }
        val objectToShow=
            SerializeHelper.instance.fromJson(json_string,Response::class.java, SerialType.GSON )


        val serialType:SerialType = SerialType.valueOf(spSerializeType.selectedItem.toString())

        if (objectToShow!=null) {
            InterActivityData.actual.serialType = serialType.name
            InterActivityData.actual.objectHash= objectToShow.hashCode()
            val intent = Intent(this, ResultActivity::class.java)
            InterActivityData.actual.timeBeforeGetExtra = Date().time
            IntentExtra.instance.setExtra(intent,EXTRA_NAME, objectToShow!!, Response::class.java, serialType)
            InterActivityData.actual.timeAfterGetExtra = Date().time
            startActivity(intent)
        }else{
            Toast.makeText(this,"null object!!",Toast.LENGTH_LONG).show()
        }
    }

    override fun onPause() {
        super.onPause()
        InterActivityData.actual.timePauseFirstActivity = Date().time
    }
}
