package by.godevelopment.sixthtask.domain

import by.godevelopment.sixthtask.data.datamodels.MetaModel

interface DataRepository {

    suspend fun getMetaModel(): MetaModel
}