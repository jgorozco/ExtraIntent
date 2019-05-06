package com.jgorozco.extraintent.data

class InterActivityData{
    companion object {
        val actual = InterActivityData()
    }
    var timeBeforeSetExtra:Long = 0
    var timeAfterSetExtra:Long = 0
    var timeBeforeGetExtra:Long = 0
    var timeAfterGetExtra:Long = 0
    var timePauseFirstActivity:Long = 0
    var timeCreateSecondActivity:Long = 0
    var timeStartSecondActivity:Long = 0
    var serialType:String =""
    var objectHash:Int = 0
    var objectClassName:String =""

    fun totalTime():Long =  timeBeforeSetExtra -timeAfterGetExtra
}
