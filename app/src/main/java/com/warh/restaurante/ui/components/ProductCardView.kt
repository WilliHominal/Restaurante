package com.warh.restaurante.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.warh.restaurante.R

@Composable
fun ProductCardView(
    imagenProductoLink: String,
    nombreProducto: String,
    precioProducto: String,
    accionClickIzquierda: () -> Unit,
    accionClickDerecha: () -> Unit,
    accionClickCard: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                accionClickCard()
            }
            .size(250.dp)
            .background(color = Color.Transparent)
            .border(width = 1.dp, color = MaterialTheme.colors.primary.copy(0.3f), shape = RoundedCornerShape(15.dp)),
        elevation = 3.dp,
        shape = RoundedCornerShape(15.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ){
                Image(
                    painter = rememberAsyncImagePainter(imagenProductoLink, placeholder = painterResource(R.drawable.placeholder)),
                    contentDescription = "Producto",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds,
                )

                Surface(modifier = Modifier.fillMaxSize(), color = Color.White.copy(0.3f)){}

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ){
                    IconButton(onClick = { accionClickIzquierda() }) {
                        Icon(Icons.Default.ArrowBackIos, "Anterior", Modifier.background(color = Color.White.copy(0.5f), shape = CircleShape))
                    }
                    IconButton(onClick = { accionClickDerecha() }) {
                        Icon(Icons.Default.ArrowForwardIos, "Siguiente", Modifier.background(color = Color.White.copy(0.5f), shape = CircleShape))
                    }
                }
            }

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(
                    if (nombreProducto.length < 29) nombreProducto.uppercase()
                    else nombreProducto.substring(0,27) + "...",
                    modifier = Modifier.padding(vertical = 3.dp),
                    style = MaterialTheme.typography.caption
                )
                Text(
                    "$${precioProducto}",
                    modifier = Modifier.padding(vertical = 3.dp),
                    style = MaterialTheme.typography.caption
                )
            }
        }
    }
}
