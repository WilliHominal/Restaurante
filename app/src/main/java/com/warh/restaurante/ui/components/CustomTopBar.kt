package com.warh.restaurante.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.warh.restaurante.R

@Composable
fun CustomTopBar(
    titulo: String,
    permiteVolver: Boolean = false,
    accionBotonIzquierda: () -> Unit,
    usuarioLogueado: Boolean = false,
    usuarioImagenLink: String,
    accionBotonDerecha: (() -> Unit)? = null,
) {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.primaryVariant,
        contentColor = Color.White,
        elevation = 5.dp,
        modifier = Modifier.height(45.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            IconButton(onClick = accionBotonIzquierda){
                if (permiteVolver)
                    Icon(Icons.Default.ArrowBack, "Volver", tint = Color.White)
                else
                    Icon(Icons.Default.Menu, "Menu lateral", tint = Color.White)
            }

            Text(titulo, style = MaterialTheme.typography.body1, color = Color.White)

            if (usuarioImagenLink.isBlank()){
                Text("")
            } else {
                IconButton(onClick = accionBotonDerecha!!) {
                    if (usuarioLogueado){
                        Image(
                            painter = rememberAsyncImagePainter(usuarioImagenLink, placeholder = painterResource(
                                R.drawable.placeholder)),
                            contentDescription = "Imagen de perfil",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .border(1.dp, Color.Black, CircleShape)
                        )
                    } else {
                        Text(usuarioImagenLink, style = MaterialTheme.typography.caption, color = Color.White)
                    }
                }
            }
        }
    }
}