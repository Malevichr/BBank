package ru.malevichrp.bbank.features.home.domain

import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    fun fullName(): Flow<String>
    fun moneySpent(): Flow<Money>
    fun accounts(): Flow<List<AccountData>>
}