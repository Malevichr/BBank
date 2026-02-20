package ru.malevichrp.bbank.features.home


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.malevichrp.bbank.R
import ru.malevichrp.bbank.features.home.domain.AccountData
import ru.malevichrp.bbank.features.home.domain.Money


@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    snackbarHostState: SnackbarHostState,
    onProfileClick: () -> Unit,
    onOperationsClick: () -> Unit,
    onTransferMoneyClick: () -> Unit,
    onTopUpClick: () -> Unit,
    onAccountClick: (AccountId) -> Unit
) {
    val fullName = viewModel.fullName.collectAsStateWithLifecycle()
    val moneySpent = viewModel.moneySpent.collectAsStateWithLifecycle()
    val accounts = viewModel.accounts.collectAsStateWithLifecycle()
    HomeScreenUi(
        fullName = fullName.value,
        moneySpent = moneySpent.value,
        accounts = accounts.value,
        onProfileClick = onProfileClick,
        onOperationsClick = onOperationsClick,
        onTransferMoneyClick = onTransferMoneyClick,
        onTopUpClick = onTopUpClick,
        onAccountClick = onAccountClick
    ) 
}


@JvmInline
value class AccountId(val value: String)

@Composable
fun HomeScreenUi(
    fullName: String,
    moneySpent: Money,
    accounts: List<AccountData>,
    onProfileClick: () -> Unit,
    onOperationsClick: () -> Unit,
    onTransferMoneyClick: () -> Unit,
    onTopUpClick: () -> Unit,
    onAccountClick: (AccountId) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            ProfileCard(
                fullName,
                onProfileClick,
            )
        }
        item {
            OperationsCard(
                moneySpent,
                onOperationsClick,
            )
        }
        item {
            Row(
                Modifier
                    .fillMaxWidth()
            ) {
                Button(
                    onTransferMoneyClick,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                ) {
                    Text(stringResource(R.string.tranfer_money))
                }
                Button(
                    onTopUpClick,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp)
                ) {
                    Text(stringResource(R.string.top_up))
                }
            }
        }
        items(
            items = accounts,
            key = {
                it.id.value
            }
        ) { account ->
            AccountCard(
                account,
                onAccountClick,
            )
        }
    }
}

@Preview
@Composable
fun HomeScreenUiPreview() {
    HomeScreenUi(
        "Петров Иван Сергеевич",
        moneySpent = Money(56_058_00),
        onProfileClick = {},
        onOperationsClick = {},
        onTransferMoneyClick = {},
        accounts = listOf(
            AccountData(AccountId("1"), "Дебетовая карта *9649", Money(9_681_01)),
            AccountData(AccountId("2"), "Кредитная карта *5434", Money(15_451_14)),
            AccountData(AccountId("3"), "Накопительный счет", Money(65_681_31))
        ),
        onAccountClick = {},
        onTopUpClick = {}
    )
}

@Composable
fun OperationsCard(
    moneySpent: Money,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        onClick = onClick,
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
                stringResource(R.string.all_operations),
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                moneySpent.formatted(),
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }
}


@Composable
fun ProfileCard(
    fullName: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outline
        ),
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = null,
                modifier = Modifier.size(56.dp),
            )
            Text(
                fullName,
                style = MaterialTheme.typography.titleLarge,
            )
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null
            )
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
