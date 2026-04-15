package ru.malevichrp.bbank.features.profile.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import coil.compose.AsyncImage
import ru.malevichrp.bbank.R
import java.io.File

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    snackbarHostState: SnackbarHostState,
    onBackNavigate: () -> Unit,
    onSettingsNavigate: () -> Unit,
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.errorOccurred.collect { message ->
                snackbarHostState.showSnackbar(message)
            }
        }
    }

    ProfileScreenUi(
        state.value,
        onBackClick = onBackNavigate,
        onSettingsClick = onSettingsNavigate,
        onLogOutClick = viewModel::logout,
        onRetryClick = viewModel::retry,
    )
}

@Composable
fun ProfileScreenUi(
    state: ProfileUiState,
    onBackClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onLogOutClick: () -> Unit,
    onRetryClick: () -> Unit
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Box(Modifier.fillMaxWidth()) {
            IconButton(onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = stringResource(R.string.back)
                )
            }
            IconButton(
                onSettingsClick,
                Modifier.align(Alignment.TopEnd)
            ) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = stringResource(R.string.settings)
                )
            }
        }
        Box(
            Modifier
                .weight(1F)
                .fillMaxWidth()
        ) {
            when (state) {
                is ProfileUiState.Error -> ProfileError(onRetryClick)
                is ProfileUiState.Loading -> ProfileLoading()
                is ProfileUiState.Success -> ProfileSuccess(
                    state
                )
            }
        }
        Button(
            onLogOutClick,
            Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(stringResource(R.string.log_out))
        }
    }
}

@Composable
fun ProfileLoading() {
    Box(Modifier.fillMaxSize()) {
        CircularProgressIndicator(Modifier.align(Alignment.Center))
    }
}

@Composable
fun ProfileSuccess(
    state: ProfileUiState.Success
) {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (state.profileImageSource) {
            is ImageSource.Empty -> Icon(
                imageVector = Icons.Outlined.AccountCircle,
                contentDescription = stringResource(R.string.profile_avatar),
                Modifier.size(128.dp)

            )

            is ImageSource.Storage -> {
                AsyncImage(
                    model = File(state.profileImageSource.path),
                    contentDescription = stringResource(R.string.profile_avatar),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(128.dp)
                        .clip(CircleShape)
                )
            }
        }
        Spacer(Modifier.height(32.dp))
        Text(state.fullName, style = MaterialTheme.typography.titleLarge)
    }
}


@Composable
fun ProfileError(onRetryClick: () -> Unit) {
    Box(Modifier.fillMaxSize()) {
        Button(onRetryClick, Modifier.align(Alignment.Center)) {
            Text(stringResource(R.string.retry_load))
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun ProfileScreenSuccessPreview() {
    ProfileScreenUi(
        ProfileUiState.Success(
            "Петров Иван Сергеевич",
            ImageSource.Empty
        ),
        {},
        {},
        {},
        {}
    )
}

@Preview(showBackground = true)
@Composable
private fun ProfileScreenLoadingPreview() {
    ProfileScreenUi(
        ProfileUiState.Loading,
        {},
        {},
        {},
        {}
    )
}

@Preview(showBackground = true)
@Composable
private fun ProfileScreenErrorPreview() {
    ProfileScreenUi(
        ProfileUiState.Error,
        {},
        {},
        {},
        {}
    )
}