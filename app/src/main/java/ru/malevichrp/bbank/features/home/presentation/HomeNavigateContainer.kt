package ru.malevichrp.bbank.features.home.presentation

import ru.malevichrp.bbank.features.home.domain.AccountId

data class HomeNavigateContainer(
    val profile: () -> Unit,
    val operations: () -> Unit,
    val transferMoney: () -> Unit,
    val topUp: () -> Unit,
    val account: (AccountId) -> Unit,
)