package ru.malevichrp.bbank.features.account.presentation

data class RequisitesData(
    val cardNumber: String,
    val expirationDate: String,
    val cvv: String,
)