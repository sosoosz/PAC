package com.example.si.ecras

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.si.R
import com.example.si.Screen
import com.example.si.model.UserViewModel
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(navController: NavHostController, viewModel: UserViewModel = viewModel()) {
    val coroutineScope = rememberCoroutineScope()
    var editingField by remember { mutableStateOf<EditingField?>(null) }
    var newValue by remember { mutableStateOf("") }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showClearMedicationsDialog by remember { mutableStateOf(false) }
    val deleteUserState by viewModel.deleteUserState.collectAsState()
    val userId by viewModel.currentUserId.collectAsState()

    LaunchedEffect(deleteUserState) {
        if (deleteUserState) {
            viewModel.resetDeleteUserState()
            viewModel.resetLoginState()
            navController.navigate(Screen.LoginScreen.route) {
                popUpTo(Screen.LoginScreen.route) { inclusive = true }
            }
        }
    }

    Box(
        Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .height(70.dp)
                .width(500.dp)
                .background(Color.Blue)
                .align(Alignment.BottomStart)
        )

        Image(
            painter = painterResource(id = R.drawable.glossario),
            contentDescription = "Glossário",
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(10.dp)
                .size(50.dp)
                .clickable { navController.navigate(Screen.GlossarioScreen.route) }
        )

        Image(
            painter = painterResource(id = R.drawable.seta2),
            contentDescription = "Voltar",
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
                .size(60.dp)
                .clickable { navController.navigate(Screen.MainScreen.route) }
        )

        Image(
            painter = painterResource(id = R.drawable.perfil2),
            contentDescription = "Perfil",
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(75.dp)
                .size(250.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.casa),
            contentDescription = "Casa",
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(0.dp)
                .size(60.dp)
                .offset(x = 20.dp, y = (-5).dp)
                .clickable { navController.navigate(Screen.MainScreen.route) }
        )

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

        Spacer(modifier = Modifier.height(20.dp))

        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(150.dp))

            when (editingField) {
                EditingField.NAME -> {
                    TextField(
                        value = newValue,
                        onValueChange = { newValue = it },
                        label = { Text("Novo Nome") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    )
                }
                EditingField.USERNAME -> {
                    TextField(
                        value = newValue,
                        onValueChange = { newValue = it },
                        label = { Text("Novo Nome de Utilizador") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    )
                }
                EditingField.PASSWORD -> {
                    TextField(
                        value = newValue,
                        onValueChange = { newValue = it },
                        label = { Text("Nova Palavra-Passe") },
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    )
                }
                null -> {
                    Button(
                        onClick = { editingField = EditingField.NAME },
                        modifier = Modifier.size(250.dp, 60.dp),
                        colors = ButtonDefaults.buttonColors(Color.Blue)
                    ) {
                        Text("Editar Nome", fontSize = 20.sp, textAlign = TextAlign.Center)
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Button(
                        onClick = { editingField = EditingField.USERNAME },
                        modifier = Modifier.size(250.dp, 60.dp),
                        colors = ButtonDefaults.buttonColors(Color.Blue)
                    ) {
                        Text("Editar Nome de Utilizador", fontSize = 20.sp, textAlign = TextAlign.Center)
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Button(
                        onClick = { editingField = EditingField.PASSWORD },
                        modifier = Modifier.size(250.dp, 60.dp),
                        colors = ButtonDefaults.buttonColors(Color.Blue)
                    ) {
                        Text("Editar Palavra-Passe", fontSize = 20.sp, textAlign = TextAlign.Center)
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Button(
                        onClick = { showDeleteDialog = true },
                        modifier = Modifier.size(250.dp, 60.dp),
                        colors = ButtonDefaults.buttonColors(Color.Red)
                    ) {
                        Text("Apagar Conta", fontSize = 20.sp, textAlign = TextAlign.Center)
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Button(
                        onClick = { showClearMedicationsDialog = true },
                        modifier = Modifier.size(250.dp, 60.dp),
                        colors = ButtonDefaults.buttonColors(Color.Red)
                    ) {
                        Text("Apagar todos os Medicamentos", fontSize = 20.sp, textAlign = TextAlign.Center)
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Button(
                        onClick = {
                            viewModel.logout()
                            navController.navigate(Screen.LoginScreen.route) {
                                popUpTo(Screen.LoginScreen.route) { inclusive = true }
                            }
                        },
                        modifier = Modifier.size(250.dp, 60.dp),
                        colors = ButtonDefaults.buttonColors(Color.Gray)
                    ) {
                        Text("Logout", fontSize = 20.sp, textAlign = TextAlign.Center)
                    }
                }
            }

            if (editingField != null) {
                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        when (editingField) {
                            EditingField.NAME -> viewModel.updateUser(newValue.takeIf { it.isNotBlank() }, null, null)
                            EditingField.USERNAME -> viewModel.updateUser(null, newValue.takeIf { it.isNotBlank() }, null)
                            EditingField.PASSWORD -> viewModel.updateUser(null, null, newValue.takeIf { it.isNotBlank() })
                            null -> {}
                        }
                        editingField = null
                        newValue = ""
                    },
                    modifier = Modifier.size(300.dp, 60.dp),
                    colors = ButtonDefaults.buttonColors(Color.Blue)
                ) {
                    Text("Confirmar", fontSize = 20.sp, textAlign = TextAlign.Center)
                }
            }
        }

        if (showDeleteDialog) {
            DeleteAccountDialog(
                onConfirm = {
                    showDeleteDialog = false
                    coroutineScope.launch {
                        viewModel.deleteUser()
                    }
                },
                onDismiss = {
                    showDeleteDialog = false
                }
            )
        }

        if (showClearMedicationsDialog) {
            ClearMedicationsDialog(
                onConfirm = {
                    showClearMedicationsDialog = false
                    coroutineScope.launch {
                        viewModel.clearAllMedications()
                    }
                },
                onDismiss = {
                    showClearMedicationsDialog = false
                }
            )
        }
    }
}

@Composable
fun ClearMedicationsDialog(onConfirm: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Eliminar todos os Medicamentos") },
        text = { Text("Tem a certeza de que quer apagar todos os medicamentos?") },
        confirmButton = {
            Button(onClick = onConfirm, colors = ButtonDefaults.buttonColors(Color.Red)) {
                Text("Apagar", color = Color.White, textAlign = TextAlign.Center)
            }
        },
        dismissButton = {
            Button(onClick = onDismiss, colors = ButtonDefaults.buttonColors(Color.Gray)) {
                Text("Cancelar", color = Color.White, textAlign = TextAlign.Center)
            }
        }
    )
}

@Composable
fun DeleteAccountDialog(onConfirm: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Apagar Conta") },
        text = { Text("Tem a certeza de que quer apagar a conta?") },
        confirmButton = {
            Button(onClick = onConfirm, colors = ButtonDefaults.buttonColors(Color.Red)) {
                Text("Apagar", color = Color.White, textAlign = TextAlign.Center)
            }
        },
        dismissButton = {
            Button(onClick = onDismiss, colors = ButtonDefaults.buttonColors(Color.Gray)) {
                Text("Cancelar", color = Color.White, textAlign = TextAlign.Center)
            }
        }
    )
}

enum class EditingField {
    NAME, USERNAME, PASSWORD
}

@Preview(showBackground = true)
@Composable
private fun ProfileScreenPreview() {
    ProfileScreen(navController = rememberNavController())
}
