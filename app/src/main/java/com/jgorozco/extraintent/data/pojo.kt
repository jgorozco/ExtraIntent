package com.jgorozco.extraintent.data

data class Response(
    val results: List<CharacterItem> = emptyList(),
    val info: Info
)
data class CharacterItem(
    val image: String,
    val gender: String,
    val species: String,
    val created: String,
    val origin: Origin,
    val name: String,
    val location: Location,
    val episode: List<String>,
    val id: Int,
    val type: String,
    val url: String,
    val status: String
)
data class Origin(
    val name: String,
    val url: String
)
data class Info(
    val next: String,
    val pages: Int,
    val prev: String,
    val count: Int
)
data class Location(
    val name: String,
    val url: String
)

