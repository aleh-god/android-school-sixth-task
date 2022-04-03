package by.godevelopment.sixthtask.data

import by.godevelopment.sixthtask.data.datamodels.MetaModel
import by.godevelopment.sixthtask.data.datamodels.RequestModel
import by.godevelopment.sixthtask.data.datamodels.ResultModel
import by.godevelopment.sixthtask.domain.DataRepository
import javax.inject.Inject

class DataRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : DataRepository {

    override suspend fun getMetaModel(): MetaModel = remoteDataSource.getMetaModel()
    override suspend fun sendFormModelToRemote(requestModel: RequestModel): ResultModel
        = remoteDataSource.sendFormModel(requestModel)
}