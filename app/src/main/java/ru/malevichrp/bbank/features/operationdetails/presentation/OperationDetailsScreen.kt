package ru.malevichrp.bbank.features.operationdetails.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.distinctUntilChanged
import ru.malevichrp.bbank.core.AccountData
import ru.malevichrp.bbank.core.AccountId
import ru.malevichrp.bbank.core.LoadableUiState
import ru.malevichrp.bbank.core.Money
import ru.malevichrp.bbank.coreui.AccountCard
import ru.malevichrp.bbank.coreui.BackButton
import ru.malevichrp.bbank.coreui.LoadingComponent
import ru.malevichrp.bbank.coreui.RetryComponent
import ru.malevichrp.bbank.features.home.presentation.formattedWithPlus
import ru.malevichrp.bbank.features.operationdetails.domain.OperationDetails

@Composable
fun OperationDetailsScreen(
    viewModel: OperationDetailsViewModel,
    onBackClick: () -> Unit,
    onAccountClick: (AccountId) -> Unit,
    snackbarHostState: SnackbarHostState
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
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
    OperationDetailsScreenUi(
        state.value,
        onBackClick = onBackClick,
        onRetryClick = viewModel::retry,
        onAccountClick = onAccountClick,
    )
}

@Composable
fun OperationDetailsScreenUi(
    state: LoadableUiState<OperationDetails>,
    onBackClick: () -> Unit = {},
    onRetryClick: () -> Unit = {},
    onAccountClick: (AccountId) -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        BackButton(onBackClick)
        when (state) {
            is LoadableUiState.Success -> OperationDetailsInfo(
                operationDetails = state.data,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(10.dp),
                onAccountClick = onAccountClick
            )

            is LoadableUiState.Error -> RetryComponent(onRetryClick, Modifier.fillMaxSize())
            is LoadableUiState.Loading -> LoadingComponent(Modifier.fillMaxSize())
        }
    }
}

@Composable
fun OperationDetailsInfo(
    operationDetails: OperationDetails,
    modifier: Modifier = Modifier,
    onAccountClick: (AccountId) -> Unit = {}
) {
    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        with(operationDetails) {
            Text(dateTime, style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.weight(1F))
            Text(title, style = MaterialTheme.typography.titleLarge)
            Text(category, style = MaterialTheme.typography.titleMedium, color = Color.Gray)
            Spacer(Modifier.weight(0.1F))
            Text(amount.formattedWithPlus(), style = MaterialTheme.typography.headlineLarge)
            Spacer(Modifier.weight(0.2F))
            AccountCard(account, onClick = onAccountClick)
            Spacer(Modifier.weight(1.5F))
        }
    }
}

@Preview
@Composable
private fun OperationDetailsScreenUiPreviewSuccess() {
    OperationDetailsScreenUi(
        LoadableUiState.Success(
            OperationDetails(
                dateTime = "10 мая, 12:05",
                title = "Fix Price",
                category = "Различные товары",
                amount = Money(-22900),
                account = AccountData(
                    id = AccountId(""),
                    title = "Дебетовая карта *9649",
                    balance = Money(968101)
                ),
            )
        )
    )
}

@Preview
@Composable
private fun OperationDetailsScreenUiPreviewLoading() {
    OperationDetailsScreenUi(
        LoadableUiState.Loading
    )
}

@Preview
@Composable
private fun OperationDetailsScreenUiPreviewError() {
    OperationDetailsScreenUi(
        LoadableUiState.Error
    )
}
