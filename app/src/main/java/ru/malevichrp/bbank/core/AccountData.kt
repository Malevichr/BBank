package ru.malevichrp.bbank.core

import androidx.compose.runtime.Immutable

@Immutable
data class AccountData(
    val id: AccountId,
    val title: String,
    val balance: Money
)