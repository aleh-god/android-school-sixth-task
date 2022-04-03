package by.godevelopment.sixthtask.domain.usecases

import by.godevelopment.sixthtask.data.datamodels.RequestModel
import by.godevelopment.sixthtask.data.datamodels.ResultModel
import javax.inject.Inject

class ConvertResultToFormModelUseCase @Inject constructor(
    private val sendFormModelToRemoteUseCase: SendFormModelToRemoteUseCase
) {
    suspend operator fun invoke(result: Map<String, String>): ResultModel {
        return sendFormModelToRemoteUseCase(RequestModel(form = result))
    }
}