package com.example.si.ecras

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.si.Screen
import com.example.si.model.Medicamento
import com.example.si.model.MedicamentoViewModel
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddMedScreen(navController: NavHostController, viewModel: MedicamentoViewModel, userId: Int) {
    var medicamento by remember { mutableStateOf("") }
    var dosagem by remember { mutableStateOf("") }
    var quantidade by remember { mutableStateOf("") }
    var data by remember { mutableStateOf("") }
    var hora by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(14.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            "Insira o nome do medicamento:",
            fontSize = 16.sp, fontWeight = FontWeight.Bold,
        )
        TextField(
            value = medicamento,
            onValueChange = { medicamento = it }
        )
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            "Insira a dosagem:",
            fontSize = 16.sp, fontWeight = FontWeight.Bold,
        )
        TextField(
            value = dosagem,
            onValueChange = { dosagem = it }
        )
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            "Insira a quantidade:",
            fontSize = 16.sp, fontWeight = FontWeight.Bold,
        )
        TextField(
            value = quantidade,
            onValueChange = { quantidade = it }
        )
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            "Insira a data (dd/MM/yyyy-dd/MM/yyyy):",
            fontSize = 16.sp, fontWeight = FontWeight.Bold,
        )
        TextField(
            value = data,
            onValueChange = { data = it },
            placeholder = { Text("dd/MM/yyyy-dd/MM/yyyy") }
        )
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            "Insira a hora (HH:mm):",
            fontSize = 16.sp, fontWeight = FontWeight.Bold,
        )
        TextField(
            value = hora,
            onValueChange = { hora = it },
            placeholder = { Text("HH:mm") }
        )
        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                val dateParts = data.split("-")
                val isValidDate = dateParts.size == 2 && dateParts.all {
                    try {
                        LocalDate.parse(it.trim(), DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                        true
                    } catch (e: DateTimeParseException) {
                        false
                    }
                }

                val isValidTime = try {
                    LocalTime.parse(hora, DateTimeFormatter.ofPattern("HH:mm"))
                    true
                } catch (e: DateTimeParseException) {
                    false
                }

                if (!isValidDate) {
                    Toast.makeText(context, "Data inválida. Use o formato dd/MM/yyyy-dd/MM/yyyy.", Toast.LENGTH_LONG).show()
                } else if (!isValidTime) {
                    Toast.makeText(context, "Hora inválida. Use o formato HH:mm.", Toast.LENGTH_LONG).show()
                } else {
                    val newMedicamento = Medicamento(
                        userId = userId,
                        nome = medicamento,
                        dosagem = dosagem,
                        quantidade = quantidade,
                        data = data,
                        hora = hora,
                        prescricao = "",
                        id = 0
                    )
                    viewModel.addMedicamento(newMedicamento)
                    navController.navigate(Screen.ListMedScreen.route)
                }
            },
            colors = ButtonDefaults.buttonColors(Color.Blue)
        ) {
            Text(text = "Adicionar novo medicamento", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
private fun AddMedScreenPreview() {
    AddMedScreen(navController = rememberNavController(), viewModel = viewModel(), userId = 1)
}
