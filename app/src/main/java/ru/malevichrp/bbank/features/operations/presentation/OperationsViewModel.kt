package ru.malevichrp.bbank.features.operations.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import ru.malevichrp.bbank.core.LoadResult
import ru.malevichrp.bbank.core.LoadableUiState
import ru.malevichrp.bbank.coreui.DomainErrorMapper
import ru.malevichrp.bbank.features.operations.domain.OperationItem
import ru.malevichrp.bbank.features.operations.domain.OperationsRepository

@HiltViewModel
class OperationsViewModel @Inject constructor(
    private val repository: OperationsRepository,
    private val errorMapper: DomainErrorMapper
) : ViewModel() {
    val errorEffect = MutableSharedFlow<String>(extraBufferCapacity = 1)
    private val retry = MutableSharedFlow<Unit>(extraBufferCapacity = 1)

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<LoadableUiState<List<OperationItem>>> =
        retry.onStart { emit(Unit) }
            .flatMapLatest {
                repository.load()
                    .map {
                        when (it) {
                            is LoadResult.Success -> LoadableUiState.Success(it.data)
                            is LoadResult.Error -> {
                                val error = it.domainError.map(errorMapper)
                                errorEffect.tryEmit(error)
                                LoadableUiState.Error
                            }
                        }
                    }
                    .onStart { emit(LoadableUiState.Loading) }
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = LoadableUiState.Loading
            )

    fun retry() {
        retry.tryEmit(Unit)
    }
}