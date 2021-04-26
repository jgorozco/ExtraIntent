package com.jgorozco.extraintent.data

import java.io.Serializable

data class ResponseSerial(
    val results: List<CharacterItemSerial> = emptyList(),
    val info: InfoSerial
):Serializable

data class CharacterItemSerial(
    val image: String,
    val gender: String,
    val species: String,
    val created: String,
    val origin: OriginSerial,
    val name: String,
    val location: LocationSerial,
    val episode: List<String>,
    val id: Int,
    val type: String,
    val url: String,
    val status: String
):Serializable

data class OriginSerial(
    val name: String,
    val url: String
):Serializable

data class InfoSerial(
    val next: String,
    val pages: Int,
    val prev: String,
    val count: Int
):Serializable

data class LocationSerial(
    val name: String,
    val url: String
):Serializable

