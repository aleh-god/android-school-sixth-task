package by.godevelopment.sixthtask.domain.usecases

import by.godevelopment.sixthtask.data.datamodels.RequestModel
import by.godevelopment.sixthtask.data.datamodels.ResultModel
import by.godevelopment.sixthtask.domain.models.ListItemModel
import javax.inject.Inject

class ConvertResultToFormModelUseCase @Inject constructor(
    private val sendFormModelToRemoteUseCase: SendFormModelToRemoteUseCase
) {
    @Suppress("UNCHECKED_CAST")
    suspend operator fun invoke(result: List<ListItemModel>): ResultModel {
        val resultWithoutNull = result
            .filter { it.result != null }
            .associate { model ->
                model.name to model.result
            } as Map<String, String>
        return sendFormModelToRemoteUseCase(RequestModel(form = resultWithoutNull))
    }
}
