package ru.malevichrp.bbank.coreui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ru.malevichrp.bbank.core.AccountData
import ru.malevichrp.bbank.core.AccountId
import ru.malevichrp.bbank.features.home.presentation.formatted

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