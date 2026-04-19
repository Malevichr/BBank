package ru.malevichrp.bbank.features.home.items.accounts

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
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
import ru.malevichrp.bbank.features.home.domain.AccountData
import ru.malevichrp.bbank.features.home.domain.AccountId
import ru.malevichrp.bbank.features.home.domain.Money
import ru.malevichrp.bbank.features.home.presentation.formatted


fun LazyListScope.accountCardsUi(
    accountsState: LoadableUiState<List<AccountData>>,
    onNavigateClick: (AccountId) -> Unit,
    onRetryClick: () -> Unit,
) {
    when (accountsState) {
        is LoadableUiState.Error -> {
            item {
                Box(
                    modifier = Modifier
                        .fillParentMaxWidth()
                        .height(480.dp)
                        .clickable {
                            onRetryClick()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = stringResource(R.string.retry_load),
                        modifier = Modifier
                            .size(64.dp)
                    )
                }
            }
        }

        is LoadableUiState.Loading -> {
            item {
                Box(
                    modifier = Modifier
                        .fillParentMaxWidth()
                        .height(480.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }

        is LoadableUiState.Success -> {
            items(
                items = accountsState.data,
                key = {
                    it.id.value
                },
            ) { account ->
                AccountCard(
                    account,
                    onNavigateClick,
                )
            }
        }
    }

}

@Composable
fun AccountCard(
    accountData: AccountData,
    onClick: (AccountId) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        onClick = {
            onClick(accountData.id)
        },
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outline
        ),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            Text(
                accountData.balance.formatted(),
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                accountData.title,
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }
}

@Preview
@Composable
fun AccountsUiSuccessPreview() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
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

@Preview
@Composable
fun AccountsUiErrorPreview() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        accountCardsUi(
            accountsState = LoadableUiState.Error,
            onNavigateClick = {},
            onRetryClick = {}
        )
    }
}

@Preview
@Composable
fun AccountsUiLoadingPreview() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        accountCardsUi(
            accountsState = LoadableUiState.Loading,
            onNavigateClick = {},
            onRetryClick = {}
        )
    }
}