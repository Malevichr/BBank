package ru.malevichrp.bbank.features.account.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.malevichrp.bbank.R

@Composable
fun RequisitesCard(
    data: RequisitesData,
    showRequisites: Boolean,
    onShowClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val visibleData = if (showRequisites) {
        data
    } else {
        RequisitesData(
            cardNumber = "**** **** **** ${data.cardNumber.takeLast(4)}",
            expirationDate = "**/**",
            cvv = "***",
        )
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(R.string.requisites),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.weight(1f),
                )

                TextButton(
                    onClick = onShowClick,
                ) {
                    Text(
                        text = stringResource(
                            if (showRequisites) R.string.hide else R.string.show
                        )
                    )
                }
            }

            RequisiteField(
                value = visibleData.cardNumber,
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                RequisiteField(
                    value = visibleData.expirationDate,
                    modifier = Modifier.weight(1f),
                )

                RequisiteField(
                    value = visibleData.cvv,
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}

@Composable
private fun RequisiteField(
    value: String,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outline,
        ),
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(
                horizontal = 12.dp,
                vertical = 14.dp,
            ),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RequisitesCardVisiblePreviewHide() {
    RequisitesCard(
        data = RequisitesData(
            cardNumber = "2200 7000 1234 9649",
            expirationDate = "05/28",
            cvv = "123",
        ),
        modifier = Modifier.padding(16.dp),
        showRequisites = false,
        onShowClick = {},
    )
}

@Preview(showBackground = true)
@Composable
private fun RequisitesCardVisiblePreviewShown() {
    RequisitesCard(
        data = RequisitesData(
            cardNumber = "2200 7000 1234 9649",
            expirationDate = "05/28",
            cvv = "123",
        ),
        modifier = Modifier.padding(16.dp),
        showRequisites = true,
        onShowClick = {},
    )
}

