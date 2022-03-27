package by.godevelopment.sixthtask.data

import by.godevelopment.sixthtask.data.datamodels.MetaModel
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MetaApi {

    @GET("meta")
    suspend fun getMetaModel(): MetaModel

//    @POST("data")
//    suspend fun sendMetaModel(@Body metaModel: MetaModel)
}

// { "title": "Форма тестового задания", "image": "http://test.clevertec.ru/tt/image.png", "fields": [ { "title": "Текстовое поле", "name": "text", "type": "TEXT" }, { "title": "Числовое поле", "name": "numeric", "type": "NUMERIC" }, { "title": "Поле выбора одного значения из списка", "name": "list", "type": "LIST", "values": { "none": "Не выбрано", "v1": "Первое значение", "v2": "Второе значение", "v3": "Третье значение" } } ] }