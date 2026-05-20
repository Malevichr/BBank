package ru.malevichrp.bbank.features.account.domain

import ru.malevichrp.bbank.core.AccountData
import ru.malevichrp.bbank.features.account.presentation.RequisitesData


data class AccountScreenData(
    val accountData: AccountData,
    val requisitesData: RequisitesData
)