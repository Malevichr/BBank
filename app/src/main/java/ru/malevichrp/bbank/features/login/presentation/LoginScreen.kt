package ru.malevichrp.bbank.features.login.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import ru.malevichrp.bbank.R
import ru.malevichrp.bbank.coreui.LogoBBank

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    snackbarHostState: SnackbarHostState,
    onSuccessLogin: () -> Unit,
    onRegisterClick: () -> Unit
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current

    val loginTextFieldState = rememberTextFieldState()
    val passwordTextFieldState = rememberTextFieldState()

    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.uiEffect.collect { state ->
                when (state) {
                    is LoginUiEffect.SuccessLogin -> onSuccessLogin()

                    is LoginUiEffect.ShowError -> {
                        snackbarHostState.showSnackbar(state.error)
                    }
                }
            }
        }
    }
    LoginScreenUi(
        loginTextFieldState,
        passwordTextFieldState,
        state.value,
        {
            viewModel.login(
                loginTextFieldState.text.toString(),
                passwordTextFieldState.text.toString()
            )
        },
        onRegisterClick
    )
}

@Composable
fun LoginScreenUi(
    loginTextFieldState: TextFieldState,
    passwordTextFieldState: TextFieldState,
    state: LoginUiState,
    onEnterClick: () -> Unit,
    onRegisterClick: () -> Unit,
) {

    Box(Modifier.fillMaxSize()) {
        LogoBBank(
            modifier = Modifier
                .padding(24.dp)
                .align(Alignment.TopCenter)
        )
        when (state) {
            is LoginUiState.Initial -> LoginInitial(
                loginTextFieldState,
                passwordTextFieldState,
                onEnterClick,
                onRegisterClick,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(16.dp)
            )

            is LoginUiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}

@Composable
fun LoginInitial(
    loginTextFieldState: TextFieldState,
    passwordTextFieldState: TextFieldState,
    onEnterClick: () -> Unit,
    onRegisterClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            stringResource(R.string.entrance_bank),
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(Modifier.height(96.dp))
        OutlinedTextField(
            loginTextFieldState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            label = {
                Text(stringResource(R.string.login))
            }
        )
        OutlinedTextField(
            passwordTextFieldState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            label = {
                Text(stringResource(R.string.password))
            }
        )
        Spacer(Modifier.height(96.dp))

        Button(onEnterClick) {
            Text(stringResource(R.string.enter))
        }
        Button(onRegisterClick) {
            Text(stringResource(R.string.registration))
        }
    }
}

