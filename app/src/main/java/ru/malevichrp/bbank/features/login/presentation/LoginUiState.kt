package ru.malevichrp.bbank.features.login.presentation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed interface LoginUiState : Parcelable {
    @Parcelize
    data object Initial : LoginUiState

    @Parcelize
    data object Loading : LoginUiState
}
