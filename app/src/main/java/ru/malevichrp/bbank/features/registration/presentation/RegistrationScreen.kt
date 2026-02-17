package ru.malevichrp.bbank.features.registration.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import ru.malevichrp.bbank.coreui.TextField

@Composable
fun RegistrationScreen(
    viewModel: RegistrationViewModel,
    snackbarHostState: SnackbarHostState
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current

    val lastNameTextFieldState = rememberTextFieldState()
    val firstNameTextFieldState = rememberTextFieldState()
    val middleNameTextFieldState = rememberTextFieldState()

    val loginTextFieldState = rememberTextFieldState()
    val passwordTextFieldState = rememberTextFieldState()

    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.uiEffect.collect { state ->
                when (state) {
                    is RegistrationUiEffect.SuccessRegistration -> {
                        //todo navigate to MainScreen
                    }

                    is RegistrationUiEffect.ShowError -> {
                        snackbarHostState.showSnackbar(state.error)
                    }
                }
            }
        }
    }
    RegistrationScreenUi(
        lastNameTextFieldState,
        firstNameTextFieldState,
        middleNameTextFieldState,
        loginTextFieldState,
        passwordTextFieldState,
        state.value,
        {
            val registrationData = RegistrationData(
                lastNameTextFieldState.text.toString(),
                firstNameTextFieldState.text.toString(),
                middleNameTextFieldState.text.toString(),
                loginTextFieldState.text.toString(),
                passwordTextFieldState.text.toString()
            )
            viewModel.register(
                registrationData
            )
        }, {
            //todo navigate to login
        })
}

@Composable
fun RegistrationScreenUi(
    lastNameTextFieldState: TextFieldState,
    firstNameTextFieldState: TextFieldState,
    middleNameTextFieldState: TextFieldState,
    loginTextFieldState: TextFieldState,
    passwordTextFieldState: TextFieldState,
    state: RegistrationUiState,
    onRegisterClick: () -> Unit,
    onBackClick: () -> Unit,
) {

    Box(Modifier.fillMaxSize()) {
        LogoBBank(
            modifier = Modifier
                .padding(24.dp)
                .align(Alignment.TopCenter)
        )
        when (state) {
            is RegistrationUiState.Initial -> RegistrationInitial(
                lastNameTextFieldState,
                firstNameTextFieldState,
                middleNameTextFieldState,
                loginTextFieldState,
                passwordTextFieldState,
                onRegisterClick,
                onBackClick,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(16.dp)
            )

            is RegistrationUiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}

@Composable
fun RegistrationInitial(
    lastNameTextFieldState: TextFieldState,
    firstNameTextFieldState: TextFieldState,
    middleNameTextFieldState: TextFieldState,
    loginTextFieldState: TextFieldState,
    passwordTextFieldState: TextFieldState,
    onRegisterClick: () -> Unit,
    onBackClick: () -> Unit,
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
        Spacer(Modifier.height(48.dp))
        TextField(lastNameTextFieldState, stringResource(R.string.last_name))
        TextField(firstNameTextFieldState, stringResource(R.string.first_name))
        TextField(middleNameTextFieldState, stringResource(R.string.middle_name))

        TextField(loginTextFieldState, stringResource(R.string.login))
        TextField(passwordTextFieldState, stringResource(R.string.password))

        Spacer(Modifier.height(48.dp))

        Button(onRegisterClick) {
            Text(stringResource(R.string.register))
        }
        Button(onBackClick) {
            Text(stringResource(R.string.back))
        }
    }
}


