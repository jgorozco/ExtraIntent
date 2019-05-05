package com.jgorozco.extraintent

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.jgorozco.extraintent.data.Response
import com.jgorozco.extraintent.intentextra.IntentExtra
import com.jgorozco.extraintent.intentextra.SerialType
import com.jgorozco.extraintent.intentextra.SerializeHelper
import kotlinx.android.synthetic.main.activity_main.*

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
        val file_name = "small.json"
        val json_string = application.assets.open(file_name).bufferedReader().use{
            it.readText()
        }
        val objectToShow=
            SerializeHelper.instance.fromJson(json_string,Response::class.java, SerialType.GSON )
        if (objectToShow!=null) {
            val intent = Intent(this, ResultActivity::class.java)
            IntentExtra.instance.setExtra(intent,"object", objectToShow!!, Response::class.java, SerialType.GSON)
            startActivity(intent)
        }else{
            Toast.makeText(this,"null object!!",Toast.LENGTH_LONG).show()
        }


    }
}
