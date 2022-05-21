package com.warh.restaurante.ui.components

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.warh.restaurante.ui.screens.Empleado
import com.warh.restaurante.R

@Composable
fun EmployeeCardView(
    empleado: Empleado,
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
            .border(width = 1.dp, color = MaterialTheme.colors.primary.copy(0.3f), shape = RoundedCornerShape(10.dp)),
        elevation = 3.dp,
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(
            Modifier.fillMaxWidth().background(MaterialTheme.colors.secondaryVariant)
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colors.secondary),
                horizontalArrangement = Arrangement.SpaceBetween,
            ){
                Text(
                    empleado.nombreCompleto.uppercase(),
                    modifier = Modifier.padding(top = 3.dp, bottom = 3.dp, start = 7.dp),
                    style = MaterialTheme.typography.body1
                )
                Text(
                    context.getString(R.string.TEXTO_ID_TEMPLATE, empleado.id.toString()),
                    modifier = Modifier.padding(top = 3.dp, bottom = 3.dp, end = 7.dp),
                    style = MaterialTheme.typography.body1
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(5.dp),
                modifier = Modifier.padding(5.dp)
            ){
                Text(context.getString(R.string.TEXTO_DNI_EMPLEADO_TEMPLATE,empleado.dni), style = MaterialTheme.typography.body2)

                Text(context.getString(R.string.TEXTO_DNI_EMPLEADO_TEMPLATE,empleado.direccion), style = MaterialTheme.typography.body2)

                Text(context.getString(R.string.TEXTO_TEL_EMPLEADO_TEMPLATE, empleado.telefono), style = MaterialTheme.typography.body2)
            }
        }
    }
}