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
import ru.malevichrp.bbank.R


@Composable
fun HomeScreen(snackbarHostState: SnackbarHostState) {

}

@JvmInline
value class AccountId(val id: String)
data class AccountUi(
    val id: AccountId,
    val title: String,
    val balance: MoneyRubles
)

@Composable
fun HomeScreenUi(
    fullName: String,
    accounts: List<AccountUi>,
    onProfileClick: () -> Unit,
    onOperationsClick: () -> Unit,
    onTransferMoneyClick: () -> Unit,
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
                MoneyRubles(56_05800),
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
                    onTransferMoneyClick,
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
                it.id
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
        onProfileClick = {},
        onOperationsClick = {},
        onTransferMoneyClick = {},
        accounts = listOf(
            AccountUi(AccountId("1"), "Дебетовая карта *9649", MoneyRubles(9_681_01)),
            AccountUi(AccountId("2"), "Кредитная карта *5434", MoneyRubles(15_451_14)),
            AccountUi(AccountId("3"), "Накопительный счет", MoneyRubles(65_681_31))
        ),
        onAccountClick = {}
    )
}

@Composable
fun OperationsCard(
    moneySpent: MoneyRubles,
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
                "Все операции",
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
            Text(
                " >",
                style = MaterialTheme.typography.titleLarge,
            )
        }
    }
}

@Composable
fun AccountCard(
    accountUi: AccountUi,
    onClick: (AccountId) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        onClick = {
            onClick(accountUi.id)
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
                accountUi.balance.formatted(),
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                accountUi.title,
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }
}

@Preview
@Composable
fun ProfileCardPreview() {
    ProfileCard(
        fullName = "Петров Иван Сергеевич",
        onClick = {}
    )
}