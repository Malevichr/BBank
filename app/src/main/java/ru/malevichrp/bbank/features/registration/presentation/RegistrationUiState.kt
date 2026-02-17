package ru.malevichrp.bbank.features.registration.presentation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed interface RegistrationUiState : Parcelable {
    @Parcelize
    data object Initial : RegistrationUiState

    @Parcelize
    data object Loading : RegistrationUiState
}
