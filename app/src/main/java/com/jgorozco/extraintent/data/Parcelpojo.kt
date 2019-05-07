package com.jgorozco.extraintent.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseParcel(
    val results: List<CharacterItemParcel> = emptyList(),
    val info: InfoParcel
) : Parcelable

@Parcelize
data class CharacterItemParcel(
    val image: String,
    val gender: String,
    val species: String,
    val created: String,
    val origin: OriginParcel,
    val name: String,
    val location: LocationParcel,
    val episode: List<String>,
    val id: Int,
    val type: String,
    val url: String,
    val status: String
) : Parcelable

@Parcelize
data class OriginParcel(
    val name: String,
    val url: String
) : Parcelable

@Parcelize
data class InfoParcel(
    val next: String,
    val pages: Int,
    val prev: String,
    val count: Int
) : Parcelable

@Parcelize
data class LocationParcel(
    val name: String,
    val url: String
) : Parcelable

