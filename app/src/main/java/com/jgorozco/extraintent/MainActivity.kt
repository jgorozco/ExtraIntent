package com.jgorozco.extraintent

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.jgorozco.extraintent.data.InterActivityData
import com.jgorozco.extraintent.data.Response
import com.jgorozco.extraintent.data.ResponseParcel
import com.jgorozco.extraintent.data.ResponseSerial
import com.jgorozco.extraintent.intentextra.IntentExtra
import com.jgorozco.extraintent.intentextra.SerialType
import com.jgorozco.extraintent.intentextra.SerializeHelper
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

const val EXTRA_NAME ="object_extra_name"
class MainActivity : AppCompatActivity() {

    val automationTypes = listOf(
        Pair(DataToSend.LIST_SMALL,     SerialType.GSON),
        Pair(DataToSend.LIST_SMALL,     SerialType.GSON),
        Pair(DataToSend.LIST_SMALL,     SerialType.GSON),
        Pair(DataToSend.LIST_BIG,       SerialType.GSON_ZIP),
        Pair(DataToSend.LIST_SMALL,     SerialType.GSON_ZIP),
        Pair(DataToSend.LIST_ULTRABIG,  SerialType.GSON_ZIP),
        Pair(DataToSend.LIST_BIG,       SerialType.GSON_ZIP),
        Pair(DataToSend.LIST_ULTRABIG,  SerialType.GSON_ZIP),
        Pair(DataToSend.LIST_BIG,       SerialType.GSON_ZIP),
        Pair(DataToSend.LIST_ULTRABIG,  SerialType.GSON_ZIP),
        Pair(DataToSend.LIST_BIG,       SerialType.GSON_ZIP),
        Pair(DataToSend.LIST_BIG,       SerialType.GSON_ZIP),
        Pair(DataToSend.LIST_BIG,       SerialType.GSON_ZIP),
        Pair(DataToSend.LIST_SMALL,     SerialType.JACKSON),
        Pair(DataToSend.LIST_SMALL,     SerialType.JACKSON),
        Pair(DataToSend.LIST_SMALL,     SerialType.JACKSON),
        Pair(DataToSend.LIST_SMALL,     SerialType.JACKSON_ZIP),
        Pair(DataToSend.LIST_BIG,       SerialType.JACKSON_ZIP),
        Pair(DataToSend.LIST_ULTRABIG,  SerialType.JACKSON_ZIP),
        Pair(DataToSend.LIST_BIG,       SerialType.JACKSON_ZIP),
        Pair(DataToSend.LIST_ULTRABIG,  SerialType.JACKSON_ZIP),
        Pair(DataToSend.LIST_BIG,       SerialType.JACKSON_ZIP),
        Pair(DataToSend.LIST_ULTRABIG,  SerialType.JACKSON_ZIP),
        Pair(DataToSend.LIST_BIG,       SerialType.JACKSON_ZIP),
        Pair(DataToSend.LIST_BIG,       SerialType.JACKSON_ZIP),
        Pair(DataToSend.LIST_BIG,       SerialType.JACKSON_ZIP),
        Pair(DataToSend.LIST_SMALL,     SerialType.MOSHI),
        Pair(DataToSend.LIST_SMALL,     SerialType.MOSHI),
        Pair(DataToSend.LIST_SMALL,     SerialType.MOSHI_ZIP),
        Pair(DataToSend.LIST_BIG,       SerialType.MOSHI_ZIP),
        Pair(DataToSend.LIST_ULTRABIG,  SerialType.MOSHI_ZIP),
        Pair(DataToSend.LIST_BIG,       SerialType.MOSHI_ZIP),
        Pair(DataToSend.LIST_ULTRABIG,  SerialType.MOSHI_ZIP),
        Pair(DataToSend.LIST_BIG,       SerialType.MOSHI_ZIP),
        Pair(DataToSend.LIST_ULTRABIG,  SerialType.MOSHI_ZIP),
        Pair(DataToSend.LIST_BIG,       SerialType.MOSHI_ZIP),
        Pair(DataToSend.LIST_BIG,       SerialType.MOSHI_ZIP),
        Pair(DataToSend.LIST_BIG,       SerialType.MOSHI_ZIP),
        Pair(DataToSend.LIST_SMALL,     SerialType.PARCEL),
        Pair(DataToSend.LIST_SMALL,     SerialType.PARCEL),
        Pair(DataToSend.LIST_BIG,       SerialType.PARCEL),
        Pair(DataToSend.LIST_BIG,       SerialType.PARCEL),
        Pair(DataToSend.LIST_BIG,       SerialType.PARCEL),
        Pair(DataToSend.LIST_SMALL,     SerialType.SERIALIZE),
        Pair(DataToSend.LIST_SMALL,     SerialType.SERIALIZE),
        Pair(DataToSend.LIST_BIG,       SerialType.SERIALIZE),
        Pair(DataToSend.LIST_BIG,       SerialType.SERIALIZE),
        Pair(DataToSend.LIST_BIG,       SerialType.SERIALIZE)
    )

    var isAutomated = false
    var automateCount = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sendData.setOnClickListener { clickSendData(it) }
        automate.setOnClickListener { clickAutomate(it) }
        initAdapters()
    }

    private fun initAdapters() {
        spSerializeType.adapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,
            SerialType.values())
        spDataToSend.adapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,DataToSend.values())
    }

    fun clickSendData(view: View){
        sendData(Pair(spDataToSend.selectedItem as DataToSend,spSerializeType.selectedItem as SerialType))
    }

    fun  sendData(dataAndType:Pair<DataToSend,SerialType>){
        val json_string = application.assets.open(dataAndType.first.jsonFile+".json").bufferedReader().use{
            it.readText()
        }
        Log.d("BBBBBBB","send data:"+dataAndType.first.jsonFile+" with:"+dataAndType.second.toString())
        InterActivityData.actual.objectSize = json_string.length
        when (dataAndType.second){
            SerialType.PARCEL -> sendParcel(json_string)
            SerialType.SERIALIZE -> sendSerial(json_string)
            else -> sendWithJson(json_string)
        }
    }

    fun clickAutomate(view:View){
        isAutomated = true
        automateCount = 0
        sendAutomate()
    }

    fun sendAutomate(){
        if (isAutomated && automateCount<automationTypes.size){
            Log.d("BBBBBBB","send number:"+automateCount+" with "+automationTypes[automateCount])
            InterActivityData.actual.isAutomate = true
            sendData(automationTypes[automateCount])
            automateCount+=1
        }else{
            InterActivityData.actual.isAutomate = false
            isAutomated = false
            automateCount = 0
        }

    }

    override fun onResume() {
        super.onResume()
        sendAutomate()
    }

    private fun sendSerial(json_string: String) {
        val objectToShow=
            SerializeHelper.instance.fromJson(json_string,ResponseSerial::class.java, SerialType.GSON )
        if (objectToShow!=null) {
            InterActivityData.actual.serialType = SerialType.SERIALIZE.name
            InterActivityData.actual.objectHash= objectToShow.hashCode()
            val intent = Intent(this, ResultActivity::class.java)
            InterActivityData.actual.timeBeforeGetExtra = Date().time
            intent.putExtra(EXTRA_NAME,objectToShow)
            InterActivityData.actual.timeAfterGetExtra = Date().time
            startActivity(intent)
        }else{
            Toast.makeText(this,"null object!!",Toast.LENGTH_LONG).show()
        }
    }

    private fun sendParcel(json_string: String) {
        val objectToShow=
            SerializeHelper.instance.fromJson(json_string,ResponseParcel::class.java, SerialType.GSON )
        if (objectToShow!=null) {
            InterActivityData.actual.serialType = SerialType.PARCEL.name
            InterActivityData.actual.objectHash= objectToShow.hashCode()
            val intent = Intent(this, ResultActivity::class.java)
            InterActivityData.actual.timeBeforeGetExtra = Date().time
            intent.putExtra(EXTRA_NAME,objectToShow)
            InterActivityData.actual.timeAfterGetExtra = Date().time
            startActivity(intent)
        }else{
            Toast.makeText(this,"null object!!",Toast.LENGTH_LONG).show()
        }
    }

    private fun sendWithJson(json_string:String) {
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
