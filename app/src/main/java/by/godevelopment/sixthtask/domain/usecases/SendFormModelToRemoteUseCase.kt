package by.godevelopment.sixthtask.domain.usecases

import by.godevelopment.sixthtask.data.datamodels.RequestModel
import by.godevelopment.sixthtask.data.datamodels.ResultModel
import by.godevelopment.sixthtask.domain.DataRepository
import javax.inject.Inject

class SendFormModelToRemoteUseCase @Inject constructor(
    private val dataRepository: DataRepository
) {
    suspend operator fun invoke(requestModel: RequestModel): ResultModel
        = dataRepository.sendFormModelToRemote(requestModel)
}