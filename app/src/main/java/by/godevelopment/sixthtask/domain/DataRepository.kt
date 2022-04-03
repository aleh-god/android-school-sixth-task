package by.godevelopment.sixthtask.domain

import by.godevelopment.sixthtask.data.datamodels.MetaModel
import by.godevelopment.sixthtask.data.datamodels.RequestModel
import by.godevelopment.sixthtask.data.datamodels.ResultModel

interface DataRepository {

    suspend fun getMetaModel(): MetaModel
    suspend fun sendFormModelToRemote(requestModel: RequestModel): ResultModel
}