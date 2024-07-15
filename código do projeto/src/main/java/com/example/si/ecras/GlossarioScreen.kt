package com.example.si.ecras

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.si.R
import com.example.si.Screen

//Menu Glosário

@Composable
fun GlossarioScreen(navController: NavHostController) {
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

        // Bottom Center Glossary Image
        Image(
            painter = painterResource(id = R.drawable.glossario),
            contentDescription = "Glossário",
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(10.dp)
                .size(50.dp)
        )

        // Top Start Back Arrow Image
        Image(
            painter = painterResource(id = R.drawable.seta2),
            contentDescription = "Voltar",
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
                .size(60.dp)
                .clickable { navController.navigate(Screen.MainScreen.route) }
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


        Spacer(modifier = Modifier.height(200.dp))

        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.Start
        ) {
            Spacer(modifier = Modifier.height(230.dp))
            Text("Glossário",
                Modifier.padding(14.dp),
                fontSize = 40.sp,
                color = Color.Blue)
        }

        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(20.dp))



            Button(
                onClick = { navController.navigate(route = Screen.GerenciarMedScreen.route) },
                modifier = Modifier.size(300.dp, 60.dp),
                colors = ButtonDefaults.buttonColors(Color.Blue)
                
            ) {
                Text("Gerenciar medicamentos", fontSize = 20.sp)
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = { navController.navigate(Screen.PrescriptionScreen.route) },
                modifier = Modifier.size(300.dp, 60.dp),
                colors = ButtonDefaults.buttonColors(Color.Blue)
            ) {
                Text("Adicionar prescrição", fontSize = 20.sp)
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

    @Composable
    fun ButtonWithImage(image: Painter, onClick: () -> Unit, modifier: Modifier = Modifier) {
        Box(
            modifier = modifier
                .size(300.dp, 60.dp)
                .clickable(onClick = onClick)
        ) {
            Image(
                painter = image,
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(20.dp)
            )
        }
}

@Preview(showBackground = true)
@Composable
private fun FirstScreenPreview() {
    GlossarioScreen(navController = rememberNavController())
}