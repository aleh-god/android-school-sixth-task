package by.godevelopment.sixthtask.data.datamodels

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MetaModel(
    @Json(name = "fields")
    val fields: List<Field>?,
    @Json(name = "image")
    val image: String?,
    @Json(name = "title")
    val title: String?
)