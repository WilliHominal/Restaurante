package com.warh.restaurante.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.warh.restaurante.R
import com.warh.restaurante.model.Producto

@Composable
fun ManagerProductCardView(
    producto: Producto,
    accionClickCard: () -> Unit
) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                accionClickCard()
            }
            .padding(vertical = 10.dp, horizontal = 25.dp)
            .background(color = Color.Transparent)
            .border(
                width = 1.dp,
                color = MaterialTheme.colors.primary.copy(0.3f),
                shape = RoundedCornerShape(10.dp)
            ),
        elevation = 3.dp,
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.secondaryVariant)
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colors.secondary),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    producto.nombre.uppercase(),
                    modifier = Modifier.padding(top = 3.dp, bottom = 3.dp, start = 7.dp),
                    style = MaterialTheme.typography.body1
                )
                Text(
                    context.getString(R.string.TEXTO_ID_TEMPLATE, producto.id.toString()),
                    modifier = Modifier.padding(top = 3.dp, bottom = 3.dp, end = 7.dp),
                    style = MaterialTheme.typography.body1
                )
            }

            Row(
                Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colors.secondaryVariant),
            ){
                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .padding(5.dp)
                        .weight(1f)
                        .height(120.dp)
                ) {
                    Text(producto.categoria, style = MaterialTheme.typography.body2)

                    Text(context.getString(R.string.TEXTO_PRECIO_PRODUCTO_TEMPLATE, producto.precio), style = MaterialTheme.typography.body2, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
                }
                Box(
                    modifier = Modifier.weight(1f).fillMaxHeight()
                ){
                    Image(
                        painter = rememberAsyncImagePainter(
                            producto.imagenLink,
                            placeholder = painterResource(
                                R.drawable.placeholder
                            )
                        ),
                        contentDescription = "Imagen producto",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize().height(130.dp)
                    )
                }
            }
        }
    }
}