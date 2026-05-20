package ru.malevichrp.bbank.features.account.domain

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.malevichrp.bbank.core.AccountData
import ru.malevichrp.bbank.core.AccountId
import ru.malevichrp.bbank.core.DomainError
import ru.malevichrp.bbank.core.LoadResult
import ru.malevichrp.bbank.core.Money
import ru.malevichrp.bbank.features.account.presentation.RequisitesData
import javax.inject.Inject

interface AccountRepository {

    fun load(
        id: AccountId,
    ): Flow<LoadResult<AccountScreenData>>

    class Fake @Inject constructor() : AccountRepository {

        private var shouldError = true

        override fun load(
            id: AccountId,
        ): Flow<LoadResult<AccountScreenData>> = flow {
            delay(1000)

            if (shouldError) {
                shouldError = false
                emit(LoadResult.Error(DomainError.Common))
            } else {
                emit(
                    LoadResult.Success(
                        AccountScreenData(
                            accountData = AccountData(
                                id = id,
                                title = "Дебетовая карта",
                                balance = Money(9_681_01),
                            ),
                            requisitesData =
                                RequisitesData(
                                    cardNumber = "2200 7000 1234 9649",
                                    expirationDate = "05/28",
                                    cvv = "123",
                                )
                        )
                    )
                )
            }
        }
    }
}