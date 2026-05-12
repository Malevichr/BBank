package ru.malevichrp.bbank.features.home.items.operations

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.malevichrp.bbank.R
import ru.malevichrp.bbank.core.LoadableUiState
import ru.malevichrp.bbank.core.Money
import ru.malevichrp.bbank.features.home.presentation.formatted


fun LazyListScope.operationsCard(
    operationsState: LoadableUiState<Money>,
    onNavigateClick: () -> Unit,
    onRetryClick: () -> Unit,
) {
    item {
        OperationsCardUi(
            operationsState,
            onNavigateClick,
            onRetryClick
        )
    }
}

@Composable
fun OperationsCardUi(
    operationsState: LoadableUiState<Money>,
    onNavigateClick: () -> Unit,
    onRetryClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(96.dp),
        shape = RoundedCornerShape(20.dp),
        onClick = when (operationsState) {
            is LoadableUiState.Success<*> -> onNavigateClick
            is LoadableUiState.Error -> onRetryClick
            is LoadableUiState.Loading -> ({})
        },
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outline
        ),
    ) {
        when (operationsState) {
            is LoadableUiState.Error -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = stringResource(R.string.retry_load),
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(16.dp)
                            .fillMaxSize()
                    )
                }
            }

            is LoadableUiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }

            is LoadableUiState.Success -> {
                Row(
                    modifier = Modifier.padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                    ) {
                        Text(
                            stringResource(R.string.all_operations),
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text(
                            operationsState.data.formatted(),
                            style = MaterialTheme.typography.titleMedium,
                        )
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun OperationsCardUiSuccessPreview() {
    OperationsCardUi(
        operationsState = LoadableUiState.Success(Money(1234552)),
        onNavigateClick = {},
        onRetryClick = {},
    )
}

@Preview
@Composable
fun OperationsCardUiErrorPreview() {
    OperationsCardUi(
        operationsState = LoadableUiState.Error,
        onNavigateClick = {},
        onRetryClick = {},
    )
}

@Preview
@Composable
fun OperationsCardUiLoadingPreview() {
    OperationsCardUi(
        operationsState = LoadableUiState.Loading,
        onNavigateClick = {},
        onRetryClick = {},
    )
}

