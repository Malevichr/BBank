package ru.malevichrp.bbank.features.home.presentation


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.Button
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.merge
import ru.malevichrp.bbank.R
import ru.malevichrp.bbank.core.AccountData
import ru.malevichrp.bbank.core.AccountId
import ru.malevichrp.bbank.core.LoadableUiState
import ru.malevichrp.bbank.core.Money
import ru.malevichrp.bbank.features.home.items.accounts.AccountListViewModel
import ru.malevichrp.bbank.features.home.items.accounts.accountCardsUi
import ru.malevichrp.bbank.features.home.items.operations.OperationsCardUi
import ru.malevichrp.bbank.features.home.items.operations.SpentMoneyViewModel
import ru.malevichrp.bbank.features.home.items.operations.operationsCard
import ru.malevichrp.bbank.features.home.items.profile.FullNameViewModel
import ru.malevichrp.bbank.features.home.items.profile.ProfileCardUi
import ru.malevichrp.bbank.features.home.items.profile.profileCard


data class HomeUiStateContainer(
    val profile: LoadableUiState<String>,
    val operations: LoadableUiState<Money>,
    val accounts: LoadableUiState<List<AccountData>>,
)

@Composable
fun HomeScreen(
    fullNameViewModel: FullNameViewModel,
    spentMoneyViewModel: SpentMoneyViewModel,
    accountListViewModel: AccountListViewModel,
    navigate: HomeNavigateContainer,
    snackbarHostState: SnackbarHostState
) {
    val profileState by fullNameViewModel.state.collectAsStateWithLifecycle()
    val operationsState by spentMoneyViewModel.state.collectAsStateWithLifecycle()
    val accountsState by accountListViewModel.state.collectAsStateWithLifecycle()

    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            merge(
                fullNameViewModel.errorEffect,
                spentMoneyViewModel.errorEffect,
                accountListViewModel.errorEffect
            )
                .distinctUntilChanged()
                .collect { message ->
                    snackbarHostState.showSnackbar(message)
                }
        }
    }
    HomeScreenUi(
        sections = HomeUiStateContainer(profileState, operationsState, accountsState),
        actions = HomeUiActions(
            onProfileClick = navigate.profile,
            onOperationsClick = navigate.operations,
            onTransferMoneyClick = navigate.transferMoney,
            onTopUpClick = navigate.topUp,
            onAccountClick = navigate.account,
            onRetryProfile = fullNameViewModel::retry,
            onRetryOperations = spentMoneyViewModel::retry,
            onRetryAccounts = accountListViewModel::retry
        )
    )
}

@Composable
fun HomeScreenUi(
    sections: HomeUiStateContainer,
    actions: HomeUiActions,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        profileCard(
            fullNameState = sections.profile,
            onNavigateClick = actions.onProfileClick,
            onRetryClick = actions.onRetryProfile
        )

        operationsCard(
            operationsState = sections.operations,
            onNavigateClick = actions.onOperationsClick,
            onRetryClick = actions.onRetryOperations
        )

        homeButtons(actions.onTransferMoneyClick, actions.onTopUpClick)

        accountCardsUi(
            accountsState = sections.accounts,
            onNavigateClick = actions.onAccountClick,
            onRetryClick = actions.onRetryAccounts
        )
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreenUi(
        HomeUiStateContainer(
            profile = LoadableUiState.Success("Петров Иван Сергеевич"),
            operations = LoadableUiState.Success(Money(1234552)),
            accounts = LoadableUiState.Success(
                listOf(
                    AccountData(AccountId("1"), "Дебетовая карта *9649", Money(9_681_01)),
                    AccountData(AccountId("2"), "Кредитная карта *5434", Money(15_451_14)),
                    AccountData(AccountId("3"), "Накопительный счет", Money(65_681_31))
                )
            )
        ),
        actions = HomeUiActions(
            onProfileClick = {},
            onOperationsClick = {},
            onTransferMoneyClick = {},
            onTopUpClick = {},
            onAccountClick = {},
            onRetryProfile = {},
            onRetryOperations = {},
            onRetryAccounts = {}
        )
    )
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            ProfileCardUi(
                fullNameState = LoadableUiState.Success("Петров Иван Сергеевич"),
                onNavigateClick = {},
                onRetryClick = {},
            )
        }
        item {
            OperationsCardUi(
                operationsState = LoadableUiState.Success(Money(1234552)),
                onNavigateClick = {},
                onRetryClick = {},
            )
        }
        homeButtons({}, {})
        accountCardsUi(
            accountsState = LoadableUiState.Success(
                listOf(
                    AccountData(AccountId("1"), "Дебетовая карта *9649", Money(9_681_01)),
                    AccountData(AccountId("2"), "Кредитная карта *5434", Money(15_451_14)),
                    AccountData(AccountId("3"), "Накопительный счет", Money(65_681_31))
                )
            ),
            onNavigateClick = {},
            onRetryClick = {}
        )
    }
}

fun LazyListScope.homeButtons(
    navigateTransfer: () -> Unit,
    navigateTopUp: () -> Unit
) {
    item {
        Row(
            Modifier.fillMaxWidth()
        ) {
            Button(
                navigateTransfer,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            ) {
                Text(stringResource(R.string.tranfer_money))
            }
            Button(
                navigateTopUp,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            ) {
                Text(stringResource(R.string.top_up))
            }
        }
    }
}