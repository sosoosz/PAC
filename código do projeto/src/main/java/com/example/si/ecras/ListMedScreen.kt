package com.example.si.ecras

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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

@Composable
fun ListMedScreen(navController: NavHostController, viewModel: MedicamentoViewModel = viewModel()) {
    val medicamentos by viewModel.medicamentos.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            TopButtons(navController)
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)
            ) {
                items(medicamentos) { medicamento ->
                    MedicamentoItem(medicamento = medicamento)
                }
            }
        }

        BottomBar(navController, modifier = Modifier.align(Alignment.BottomCenter))
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
                .clickable { navController.navigate(Screen.GerenciarMedScreen.route) }
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
                    .clickable { navController.navigate(Screen.CalendarScreen.route) }
            )
        }
    }
}

@Composable
private fun MedicamentoItem(medicamento: Medicamento) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Nome: ${medicamento.nome}",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Prescrição: ${medicamento.prescricao}",
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text("━━━━━━━━━━━━")
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Preview(showBackground = true)
@Composable
private fun FourthScreenPreview() {
    ListMedScreen(navController = rememberNavController(), viewModel = viewModel())
}
