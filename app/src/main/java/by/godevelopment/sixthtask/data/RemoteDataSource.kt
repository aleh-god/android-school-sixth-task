package by.godevelopment.sixthtask.data

import by.godevelopment.sixthtask.data.datamodels.RequestModel
import by.godevelopment.sixthtask.data.datamodels.ResultModel
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val metaApi: MetaApi
) {

    suspend fun getMetaModel() = metaApi.getMetaModel()
    suspend fun sendFormModel(requestModel: RequestModel): ResultModel
        = metaApi.sendFormModel(requestModel)
}