package ru.malevichrp.bbank.features.home.items.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.malevichrp.bbank.R
import ru.malevichrp.bbank.core.LoadableUiState

fun LazyListScope.profileCard(
    fullNameState: LoadableUiState<String>,
    onNavigateClick: () -> Unit,
    onRetryClick: () -> Unit,
) {
    item {
        ProfileCardUi(
            fullNameState,
            onNavigateClick,
            onRetryClick,
        )
    }
}

@Composable
fun ProfileCardUi(
    fullNameState: LoadableUiState<String>,
    onNavigateClick: () -> Unit,
    onRetryClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp),
        shape = RoundedCornerShape(20.dp),
        onClick = when (fullNameState) {
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
        when (fullNameState) {
            is LoadableUiState.Error -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = stringResource(R.string.retry_load),
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(8.dp)
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
                    Icon(
                        painter = painterResource(id = R.drawable.ic_launcher_foreground),
                        contentDescription = null,
                        modifier = Modifier.size(56.dp),
                    )
                    Text(
                        fullNameState.data,
                        style = MaterialTheme.typography.titleLarge,
                    )
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = null
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun ProfileCardUiSuccessPreview() {
    ProfileCardUi(
        fullNameState = LoadableUiState.Success("Петров Иван Сергеевич"),
        onNavigateClick = {},
        onRetryClick = {},
    )
}

@Preview
@Composable
fun ProfileCardUiErrorPreview() {
    ProfileCardUi(
        fullNameState = LoadableUiState.Error,
        onNavigateClick = {},
        onRetryClick = {},
    )
}

@Preview
@Composable
fun ProfileCardUiLoadingPreview() {
    ProfileCardUi(
        fullNameState = LoadableUiState.Loading,
        onNavigateClick = {},
        onRetryClick = {},
    )
}