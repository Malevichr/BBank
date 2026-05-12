package ru.malevichrp.bbank.features.operationdetails.domain

import ru.malevichrp.bbank.core.AccountData
import ru.malevichrp.bbank.core.Money

data class OperationDetails(
    val dateTime: String,
    val title: String,
    val category: String,
    val amount: Money,
    val account: AccountData,
    val id: String = "",
)