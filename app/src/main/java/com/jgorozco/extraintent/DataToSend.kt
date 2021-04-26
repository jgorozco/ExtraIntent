package com.jgorozco.extraintent

enum class DataToSend(val jsonFile: String) {
    LIST_SMALL("list_small"),
    LIST_BIG("list_big"),
    LIST_ULTRABIG("list_ultrabig")
}