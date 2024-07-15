package com.example.si.ecras

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.example.si.Screen
import com.example.si.model.Medicamento
import com.example.si.model.MedicamentoViewModel
import com.example.si.R
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun PrescriptionScreen(
    navController: NavHostController,
    viewModel: MedicamentoViewModel = viewModel()
) {
    val medicamentos by viewModel.medicamentos.collectAsState()
    
    Box(
        Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(bottom = 70.dp) // Ensure padding for the blue bar
        ) {
            Spacer(modifier = Modifier.height(100.dp)) // Ensure space for the top buttons
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(medicamentos) { medicamento ->
                    EditableMedicamentoWithPrescriptionItem(
                        medicamento = medicamento,
                        onUpdateMedicamento = { updatedMedicamento ->
                            viewModel.updateMedicamento(updatedMedicamento)
                        }
                    )
                }
            }
        }

        // Blue bar at the bottom
        Box(
            modifier = Modifier
                .height(70.dp)
                .fillMaxWidth()
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

        // Top Start Back Arrow Image
        Image(
            painter = painterResource(id = R.drawable.seta2),
            contentDescription = "Voltar",
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
                .size(60.dp)
                .clickable { navController.popBackStack() }
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
}

@Composable
private fun EditableMedicamentoWithPrescriptionItem(
    medicamento: Medicamento,
    onUpdateMedicamento: (Medicamento) -> Unit
) {
    var prescricao by remember { mutableStateOf(medicamento.prescricao) }
    var updateJob by remember { mutableStateOf<Job?>(null) }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = prescricao,
            onValueChange = { updatedPrescricao ->
                prescricao = updatedPrescricao
                updateJob?.cancel()
                updateJob = coroutineScope.launch {
                    delay(300) // Debounce time
                    onUpdateMedicamento(
                        medicamento.copy(prescricao = updatedPrescricao)
                    )
                }
            },
            label = { Text("Prescrição") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Nome: ${medicamento.nome}, Dosagem: ${medicamento.dosagem}",
            fontSize = 16.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SeventhScreenPreview() {
    PrescriptionScreen(navController = rememberNavController())
}
