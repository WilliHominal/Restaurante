package com.warh.restaurante.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.warh.restaurante.R
import com.warh.restaurante.utils.Screens

@Composable
fun CustomDrawer(usuarioLogueado: Boolean, navController: NavController) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primary.copy(0.1f)),
    ) {

        Column(
            Modifier
                .background(MaterialTheme.colors.primary)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.padding(10.dp))

            Image(
                painter = if (usuarioLogueado) {
                    rememberAsyncImagePainter(
                        "https://picsum.photos/100",
                        placeholder = painterResource(
                            R.drawable.placeholder
                        )
                    )
                } else
                    painterResource(id = R.drawable.placeholder),
                contentDescription = "Imagen de perfil",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .border(1.dp, Color.Black, CircleShape)
            )

            if (usuarioLogueado) //TODO cambiar nombre completo por nombre del usuario
                Text("NOMBRE COMPLETO", style = MaterialTheme.typography.caption)
            else
                Text(
                    context.getString(R.string.TEXTO_INICIAR_SESION),
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.clickable {
                        navController.navigate(
                            Screens.LoginScreen.route
                        )
                    })

            Spacer(Modifier.padding(10.dp))
        }

        Divider(color = MaterialTheme.colors.primary.copy(0.5f))

        Column(
            Modifier
                .fillMaxWidth()
                .padding(15.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            if (usuarioLogueado) {
                Text(
                    context.getString(Screens.NewOrderScreen.title),
                    modifier = Modifier
                        .clickable { navController.navigate(Screens.NewOrderScreen.route) }
                        .fillMaxWidth()
                )
                Text(
                    context.getString(Screens.MyOrderListScreen.title),
                    modifier = Modifier
                        .clickable { navController.navigate(Screens.MyOrderListScreen.route) }
                        .fillMaxWidth()
                )
                Text(
                    context.getString(Screens.MyAccountScreen.title),
                    modifier = Modifier
                        .clickable { navController.navigate(Screens.MyAccountScreen.route) }
                        .fillMaxWidth()
                )
                Text(
                    context.getString(R.string.BOTON_CERRAR_SESION),
                    modifier = Modifier
                        .clickable { /*TODO Cerrar sesión*/ }
                        .fillMaxWidth()
                )
            }
        }
    }
}