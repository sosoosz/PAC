package com.example.si.ecras

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.si.R
import com.example.si.Screen
import com.example.si.model.UserViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*
import kotlin.system.exitProcess

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(navController: NavHostController, viewModel: UserViewModel = viewModel()) {
    val userName by viewModel.currentUserName.collectAsState()

    // Get the current date
    val currentDate = LocalDate.now()
    val dayOfWeek = currentDate.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
    val formattedDate = currentDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))

    var showExitDialog by remember { mutableStateOf(false) }

    if (showExitDialog) {
        ExitConfirmationDialog(
            onConfirm = { exitProcess(0) },
            onDismiss = { showExitDialog = false }
        )
    }

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.Start
    ) {
        Spacer(modifier = Modifier.height(25.dp))
        Text("Bem Vindo, ${userName ?: ""}",
            Modifier.padding(14.dp),
            fontSize = 30.sp,
            color = Color.Black)
    }
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.Start
    ) {
        Spacer(modifier = Modifier.height(70.dp))
        Text("$dayOfWeek,",
            Modifier.padding(14.dp),
            fontSize = 40.sp,
            color = Color.Black)
    }
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.Start
    ) {
        Spacer(modifier = Modifier.height(115.dp))
        Text(formattedDate,
            Modifier.padding(14.dp),
            fontSize = 25.sp,
            color = Color.Black)
    }

    Box(
        Modifier.fillMaxSize()
    ) {
        // Blue bar at the bottom
        Box(
            modifier = Modifier
                .height(70.dp)
                .width(500.dp)
                .background(Color.Blue)
                .align(Alignment.BottomStart)
        )

        // Bottom Center Glossary Image
        Image(
            painter = painterResource(id = R.drawable.glossario),
            contentDescription = "Glossário",
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(10.dp)
                .size(50.dp)
                .clickable { navController.navigate(Screen.GlossarioScreen.route) }
        )
    }

    Box(
        Modifier.fillMaxSize()
    ) {

        // Top End Profile Image
        Image(
            painter = painterResource(id = R.drawable.perfil2),
            contentDescription = "Perfil",
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
                .size(60.dp)
                .clickable { navController.navigate(Screen.ProfileScreen.route) }
        )

        // Bottom Start House Image
        Image(
            painter = painterResource(id = R.drawable.casa),
            contentDescription = "Casa",
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(0.dp)
                .size(60.dp)
                .offset(x = (20).dp, y = (-5).dp)
        )

        // Bottom End Calendar Image
        Image(
            painter = painterResource(id = R.drawable.calendario),
            contentDescription = "Calendário",
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(10.dp)
                .size(50.dp)
                .offset(x = (-20).dp, y = (0).dp)
                .clickable { navController.navigate(Screen.CalendarScreen.route) }
        )
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    navController.navigate(route = Screen.CalendarScreen.route)
                },
                modifier = Modifier
                    .size(300.dp, 60.dp),
                colors = ButtonDefaults.buttonColors(
                    Color.Blue
                )
            ) {
                Text(
                    "Calendário",
                    fontSize = 20.sp
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    navController.navigate(route = Screen.GlossarioScreen.route)
                },
                modifier = Modifier
                    .size(300.dp, 60.dp),
                colors = ButtonDefaults.buttonColors(
                    Color.Blue
                )
            ) {
                Text(
                    "Glossário",
                    fontSize = 20.sp
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    showExitDialog = true
                },
                modifier = Modifier
                    .size(300.dp, 60.dp),
                colors = ButtonDefaults.buttonColors(
                    Color.Blue
                )
            ) {
                Text(
                    "Sair",
                    fontSize = 20.sp
                )
            }
        }
    }
}

@Composable
fun ExitConfirmationDialog(onConfirm: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Confirmar Saída") },
        text = { Text(text = "Tem certeza de que deseja sair do aplicativo?") },
        confirmButton = {
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(
                    Color.Red
                )
            ) {
                Text("Confirmar", color = Color.White)
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(
                    Color.Gray
                )
            ) {
                Text("Cancelar", color = Color.White)
            }
        }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
private fun MainScreenPreview() {
    MainScreen(navController = rememberNavController())
}
