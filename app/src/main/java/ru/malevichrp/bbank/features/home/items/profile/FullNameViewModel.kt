package ru.malevichrp.bbank.features.home.items.profile

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.malevichrp.bbank.features.home.presentation.HomeErrorMapper
import ru.malevichrp.bbank.features.home.presentation.HomeViewModel
import javax.inject.Inject

@HiltViewModel
class FullNameViewModel @Inject constructor(
    repository: FullNameRepository,
    errorMapper: HomeErrorMapper
) : HomeViewModel<String>(
    repository = repository,
    errorMapper = errorMapper
)