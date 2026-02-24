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
import ru.malevichrp.bbank.features.home.HomeRoute
import ru.malevichrp.bbank.features.home.HomeScreen
import ru.malevichrp.bbank.features.login.presentation.LoginRoute
import ru.malevichrp.bbank.features.login.presentation.LoginScreen
import ru.malevichrp.bbank.features.login.presentation.LoginViewModel
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
            HomeScreen(snackbarHostState)
        }
    }
}

