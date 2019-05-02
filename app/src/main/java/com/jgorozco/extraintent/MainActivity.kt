package com.jgorozco.extraintent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initAdapters()
    }

    private fun initAdapters() {
        spSerializeType.adapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,SerialType.values())
        spDataToSend.adapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,DataToSend.values())
    }

    fun clickSendData(view: View){

    }
}
