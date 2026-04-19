package ru.malevichrp.bbank.core

sealed interface LoadableUiState<out T> {
    data object Loading : LoadableUiState<Nothing>
    data class Success<out T>(val data: T) : LoadableUiState<T>
    data object Error : LoadableUiState<Nothing>
}