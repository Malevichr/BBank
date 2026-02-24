package ru.malevichrp.bbank.features.home.presentation

import ru.malevichrp.bbank.features.home.domain.AccountId

data class HomeUiActions(
    val onProfileClick: () -> Unit,
    val onOperationsClick: () -> Unit,
    val onTransferMoneyClick: () -> Unit,
    val onTopUpClick: () -> Unit,
    val onAccountClick: (AccountId) -> Unit,
    val onRetryProfile: () -> Unit,
    val onRetryOperations: () -> Unit,
    val onRetryAccounts: () -> Unit,
)