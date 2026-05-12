package ru.malevichrp.bbank.features.operations.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.distinctUntilChanged
import ru.malevichrp.bbank.core.LoadableUiState
import ru.malevichrp.bbank.core.Money
import ru.malevichrp.bbank.coreui.BackButton
import ru.malevichrp.bbank.coreui.LoadingComponent
import ru.malevichrp.bbank.coreui.RetryComponent
import ru.malevichrp.bbank.features.home.presentation.formattedWithPlus
import ru.malevichrp.bbank.features.operations.domain.OperationItem

@Composable
fun OperationsScreen(
    viewModel: OperationsViewModel,
    onBackClick: () -> Unit,
    navigateDetails: (String) -> Unit,
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
    OperationsScreenUi(
        state.value,
        onBackClick = onBackClick,
        onRetryClick = viewModel::retry,
        navigateDetails = navigateDetails
    )
}

@Composable
fun OperationsScreenUi(
    state: LoadableUiState<List<OperationItem>>,
    onBackClick: () -> Unit = {},
    onRetryClick: () -> Unit = {},
    navigateDetails: (String) -> Unit = {}
) {
    Scaffold(
        Modifier.padding(8.dp),
        topBar = {
            BackButton(onBackClick)
        }
    ) { paddingValues ->
        when (state) {
            is LoadableUiState.Success ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues = paddingValues)
                ) {
                    items(
                        items = state.data
                    ) { item ->
                        when (item) {
                            is OperationItem.DateHeader -> DateCard(item)
                            is OperationItem.Operation -> OperationCard(item, navigateDetails)
                        }
                    }
                }

            is LoadableUiState.Error -> RetryComponent(onRetryClick, Modifier.fillMaxSize())
            is LoadableUiState.Loading -> LoadingComponent(Modifier.fillMaxSize())
        }
    }

}

@Composable
fun DateCard(item: OperationItem.DateHeader) {
    Text(
        text = item.date,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(
            start = 16.dp,
            top = 16.dp,
            bottom = 8.dp
        )
    )
}

@Composable
fun OperationCard(
    transactionData: OperationItem.Operation,
    navigateDetails: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { navigateDetails(transactionData.id) }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(Modifier.weight(1F)) {
            Text(
                transactionData.title,
                style = MaterialTheme.typography.titleMedium
            )

            Text(transactionData.category)

        }
        Text(
            transactionData.amount.formattedWithPlus(),
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Preview
@Composable
fun OperationsScreenUiPreviewSuccess() {
    OperationsScreenUi(
        LoadableUiState.Success(
            listOf(
                OperationItem.DateHeader("6 мая"),
                OperationItem.Operation(
                    title = "Подписка Pro",
                    category = "Другое",
                    amount = Money(-14900)
                ),
                OperationItem.DateHeader("4 мая"),
                OperationItem.Operation(
                    title = "Александр И.",
                    category = "Переводы",
                    amount = Money(+1000000)
                ),
            )
        ),
    )
}

@Preview
@Composable
fun OperationsScreenUiPreviewError() {
    OperationsScreenUi(LoadableUiState.Error)
}

@Preview
@Composable
fun OperationsScreenUiPreviewLoading() {
    OperationsScreenUi(LoadableUiState.Loading)
}