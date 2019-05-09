package com.jgorozco.extraintent.data

class InterActivityData{
    companion object {
        val instance = InterActivityData()
    }
    var timeBeforeSetExtra:Long = 0
    var timeAfterSetExtra:Long = 0
    var timeBeforeGetExtra:Long = 0
    var timeAfterGetExtra:Long = 0
    var timePauseFirstActivity:Long = 0
    var timeCreateSecondActivity:Long = 0
    var timeStartSecondActivity:Long = 0
    var isAutomate:Boolean = false
    var serialType:String = ""
    var objectSize:Int = 0
    var objectHash:Int = 0
    var objectClassName:String =""

    fun totalTime():Long =  timeAfterSetExtra - timeBeforeGetExtra
    fun serialTime():Long =  timeAfterSetExtra - timeBeforeSetExtra
    fun unserialTime():Long =  timeAfterGetExtra - timeBeforeGetExtra
    fun interActivityTime():Long =  timeCreateSecondActivity -timeAfterGetExtra
    fun fromPauseToStart():Long =  timeStartSecondActivity -timePauseFirstActivity

    override fun toString():String{
        return "TOTAL:${totalTime()}    " +
                "serial:${serialTime()}   " +
                "unserial:${unserialTime()} " +
                "interactiviy:${interActivityTime()}    " +
                "completeActivity:${fromPauseToStart()} " +
                "object size:$objectSize    "+
                "serial type:$serialType "
    }
}
