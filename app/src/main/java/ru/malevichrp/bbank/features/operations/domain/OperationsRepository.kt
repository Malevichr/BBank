package ru.malevichrp.bbank.features.operations.domain

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.malevichrp.bbank.core.DomainError
import ru.malevichrp.bbank.core.LoadResult
import ru.malevichrp.bbank.features.home.domain.Money
import javax.inject.Inject

interface OperationsRepository {
    fun load(): Flow<LoadResult<List<OperationItem>>>
    class Fake @Inject constructor() : OperationsRepository {
        private var shouldError = true
        override fun load(): Flow<LoadResult<List<OperationItem>>> = flow {
            delay(3000)
            if (shouldError) {
                shouldError = false
                emit(LoadResult.Error(DomainError.Common))
            } else {
                emit(
                    LoadResult.Success(
                        listOf(
                            OperationItem.DateHeader("6 мая"),
                            OperationItem.Operation(
                                title = "Подписка Pro",
                                category = "Другое",
                                amount = Money(-14900)
                            ),
                            OperationItem.DateHeader("4 мая"),
                            OperationItem.Operation(
                                title = "Александр И.",
                                category = "Переводы",
                                amount = Money(+1000000)
                            ),
                        )
                    )
                )
            }
        }
    }
}
