package ru.malevichrp.bbank.features.profile.presentation

sealed interface ProfileUiState {
    data object Loading : ProfileUiState
    data class Success(
        val fullName: String,
        val profileImageSource: ImageSource
    ) : ProfileUiState

    data object Error : ProfileUiState
}

sealed interface ImageSource {
    data object Empty : ImageSource
    data class Storage(val path: String) : ImageSource
}