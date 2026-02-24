package ru.malevichrp.bbank.features.home.domain

import androidx.compose.runtime.Immutable

@Immutable
data class AccountData(
    val id: AccountId,
    val title: String,
    val balance: Money
)