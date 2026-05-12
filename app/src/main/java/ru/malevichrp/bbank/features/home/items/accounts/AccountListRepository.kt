package ru.malevichrp.bbank.features.home.items.accounts

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.malevichrp.bbank.core.AccountData
import ru.malevichrp.bbank.core.AccountId
import ru.malevichrp.bbank.core.DomainError
import ru.malevichrp.bbank.core.LoadResult
import ru.malevichrp.bbank.core.Money
import ru.malevichrp.bbank.features.home.domain.HomeRepository
import javax.inject.Inject

interface AccountListRepository : HomeRepository<List<AccountData>> {
    class Fake @Inject constructor() : AccountListRepository {
        private var shouldError = true
        override fun load(): Flow<LoadResult<List<AccountData>>> = flow {
            delay(2000)
            if (shouldError) {
                shouldError = false
                emit(LoadResult.Error(DomainError.Common))
            } else {
                emit(
                    LoadResult.Success(
                        listOf(
                            AccountData(AccountId("1"), "Дебетовая карта *9649", Money(9_681_01)),
                            AccountData(AccountId("2"), "Кредитная карта *5434", Money(15_451_14)),
                            AccountData(AccountId("3"), "Накопительный счет", Money(65_681_31))
                        )
                    )
                )
            }
        }
    }
}
