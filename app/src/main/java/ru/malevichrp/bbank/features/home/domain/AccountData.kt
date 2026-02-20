package ru.malevichrp.bbank.features.home.domain

import androidx.compose.runtime.Immutable
import ru.malevichrp.bbank.features.home.AccountId

@Immutable
data class AccountData(
    val id: AccountId,
    val title: String,
    val balance: Money
)