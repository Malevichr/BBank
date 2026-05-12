package ru.malevichrp.bbank.features.operationdetails.domain

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.malevichrp.bbank.core.AccountData
import ru.malevichrp.bbank.core.AccountId
import ru.malevichrp.bbank.core.DomainError
import ru.malevichrp.bbank.core.LoadResult
import ru.malevichrp.bbank.core.Money
import javax.inject.Inject

interface OperationDetailsRepository {
    fun load(): Flow<LoadResult<OperationDetails>>
    class Fake @Inject constructor() : OperationDetailsRepository {
        private var shouldError = true
        override fun load(): Flow<LoadResult<OperationDetails>> = flow {
            delay(3000)
            if (shouldError) {
                shouldError = false
                emit(LoadResult.Error(DomainError.Common))
            } else {
                emit(
                    LoadResult.Success(
                        OperationDetails(
                            dateTime = "10 мая, 12:05",
                            title = "Fix Price",
                            category = "Различные товары",
                            amount = Money(-22900),
                            account = AccountData(
                                id = AccountId(""),
                                title = "Дебетовая карта *9649",
                                balance = Money(968101)
                            )
                        )
                    )
                )
            }
        }
    }
}
