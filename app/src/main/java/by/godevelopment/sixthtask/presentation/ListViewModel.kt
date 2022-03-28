package by.godevelopment.sixthtask.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.godevelopment.sixthtask.R
import by.godevelopment.sixthtask.commons.INIT_STRING_VALUE
import by.godevelopment.sixthtask.commons.TAG
import by.godevelopment.sixthtask.data.datamodels.MetaModel
import by.godevelopment.sixthtask.domain.helpers.StringHelper
import by.godevelopment.sixthtask.domain.usecases.GetMetaModelUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class ListViewModel @Inject constructor(
    private val getMetaModelUseCase: GetMetaModelUseCase,
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
            getMetaModelUseCase()
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
                        dataList = it.fields?.map { filed ->
                            filed.type ?: INIT_STRING_VALUE
                        } ?: listOf()
                    )
                }
        }
    }

    data class UiState(
        val isFetchingData: Boolean = false,
        val title: String = INIT_STRING_VALUE,
        val imageLink: String = INIT_STRING_VALUE,
        val dataList: List<String> = listOf()
    )
}