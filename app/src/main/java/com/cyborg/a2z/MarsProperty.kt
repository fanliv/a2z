package com.cyborg.a2z

import com.squareup.moshi.Json

data class MarsProperty(
    val id: String, @Json(name = "img_src") val url: String
)