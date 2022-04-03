package by.godevelopment.sixthtask.data.datamodels

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RequestModel (
    @Json(name = "form")
    val form: Map<String, String>
)