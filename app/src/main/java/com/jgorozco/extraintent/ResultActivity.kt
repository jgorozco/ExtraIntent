package com.jgorozco.extraintent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jgorozco.extraintent.data.Response
import com.jgorozco.extraintent.intentextra.IntentExtra
import com.jgorozco.extraintent.intentextra.SerialType
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        val resultado:Response= IntentExtra.instance.getExtra(intent,"object", Response::class.java,
            SerialType.GSON)!!
        element_number.text = resultado.results.size.toString()

    }
}
