package ru.malevichrp.bbank.features.operations.domain

import ru.malevichrp.bbank.features.home.domain.Money

sealed interface OperationItem {
    data class Operation(
        val title: String,
        val category: String,
        val amount: Money,
        val id: String = ""
    ) : OperationItem

    data class DateHeader(val date: String) : OperationItem
}