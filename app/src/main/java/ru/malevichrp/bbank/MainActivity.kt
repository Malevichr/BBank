package ru.malevichrp.bbank

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.malevichrp.bbank.features.home.items.accounts.AccountListViewModel
import ru.malevichrp.bbank.features.home.items.operations.SpentMoneyViewModel
import ru.malevichrp.bbank.features.home.items.profile.FullNameViewModel
import ru.malevichrp.bbank.features.home.presentation.HomeNavigateContainer
import ru.malevichrp.bbank.features.home.presentation.HomeRoute
import ru.malevichrp.bbank.features.home.presentation.HomeScreen
import ru.malevichrp.bbank.features.login.presentation.LoginRoute
import ru.malevichrp.bbank.features.login.presentation.LoginScreen
import ru.malevichrp.bbank.features.login.presentation.LoginViewModel
import ru.malevichrp.bbank.features.operationdetails.presentation.OperationDetailsRoute
import ru.malevichrp.bbank.features.operationdetails.presentation.OperationDetailsScreen
import ru.malevichrp.bbank.features.operationdetails.presentation.OperationDetailsViewModel
import ru.malevichrp.bbank.features.operations.presentation.OperationsRoute
import ru.malevichrp.bbank.features.operations.presentation.OperationsScreen
import ru.malevichrp.bbank.features.operations.presentation.OperationsViewModel
import ru.malevichrp.bbank.features.profile.presentation.ProfileRoute
import ru.malevichrp.bbank.features.profile.presentation.ProfileScreen
import ru.malevichrp.bbank.features.profile.presentation.ProfileViewModel
import ru.malevichrp.bbank.features.registration.presentation.RegistrationRoute
import ru.malevichrp.bbank.features.registration.presentation.RegistrationScreen
import ru.malevichrp.bbank.features.registration.presentation.RegistrationViewModel
import ru.malevichrp.bbank.ui.theme.BBankTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BBankTheme {
                BBankApp()
            }
        }
    }
}

@Composable
fun BBankApp() {
    val snackbarHostState = remember { SnackbarHostState() }
    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        }
    ) { innerPadding ->
        Box(
            Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            BBankNavHost(snackbarHostState, navController)
        }
    }
}

@Composable
fun BBankNavHost(
    snackbarHostState: SnackbarHostState,
    navController: NavHostController
) {
    NavHost(
        navController,
        startDestination = LoginRoute
    ) {
        composable<LoginRoute> {
            LoginScreen(
                hiltViewModel<LoginViewModel>(),
                snackbarHostState,
                {
                    navController.navigate(HomeRoute) {
                        popUpTo(LoginRoute) { inclusive = true }
                    }
                },
                {
                    navController.navigate(RegistrationRoute)
                }
            )
        }
        composable<RegistrationRoute> {
            RegistrationScreen(
                hiltViewModel<RegistrationViewModel>(),
                snackbarHostState,
                {
                    navController.navigate(HomeRoute)
                },
                {
                    navController.popBackStack()
                }
            )
        }
        composable<HomeRoute> {
            HomeScreen(
                fullNameViewModel = hiltViewModel<FullNameViewModel>(),
                spentMoneyViewModel = hiltViewModel<SpentMoneyViewModel>(),
                accountListViewModel = hiltViewModel<AccountListViewModel>(),
                navigate = HomeNavigateContainer(
                    {
                        navController.navigate(ProfileRoute)
                    },
                    {
                        navController.navigate(OperationsRoute)
                    },
                    {},
                    {},
                    {},
                ),
                snackbarHostState = snackbarHostState
            )
        }
        composable<ProfileRoute> {
            ProfileScreen(
                viewModel = hiltViewModel<ProfileViewModel>(),
                backNavigate = {
                    navController.popBackStack()
                },
                settingsNavigate = {},
                snackbarHostState = snackbarHostState
            )
        }
        composable<OperationsRoute> {
            OperationsScreen(
                viewModel = hiltViewModel<OperationsViewModel>(),
                onBackClick = navController::popBackStack,
                navigateDetails = { navController.navigate(OperationDetailsRoute) },
                snackbarHostState = snackbarHostState
            )
        }
        composable<OperationDetailsRoute> {
            OperationDetailsScreen(
                viewModel = hiltViewModel<OperationDetailsViewModel>(),
                onBackClick = navController::popBackStack,
                onAccountClick = { },
                snackbarHostState = snackbarHostState,
            )
        }
    }
}

