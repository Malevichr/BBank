package ru.malevichrp.bbank.features.home.items.operations

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.malevichrp.bbank.features.home.domain.HomeDomainError
import ru.malevichrp.bbank.features.home.domain.HomeRepository
import ru.malevichrp.bbank.features.home.domain.HomeResult
import ru.malevichrp.bbank.features.home.domain.Money
import javax.inject.Inject

interface SpentMoneyRepository : HomeRepository<Money> {
    class Fake @Inject constructor() : SpentMoneyRepository {
        private var shouldError = true
        override fun load(): Flow<HomeResult<Money>> = flow {
            delay(2500)
            if (shouldError) {
                shouldError = false
                emit(HomeResult.Error(HomeDomainError.Common))
            } else {
                emit(HomeResult.Success(Money(412_523_54)))
            }
        }
    }
}
