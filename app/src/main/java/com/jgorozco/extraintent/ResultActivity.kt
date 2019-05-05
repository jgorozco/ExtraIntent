package com.jgorozco.extraintent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jgorozco.extraintent.data.Response
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        val resultado:Response= intent.getSmallObjectExtra("object", Response::class.java,SerialType.GSON)!!
        element_number.text = resultado.results.size.toString()

    }
}
