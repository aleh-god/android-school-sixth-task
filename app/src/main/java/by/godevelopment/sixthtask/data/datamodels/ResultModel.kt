package by.godevelopment.sixthtask.data.datamodels

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResultModel (
    @Json(name = "result")
    val result: String
)