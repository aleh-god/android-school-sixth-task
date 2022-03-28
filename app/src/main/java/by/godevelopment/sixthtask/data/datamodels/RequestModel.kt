package by.godevelopment.sixthtask.data.datamodels

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RequestModel (
    val form: Map<String, String>
)