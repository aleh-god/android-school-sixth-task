package by.godevelopment.sixthtask.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.godevelopment.sixthtask.R
import by.godevelopment.sixthtask.commons.INIT_STRING_VALUE
import by.godevelopment.sixthtask.commons.TAG
import by.godevelopment.sixthtask.domain.helpers.StringHelper
import by.godevelopment.sixthtask.domain.models.ListItemModel
import by.godevelopment.sixthtask.domain.usecases.ConvertMetaModelToUiStateModelUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class ListViewModel @Inject constructor(
    private val convertMetaModelToUiStateModelUseCase: ConvertMetaModelToUiStateModelUseCase,
    private val stringHelper: StringHelper
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    private val _uiEvent  = MutableSharedFlow<String>(0)
    val uiEvent: SharedFlow<String> = _uiEvent

    private var fetchJob: Job? = null

    init {
        fetchMetaModel()
    }

    fun fetchMetaModel() {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            convertMetaModelToUiStateModelUseCase()
                .onStart {
                    Log.i(TAG, "viewModelScope.launch: .onStart")
                    _uiState.value = UiState(
                        isFetchingData = true
                    )
                }
                .catch { exception ->
                    Log.i(TAG, "viewModelScope.catch ${exception.message}")
                    _uiState.value = UiState(
                        isFetchingData = false
                    )
                    delay(1000)
                    _uiEvent.emit(stringHelper.getString(R.string.alert_error_loading))
                }
                .collect {
                    Log.i(TAG, "viewModelScope.launch: .collect = $it")
                    _uiState.value = UiState(
                        isFetchingData = false,
                        title = it.title ?: INIT_STRING_VALUE,
                        imageLink = it.image ?: INIT_STRING_VALUE,
                        dataList = it.fields ?: listOf()
                    )
                }
        }
    }

    fun setResultToList(id: Int, key: String, resultOrNull: String?) {
        Log.i(TAG, "MainViewModel setResultToList: $key to $resultOrNull")
        val newList = uiState.value.dataList
            .filter { it.id != id }
            .toMutableList()
        val newItem = uiState.value.dataList
            .first { it.id == id }
            .copy(result = resultOrNull)
        newList.add(newItem)
        newList.sortedBy { it.id }
        _uiState.value = uiState.value.copy(
            dataList = newList
        )
    }

    data class UiState(
        val isFetchingData: Boolean = false,
        val title: String = INIT_STRING_VALUE,
        val imageLink: String = INIT_STRING_VALUE,
        val dataList: List<ListItemModel> = listOf()
    )
}