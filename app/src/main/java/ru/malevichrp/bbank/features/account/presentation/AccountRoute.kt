package ru.malevichrp.bbank.features.account.presentation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable


@Serializable
data class AccountRoute(val accountId: String) : NavKey