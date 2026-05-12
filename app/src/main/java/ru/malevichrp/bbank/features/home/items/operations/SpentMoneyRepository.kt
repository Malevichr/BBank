package ru.malevichrp.bbank.features.home.items.operations

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.malevichrp.bbank.core.DomainError
import ru.malevichrp.bbank.core.LoadResult
import ru.malevichrp.bbank.core.Money
import ru.malevichrp.bbank.features.home.domain.HomeRepository
import javax.inject.Inject

interface SpentMoneyRepository : HomeRepository<Money> {
    class Fake @Inject constructor() : SpentMoneyRepository {
        private var shouldError = true
        override fun load(): Flow<LoadResult<Money>> = flow {
            delay(2500)
            if (shouldError) {
                shouldError = false
                emit(LoadResult.Error(DomainError.Common))
            } else {
                emit(LoadResult.Success(Money(412_523_54)))
            }
        }
    }
}
