package by.godevelopment.sixthtask.data

import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val metaApi: MetaApi
) {

    suspend fun getMetaModel() = metaApi.getMetaModel()
}