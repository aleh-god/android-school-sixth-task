package by.godevelopment.sixthtask.domain.usecases

import by.godevelopment.sixthtask.data.datamodels.MetaModel
import by.godevelopment.sixthtask.domain.DataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMetaModelUseCase @Inject constructor(
    private val dataRepository: DataRepository
) {
    operator fun invoke(): Flow<MetaModel> = flow {
        emit(dataRepository.getMetaModel())
    }
}