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

    private val _uiEvent  = MutableSharedFlow<String>(0)
    val uiEvent: SharedFlow<String> = _uiEvent

    private var fetchJob: Job? = null
    private val resultState: MutableMap<String, String> = mutableMapOf()

    init {
        fetchMetaModel()
        viewModelScope.launch {
            listState.collect {
                Log.i(TAG, "viewModelScope.launch: listState = $it")
            }
        }
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
                        title = it.title ?: stringHelper.getString(R.string.alert_error_loading),
                        imageLink = it.image
                    )
                    _listState.value = it.fields ?: listOf()
                }
        }
    }

    fun setResultToList(id: Int, key: String, resultOrNull: String) {
        Log.i(TAG, "MainViewModel setResultToList: $key to $resultOrNull")
        resultState[key] = resultOrNull
        Log.i(TAG, "MainViewModel setResultToList: $resultState")
//        val newList = listState.value
//            .filter { it.id != id }
//            .toMutableList()
//        val newItem = listState.value
//            .first { it.id == id }
//            .copy(result = resultOrNull)
//        newList.add(newItem)
//        newList.sortedBy { it.id }
//        _listState.value = newList
    }

    fun sendFormToRemote() {
        viewModelScope.launch {
            try {
                val response = convertResultToFormModelUseCase(resultState).result
                _uiEvent.emit(response)
            } catch (e: Exception) {
                _uiEvent.emit(e.message.toString())
                Log.i(TAG, "sendFormToRemote: ${e.message}")
            }
        }
    }

    data class UiState(
        val isFetchingData: Boolean = false,
        val title: String = INIT_STRING_VALUE,
        val imageLink: String? = null
    )
}