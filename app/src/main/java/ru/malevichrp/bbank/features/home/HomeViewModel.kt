package ru.malevichrp.bbank.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import ru.malevichrp.bbank.features.home.domain.AccountData
import ru.malevichrp.bbank.features.home.domain.HomeRepository
import ru.malevichrp.bbank.features.home.domain.Money
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    repository: HomeRepository
) : ViewModel() {
    val fullName: StateFlow<String> =
        repository.fullName().stateInViewModel("")
    val moneySpent: StateFlow<Money> =
        repository.moneySpent().stateInViewModel(Money(0))
    val accounts: StateFlow<List<AccountData>> =
        repository.accounts().stateInViewModel(listOf())

    private fun <T> Flow<T>.stateInViewModel(initialValue: T): StateFlow<T> {
        return this.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = initialValue
        )
    }
}
