package com.example.si


import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.si.ecras.*
import com.example.si.model.MedicamentoViewModel
import com.example.si.model.UserViewModel
import com.example.si.ui.theme.SITheme
import java.time.LocalDate

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("StateFlowValueCalledInComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SITheme {
                val navController = rememberNavController()
                val userViewModel: UserViewModel = viewModel()
                val medicamentoViewModel: MedicamentoViewModel = viewModel()
                val currentUserId = userViewModel.currentUserId.collectAsState().value
                var checkedStates by rememberSaveable { mutableStateOf(mapOf<Int, LocalDate>()) }

                Box(modifier = Modifier.fillMaxSize()) {
                    Imagem()

                    NavHost(
                        navController = navController,
                        startDestination = if (currentUserId != null) Screen.MainScreen.route else Screen.LoginScreen.route
                    ) {
                        composable(route = Screen.GlossarioScreen.route) {
                            GlossarioScreen(navController)
                        }
                        composable(route = Screen.GerenciarMedScreen.route) {
                            if (currentUserId != null) {
                                medicamentoViewModel.loadMedicamentosForUser(currentUserId)
                                GerenciarMedScreen(navController)
                            } else {
                                navController.navigate(Screen.LoginScreen.route)
                            }
                        }
                        composable(route = Screen.AddMedScreen.route) {
                            if (currentUserId != null) {
                                AddMedScreen(navController, medicamentoViewModel, currentUserId)
                            } else {
                                navController.navigate(Screen.LoginScreen.route)
                            }
                        }
                        composable(route = Screen.ListMedScreen.route) {
                            if (currentUserId != null) {
                                medicamentoViewModel.loadMedicamentosForUser(currentUserId)
                                ListMedScreen(navController, medicamentoViewModel)
                            } else {
                                navController.navigate(Screen.LoginScreen.route)
                            }
                        }
                        composable(route = Screen.EditMedScreen.route) {
                            if (currentUserId != null) {
                                medicamentoViewModel.loadMedicamentosForUser(currentUserId)
                                EditMedScreen(navController, medicamentoViewModel)
                            } else {
                                navController.navigate(Screen.LoginScreen.route)
                            }
                        }
                        composable(route = Screen.PrescriptionScreen.route) {
                            if (currentUserId != null) {
                                medicamentoViewModel.loadMedicamentosForUser(currentUserId)
                                PrescriptionScreen(navController, medicamentoViewModel)
                            } else {
                                navController.navigate(Screen.LoginScreen.route)
                            }
                        }
                        composable(route = Screen.CalendarScreen.route) {
                            if (currentUserId != null) {
                                medicamentoViewModel.loadMedicamentosForUser(currentUserId)
                                CalendarScreen(
                                    navController,
                                    medicamentoViewModel,
                                    checkedStates,
                                    onCheckedStateChange = { id, date ->
                                        checkedStates = checkedStates.toMutableMap().apply {
                                            this[id] = date
                                        }
                                    }
                                )
                            } else {
                                navController.navigate(Screen.LoginScreen.route)
                            }
                        }
                        composable(route = Screen.ProfileScreen.route) {
                            if (currentUserId != null) {
                                ProfileScreen(navController, userViewModel)
                            } else {
                                navController.navigate(Screen.LoginScreen.route)
                            }
                        }
                        composable(route = Screen.MainScreen.route) {
                            MainScreen(navController, userViewModel)
                        }
                        composable(route = Screen.LoginScreen.route) {
                            LoginScreen(navController, userViewModel)
                        }
                        composable(route = Screen.RegisterScreen.route) {
                            RegisterScreen(navController, userViewModel)
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun Imagem(modifier: Modifier = Modifier) {
        val image = painterResource(R.drawable.image)

        Box(modifier) {
            Image(
                painter = image,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                alpha = 0.5F
            )
        }
    }
}
