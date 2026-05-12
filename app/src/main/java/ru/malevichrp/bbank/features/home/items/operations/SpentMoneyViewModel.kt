package ru.malevichrp.bbank.features.home.items.operations

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.malevichrp.bbank.core.Money
import ru.malevichrp.bbank.coreui.DomainErrorMapper
import ru.malevichrp.bbank.features.home.presentation.HomeViewModel
import javax.inject.Inject

@HiltViewModel
class SpentMoneyViewModel @Inject constructor(
    repository: SpentMoneyRepository,
    errorMapper: DomainErrorMapper

) : HomeViewModel<Money>(
    repository = repository,
    errorMapper = errorMapper
)
