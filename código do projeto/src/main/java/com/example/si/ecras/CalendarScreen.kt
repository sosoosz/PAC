package com.example.si.ecras

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
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
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

@RequiresApi(Build.VERSION_CODES.O)
fun filterAndSortMedicamentos(medicamentos: List<Medicamento>, checkedStates: Map<Int, LocalDate>): List<Medicamento> {
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val today = LocalDate.now()
    val nextWeek = today.plusDays(7)

    val filteredMedicamentos = medicamentos.filter { medicamento ->
        medicamento.isActive && try {
            val dates = medicamento.data.split("-").map { LocalDate.parse(it.trim(), formatter) }
            val startDate = dates[0]
            val endDate = dates[1]
            !endDate.isBefore(today) && !startDate.isAfter(nextWeek) && checkedStates[medicamento.id] != today
        } catch (e: DateTimeParseException) {
            false
        }
    }

    return filteredMedicamentos.sortedBy { medicamento ->
        try {
            LocalTime.parse(medicamento.hora, DateTimeFormatter.ofPattern("HH:mm"))
        } catch (e: DateTimeParseException) {
            LocalTime.MIN
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarScreen(
    navController: NavHostController,
    viewModel: MedicamentoViewModel = viewModel(),
    checkedStates: Map<Int, LocalDate>,
    onCheckedStateChange: (Int, LocalDate) -> Unit
) {
    val medicamentos by viewModel.medicamentos.collectAsState()
    val sortedMedicamentos = filterAndSortMedicamentos(medicamentos, checkedStates)

    LaunchedEffect(medicamentos) {
        viewModel.loadMedicamentosForUser(1) // Replace with actual user ID
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                item { Spacer(modifier = Modifier.height(100.dp)) }
                items(sortedMedicamentos) { medicamento ->
                    MedicamentoItem(
                        medicamento = medicamento,
                        checked = checkedStates[medicamento.id] == LocalDate.now()
                    ) {
                        onCheckedStateChange(medicamento.id, if (it) LocalDate.now() else LocalDate.MIN)
                    }
                }
            }
        }

        BottomBar(navController, modifier = Modifier.align(Alignment.BottomCenter))

        TopButtons(navController)
    }
}

@Composable
private fun TopButtons(navController: NavHostController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Image(
            painter = painterResource(id = R.drawable.seta2),
            contentDescription = "Voltar",
            modifier = Modifier
                .size(60.dp)
                .clickable { navController.navigate(Screen.MainScreen.route) }
        )
        Image(
            painter = painterResource(id = R.drawable.perfil2),
            contentDescription = "Perfil",
            modifier = Modifier
                .size(60.dp)
                .clickable { navController.navigate(Screen.ProfileScreen.route) }
        )
    }
}

@Composable
private fun BottomBar(navController: NavHostController, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(Color.Blue),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Image(
                painter = painterResource(id = R.drawable.casa),
                contentDescription = "Casa",
                modifier = Modifier
                    .size(60.dp)
                    .offset(x = (-28).dp, y = (0).dp)
                    .clickable { navController.navigate(Screen.MainScreen.route) }
            )

            Image(
                painter = painterResource(id = R.drawable.glossario),
                contentDescription = "Glossário",
                modifier = Modifier
                    .size(50.dp)
                    .offset(x = (-5).dp, y = (5).dp)
                    .clickable { navController.navigate(Screen.GlossarioScreen.route) }
            )

            Image(
                painter = painterResource(id = R.drawable.calendario),
                contentDescription = "Calendário",
                modifier = Modifier
                    .size(50.dp)
                    .offset(x = (18).dp, y = (5).dp)
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun MedicamentoItem(medicamento: Medicamento, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    val currentTime = LocalTime.now()
    val medicamentoTime = try {
        LocalTime.parse(medicamento.hora, DateTimeFormatter.ofPattern("HH:mm"))
    } catch (e: DateTimeParseException) {
        LocalTime.MIN
    }
    val textColor = if (medicamentoTime.isBefore(currentTime)) Color.Red else Color.Black

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Medicamento: ${medicamento.nome}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = textColor
            )
            Text(
                text = "Dosagem: ${medicamento.dosagem}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = textColor
            )

            Text(
                text = "Quantidade: ${medicamento.quantidade}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = textColor
            )

            Text(
                text = "Data: ${medicamento.data}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = textColor
            )

            Text(
                text = "Hora: ${medicamento.hora}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = textColor
            )

            Text(
                text = "Prescrição: ${medicamento.prescricao}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = textColor
            )

            Text("━━━━━━━━━━━━", color = textColor)
            Spacer(modifier = Modifier.height(20.dp))
        }
        Checkbox(checked = checked, onCheckedChange = onCheckedChange)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
private fun CalendarScreenPreview() {
    val navController = rememberNavController()
    CalendarScreen(navController = navController, viewModel = viewModel(), checkedStates = mapOf(), onCheckedStateChange = { _, _ -> })
}
