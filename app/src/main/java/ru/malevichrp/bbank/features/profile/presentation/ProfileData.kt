package ru.malevichrp.bbank.features.profile.presentation

data class ProfileData(
    val fullName: String,
    val profileImageSource: ImageSource
)
sealed interface ImageSource {
    data object Empty : ImageSource
    data class Storage(val path: String) : ImageSource
}