package com.warh.restaurante.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.warh.restaurante.ui.screens.Pedido
import com.warh.restaurante.R

@Composable
fun OrderCardView(
    pedido: Pedido,
    accionClickCard: () -> Unit
) {
    val context = LocalContext.current

    val total = pedido.productos.map { productoComprado -> productoComprado.cantidadTotal * productoComprado.precioUnitarioPagado.toInt() }.sum()

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
                .background(MaterialTheme.colors.secondaryVariant),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colors.secondary)
            ){
                Text(
                    context.getString(R.string.TEXTO_PEDIDO_NUMERO_TEMPLATE, pedido.id.toString()),
                    modifier = Modifier
                        .padding(vertical = 5.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Center
                )
            }

            pedido.productos.forEach { productoComprado ->
                Row(
                    Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Text(
                        context.getString(R.string.TEXTO_PRODUCTO_PEDIDO_TEMPLATE, productoComprado.producto.nombre, productoComprado.cantidadTotal.toString()),
                        style = MaterialTheme.typography.body2,
                        modifier = Modifier
                            .padding(start = 5.dp)
                    )
                    Text(
                        context.getString(R.string.TEXTO_PRECIO_PRODUCTO_TEMPLATE, (productoComprado.precioUnitarioPagado.toInt() * productoComprado.cantidadTotal).toString()),
                        style = MaterialTheme.typography.body2,
                        modifier = Modifier
                            .padding(end = 5.dp)
                    )
                }
            }

            Divider(Modifier.fillMaxWidth(), color = Color.Gray.copy(0.5f))

            Row(
                Modifier
                    .fillMaxWidth(),
            ){
                Text(
                    context.getString(R.string.TEXTO_TOTAL_PEDIDO_TEMPLATE, total.toString()),
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier
                        .padding(end = 5.dp, bottom = 5.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.End,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}