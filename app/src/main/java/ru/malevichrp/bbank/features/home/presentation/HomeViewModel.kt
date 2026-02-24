package ru.malevichrp.bbank.features.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import ru.malevichrp.bbank.features.home.domain.HomeRepository
import ru.malevichrp.bbank.features.home.domain.HomeResult


abstract class HomeViewModel<T>(
    private val repository: HomeRepository<T>,
    private val errorMapper: HomeErrorMapper
) : ViewModel() {
    val errorEffect = MutableSharedFlow<String>(extraBufferCapacity = 1)
    private val retry = MutableSharedFlow<Unit>(extraBufferCapacity = 1)

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<HomeLoadableState<T>> =
        retry.onStart { emit(Unit) }
            .flatMapLatest {
                repository.load()
                    .map {
                        when (it) {
                            is HomeResult.Success -> HomeLoadableState.Success(it.data)
                            is HomeResult.Error -> {
                                val message = it.domainError.map(errorMapper)
                                errorEffect.tryEmit(message)
                                HomeLoadableState.Error
                            }
                        }
                    }
                    .onStart { emit(HomeLoadableState.Loading) }
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = HomeLoadableState.Loading
            )

    fun retry() {
        retry.tryEmit(Unit)
    }
}


sealed interface HomeLoadableState<out T> {
    data object Loading : HomeLoadableState<Nothing>
    data class Success<out T>(val data: T) : HomeLoadableState<T>
    data object Error : HomeLoadableState<Nothing>
}