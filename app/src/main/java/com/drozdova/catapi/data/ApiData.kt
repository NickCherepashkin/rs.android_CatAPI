package com.drozdova.catapi.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiData(
    val id: String?,
    @Json(name = "url") val url: String?,
    val width: String?,
    val height: String?
)
