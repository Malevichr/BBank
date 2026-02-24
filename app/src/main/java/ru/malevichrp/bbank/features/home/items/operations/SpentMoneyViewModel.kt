package ru.malevichrp.bbank.features.home.items.operations

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.malevichrp.bbank.features.home.domain.Money
import ru.malevichrp.bbank.features.home.presentation.HomeErrorMapper
import ru.malevichrp.bbank.features.home.presentation.HomeViewModel
import javax.inject.Inject

@HiltViewModel
class SpentMoneyViewModel @Inject constructor(
    repository: SpentMoneyRepository,
    errorMapper: HomeErrorMapper

) : HomeViewModel<Money>(
    repository = repository,
    errorMapper = errorMapper
)
