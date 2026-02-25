package ru.malevichrp.bbank.features.registration.presentation

data class RegistrationData(
    val lastName: String,
    val firstName: String,
    val middleName: String,
    val login: String,
    val password: String,
    val phone: String
)
