package by.godevelopment.sixthtask.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.godevelopment.sixthtask.R
import by.godevelopment.sixthtask.commons.INIT_STRING_VALUE
import by.godevelopment.sixthtask.commons.TAG
import by.godevelopment.sixthtask.domain.helpers.StringHelper
import by.godevelopment.sixthtask.domain.models.*
import by.godevelopment.sixthtask.domain.usecases.ConvertMetaModelToUiStateModelUseCase
import by.godevelopment.sixthtask.domain.usecases.ConvertResultToFormModelUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class ListViewModel @Inject constructor(
    private val convertMetaModelToUiStateModelUseCase: ConvertMetaModelToUiStateModelUseCase,
    private val convertResultToFormModelUseCase: ConvertResultToFormModelUseCase,
    private val stringHelper: StringHelper
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    private val _listState: MutableStateFlow<List<ListItemModel>> = MutableStateFlow(listOf())
    val listState: StateFlow<List<ListItemModel>> = _listState

    private val _uiEvent  = MutableSharedFlow<ListFragmentEvent>(0)
    val uiEvent: SharedFlow<ListFragmentEvent> = _uiEvent

    private var fetchJob: Job? = null

    init {
        fetchMetaModel()
    }

    fun fetchMetaModel() {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            convertMetaModelToUiStateModelUseCase()
                .onStart {
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
                    _uiEvent.emit(SnackbarEvent(stringHelper.getString(R.string.alert_error_loading)))
                }
                .collect {
                    _uiState.value = UiState(
                        isFetchingData = false,
                        title = it.title ?: stringHelper.getString(R.string.alert_error_loading),
                        imageLink = it.image
                    )
                    _listState.value = it.fields ?: listOf()
                }
        }
    }

    fun setResultToList(id: Int, key: String, resultOrNull: String?) {
        Log.i(TAG, "MainViewModel setResultToList $id: $key to $resultOrNull")
        resultOrNull?.let { result ->
            val newList = listState.value.map {
                if (it.id == id) {
                    it.copy(
                        result = result
                    )
                } else it
            }
            Log.i(TAG, "MainViewModel setResultToList: newList.sortedBy = $newList")
            _listState.value = newList
        }
    }

    fun sendFormToRemote() {
        Log.i(TAG, "sendFormToRemote: ${_listState.value.size}")
        viewModelScope.launch {
            try {
                val resultState = _listState.value
                val response = convertResultToFormModelUseCase(resultState).result
                _uiEvent.emit(DialogEvent(response))
                Log.i(TAG, "sendFormToRemote: response = $response")
            } catch (e: Exception) {
                _uiEvent.emit(SnackbarEvent(stringHelper.getString(R.string.alert_error_loading)))
                Log.i(TAG, "sendFormToRemote: catch = ${e.message}")
            }
        }
    }

    data class UiState(
        val isFetchingData: Boolean = false,
        val title: String = INIT_STRING_VALUE,
        val imageLink: String? = null
    )
}