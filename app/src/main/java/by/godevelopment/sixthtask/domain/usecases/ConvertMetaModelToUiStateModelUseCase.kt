package by.godevelopment.sixthtask.domain.usecases

import android.util.Log
import by.godevelopment.sixthtask.commons.*
import by.godevelopment.sixthtask.domain.models.ListItemModel
import by.godevelopment.sixthtask.domain.models.UiStateModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ConvertMetaModelToUiStateModelUseCase @Inject constructor(
    private val getMetaModelUseCase: GetMetaModelUseCase
) {
    operator fun invoke(): Flow<UiStateModel> {
        return getMetaModelUseCase().map { metaModel ->
            val newFields = metaModel.fields?.mapIndexed { index, field ->
                ListItemModel(
                    id = index,
                    title = field.title !!,
                    name = field.name !!,
                    type = field.type !!,
                    values = field.values,
                    haseFocus = index == 0,
                    itemViewType = convertTextLabelToItemType(field.type)
                )
            }
            UiStateModel(
                image = metaModel.image,
                title = metaModel.title,
                fields = newFields
            )
        }
    }

    private fun convertTextLabelToItemType(label: String?): Int = when (label) {
        "TEXT" -> TEXT_ROW_TYPE
        "NUMERIC" -> NUMERIC_ROW_TYPE
        "LIST" -> LIST_ROW_TYPE
        else -> ERROR_ROW_TYPE
    }
}