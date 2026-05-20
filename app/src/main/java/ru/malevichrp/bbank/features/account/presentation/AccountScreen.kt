package ru.malevichrp.bbank.features.account.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.distinctUntilChanged
import ru.malevichrp.bbank.R
import ru.malevichrp.bbank.core.AccountData
import ru.malevichrp.bbank.core.AccountId
import ru.malevichrp.bbank.core.LoadableUiState
import ru.malevichrp.bbank.core.Money
import ru.malevichrp.bbank.coreui.BackButton
import ru.malevichrp.bbank.coreui.LoadingComponent
import ru.malevichrp.bbank.coreui.RetryComponent
import ru.malevichrp.bbank.coreui.SecureScreen
import ru.malevichrp.bbank.features.account.domain.AccountScreenData
import ru.malevichrp.bbank.features.home.presentation.formatted
import ru.malevichrp.bbank.ui.theme.BBankTheme

@Composable
fun AccountScreen(
    accountId: AccountId,
    viewModel: AccountViewModel,
    onBackClick: () -> Unit,
    onNavigateOperations: () -> Unit,
    onNavigateTransfer: (AccountId) -> Unit,
    onNavigateTopUp: (AccountId) -> Unit,
    snackbarHostState: SnackbarHostState,
) {
    val stateFlow = remember(accountId) {
        viewModel.state(accountId)
    }
    var showRequisites by rememberSaveable { mutableStateOf(false) }

    val state = stateFlow.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.errorEffect
                .distinctUntilChanged()
                .collect { message ->
                    snackbarHostState.showSnackbar(message)
                }
        }
    }

    SecureScreen(enabled = showRequisites)

    AccountScreenUi(
        state = state.value,
        onBackClick = onBackClick,
        onRetryClick = viewModel::retry,
        onNavigateOperations = onNavigateOperations,
        onNavigateTransfer = onNavigateTransfer,
        onNavigateTopUp = onNavigateTopUp,
        showRequisites = showRequisites,
        onShowClick = {
            showRequisites = !showRequisites
        }
    )
}

@Composable
fun AccountScreenUi(
    state: LoadableUiState<AccountScreenData>,
    showRequisites: Boolean,

    onBackClick: () -> Unit = {},
    onRetryClick: () -> Unit = {},
    onNavigateOperations: () -> Unit = {},
    onNavigateTransfer: (AccountId) -> Unit = {},
    onNavigateTopUp: (AccountId) -> Unit = {},
    onShowClick: () -> Unit = {},
) {
    Scaffold(
        Modifier
            .fillMaxSize()
            .padding(8.dp),
        topBar = { BackButton(onBackClick) }
    ) { paddingValues ->
        when (state) {
            is LoadableUiState.Success -> AccountContent(
                data = state.data,
                onNavigateOperations = onNavigateOperations,
                onNavigateTransfer = onNavigateTransfer,
                onNavigateTopUp = onNavigateTopUp,
                modifier = Modifier.padding(paddingValues),
                showRequisites = showRequisites,
                onShowClick = onShowClick,
            )

            is LoadableUiState.Error -> RetryComponent(
                onClick = onRetryClick,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
            )

            is LoadableUiState.Loading -> LoadingComponent(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
            )
        }
    }
}

@Composable
fun AccountContent(
    data: AccountScreenData,
    modifier: Modifier = Modifier,
    onNavigateOperations: () -> Unit = {},
    onNavigateTransfer: (AccountId) -> Unit = {},
    onNavigateTopUp: (AccountId) -> Unit = {},
    showRequisites: Boolean,
    onShowClick: () -> Unit,
) {
    val accountData = data.accountData

    Column(
        modifier
            .fillMaxSize()
            .padding(8.dp),
    ) {
        Text(
            accountData.title,
            Modifier.padding(vertical = 8.dp),
            style = MaterialTheme.typography.titleLarge,
        )

        Text(
            accountData.balance.formatted(),
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(Modifier.height(40.dp))

        OperationsCard(
            Money(56_058),
            onNavigateOperations
        )

        TransactionButtons(
            { onNavigateTransfer(accountData.id) },
            { onNavigateTopUp(accountData.id) },
            Modifier.padding(vertical = 16.dp)
        )

        RequisitesCard(
            data = data.requisitesData,
            showRequisites = showRequisites,
            onShowClick = onShowClick,
        )
    }
}

@Composable
fun TransactionButtons(
    navigateTransfer: () -> Unit,
    navigateTopUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier.fillMaxWidth()
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

@Composable
fun OperationsCard(moneySpent: Money, onNavigate: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(88.dp),
        shape = RoundedCornerShape(20.dp),
        onClick = onNavigate,
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outline
        ),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
            ) {
                Text(
                    stringResource(R.string.account_operations),
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    moneySpent.formatted(),
                    style = MaterialTheme.typography.titleMedium,
                )
            }
        }
    }
}


@Preview
@Composable
fun AccountScreenUiPreviewSuccess() {
    BBankTheme {
        AccountScreenUi(
            state = LoadableUiState.Success(
                AccountScreenData(
                    accountData = AccountData(
                        id = AccountId("1"),
                        title = "Дебетовая карта",
                        balance = Money(9_681_01),
                    ),
                    requisitesData = RequisitesData(
                        cardNumber = "**** **** **** 9649",
                        expirationDate = "**/**",
                        cvv = "***",
                    ),
                )
            ),
            true
        )
    }
}

@Preview
@Composable
fun AccountScreenUiPreviewSuccessRequisitesShown() {
    BBankTheme {
        AccountScreenUi(
            state = LoadableUiState.Success(
                AccountScreenData(
                    accountData = AccountData(
                        id = AccountId("1"),
                        title = "Дебетовая карта",
                        balance = Money(9_681_01),
                    ),
                    requisitesData = RequisitesData(
                        cardNumber = "2200 7000 1234 9649",
                        expirationDate = "05/28",
                        cvv = "123",
                    ),
                )
            ),
            true
        )
    }
}

@Preview
@Composable
fun AccountScreenUiPreviewError() {
    BBankTheme {
        AccountScreenUi(
            state = LoadableUiState.Error,
            true
        )
    }
}

@Preview
@Composable
fun AccountScreenUiPreviewLoading() {
    BBankTheme {
        AccountScreenUi(
            state = LoadableUiState.Loading,
            true
        )
    }
}