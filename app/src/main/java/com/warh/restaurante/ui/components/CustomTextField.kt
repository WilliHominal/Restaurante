package com.warh.restaurante.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun CustomTextField(
    texto: String,
    placeholder: String,
    modifier: Modifier = Modifier,
    habilitado: Boolean = true,
    iconoTrasero: @Composable (() -> Unit)? = null,
    passwordVisible: Boolean = true,
    accionCambioDeValor: (String) -> Unit,
    tipoTeclado: KeyboardType = KeyboardType.Text
) {
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        value = texto,
        onValueChange = accionCambioDeValor,
        textStyle = MaterialTheme.typography.body2,
        label = {
            Text(placeholder,
                style=MaterialTheme.typography.caption, color = Color.Gray)
        },
        singleLine = true,
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        modifier = Modifier
            .then(modifier)
            .fillMaxWidth(),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.background,
            cursorColor = MaterialTheme.colors.background,
        ),
        enabled = habilitado,
        keyboardActions = KeyboardActions(
            onDone = {focusManager.clearFocus()}
        ),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done,
            keyboardType = tipoTeclado
        ),
        trailingIcon = iconoTrasero
    )
}