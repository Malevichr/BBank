package ru.malevichrp.bbank.features.home.items.profile

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.malevichrp.bbank.core.LoadResult
import ru.malevichrp.bbank.features.home.domain.HomeDomainError
import ru.malevichrp.bbank.features.home.domain.HomeRepository
import javax.inject.Inject


interface FullNameRepository : HomeRepository<String> {
    class Fake @Inject constructor() : FullNameRepository {
        private var shouldError = true
        override fun load(): Flow<LoadResult<String>> = flow {
            delay(3000)
            if (shouldError) {
                shouldError = false
                emit(LoadResult.Error(HomeDomainError.Common))
            } else {
                emit(LoadResult.Success("Петров Иван Сергеевич"))
            }
        }
    }
}



