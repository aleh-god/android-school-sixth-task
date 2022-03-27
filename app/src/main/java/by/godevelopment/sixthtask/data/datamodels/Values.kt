package by.godevelopment.sixthtask.data.datamodels


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Values(
    @Json(name = "none")
    val none: String?,
    @Json(name = "v1")
    val v1: String?,
    @Json(name = "v2")
    val v2: String?,
    @Json(name = "v3")
    val v3: String?
)