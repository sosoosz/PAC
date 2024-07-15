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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.si.R
import com.example.si.Screen
import com.example.si.model.Medicamento
import com.example.si.model.MedicamentoViewModel

@Composable
fun EditMedScreen(
    navController: NavHostController,
    viewModel: MedicamentoViewModel = viewModel()
) {
    val medicamentos by viewModel.medicamentos.collectAsState()
    var selectedMedicamento by remember { mutableStateOf<Medicamento?>(null) }
    var medicamentoToDeactivate by remember { mutableStateOf<Medicamento?>(null) }

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
        // Top Start Back Arrow Image
        Image(
            painter = painterResource(id = R.drawable.seta2),
            contentDescription = "Voltar",
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
                .size(60.dp)
                .clickable { navController.navigate(Screen.GerenciarMedScreen.route) }
        )

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
                .clickable { navController.navigate(Screen.MainScreen.route) }
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
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        if (selectedMedicamento == null) {
            // Display list of medications for selection
            medicamentos.filter { it.isActive }.forEach { medicamento ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .clickable { selectedMedicamento = medicamento }
                    ) {
                        Text(
                            text = medicamento.nome,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                        Text(
                            text = "Dosagem: ${medicamento.dosagem}",
                            fontSize = 16.sp,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                        Text(
                            text = "Hora: ${medicamento.hora}",
                            fontSize = 16.sp,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                    }
                    Row {
                        Text(
                            text = "Editar",
                            color = Color.Blue,
                            fontSize = 18.sp,
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .clickable { selectedMedicamento = medicamento }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Remover do Calendário",
                            color = Color.Red,
                            fontSize = 18.sp,
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .clickable { medicamentoToDeactivate = medicamento }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Button to navigate back to the previous screen
            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    Color.Blue
                )
            ) {
                Text(
                    text = "Voltar",
                    color = Color.White,
                    fontSize = 18.sp
                )
            }
        } else {
            // Display editable fields for selected medication
            EditableMedicamentoItem(medicamento = selectedMedicamento!!) { updatedMedicamento ->
                // Update medication in ViewModel
                viewModel.updateMedicamento(updatedMedicamento)
                // Return to the list after saving
                selectedMedicamento = null
            }

            // Button to navigate back to the list
            Button(
                onClick = { selectedMedicamento = null },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    Color.Blue
                )
            ) {
                Text(
                    text = "Voltar",
                    color = Color.White,
                    fontSize = 18.sp
                )
            }
        }
    }

    // Show confirmation dialog if a medication is selected for deactivation
    medicamentoToDeactivate?.let { medicamento ->
        ConfirmDeactivateDialog(
            medicamento = medicamento,
            onConfirm = {
                viewModel.updateIsActive(medicamento.id, false)
                medicamentoToDeactivate = null
            },
            onDismiss = {
                medicamentoToDeactivate = null
            }
        )
    }
}

@Composable
private fun EditableMedicamentoItem(
    medicamento: Medicamento,
    onUpdateMedicamento: (Medicamento) -> Unit
) {
    // State for editable fields
    val (nome, setNome) = remember { mutableStateOf(medicamento.nome) }
    val (dosagem, setDosagem) = remember { mutableStateOf(medicamento.dosagem) }
    val (quantidade, setQuantidade) = remember { mutableStateOf(medicamento.quantidade) }
    val (data, setData) = remember { mutableStateOf(medicamento.data) }
    val (hora, setHora) = remember { mutableStateOf(medicamento.hora) }

    Column {
        // Editable fields for medication details
        TextField(
            value = nome,
            onValueChange = setNome,
            label = { Text("Nome") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )

        TextField(
            value = dosagem,
            onValueChange = setDosagem,
            label = { Text("Dosagem") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )

        TextField(
            value = quantidade,
            onValueChange = setQuantidade,
            label = { Text("Quantidade") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )

        TextField(
            value = data,
            onValueChange = setData,
            label = { Text("Data") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )

        TextField(
            value = hora,
            onValueChange = setHora,
            label = { Text("Hora") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )

        // Button to save changes
        Button(
            onClick = {
                // Create updated Medicamento object
                val updatedMedicamento = medicamento.copy(
                    nome = nome,
                    dosagem = dosagem,
                    quantidade = quantidade,
                    data = data,
                    hora = hora
                )
                // Update medication via callback
                onUpdateMedicamento(updatedMedicamento)
            },
            modifier = Modifier.padding(top = 8.dp),
            colors = ButtonDefaults.buttonColors(
                Color.Blue
            )
        ) {
            Text(
                text = "Salvar Alterações",
                color = Color.White
            )
        }
    }
}

@Composable
private fun ConfirmDeactivateDialog(
    medicamento: Medicamento,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Confirmar Desativação")
        },
        text = {
            Text(text = "Tem certeza de que deseja remover o medicamento '${medicamento.nome}' do calendário?")
        },
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

@Preview(showBackground = true)
@Composable
private fun EditMedScreenPreview() {
    EditMedScreen(navController = rememberNavController(), viewModel = viewModel())
}
