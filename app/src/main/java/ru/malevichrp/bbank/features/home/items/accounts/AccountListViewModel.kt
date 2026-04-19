package ru.malevichrp.bbank.features.home.items.accounts

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.malevichrp.bbank.coreui.DomainErrorMapper
import ru.malevichrp.bbank.features.home.domain.AccountData
import ru.malevichrp.bbank.features.home.presentation.HomeViewModel
import javax.inject.Inject

@HiltViewModel
class AccountListViewModel @Inject constructor(
    repository: AccountListRepository,
    errorMapper: DomainErrorMapper

) : HomeViewModel<List<AccountData>>(
    repository = repository,
    errorMapper = errorMapper
)
