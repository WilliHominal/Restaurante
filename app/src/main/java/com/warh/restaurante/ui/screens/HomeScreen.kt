package com.warh.restaurante.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Whatsapp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.maps.android.compose.*
import com.warh.restaurante.R
import com.warh.restaurante.model.Producto
import com.warh.restaurante.ui.components.CustomDrawer
import com.warh.restaurante.ui.components.CustomTopBar
import com.warh.restaurante.ui.components.ProductCardView
import com.warh.restaurante.utils.MapStyle
import com.warh.restaurante.utils.Screens
import com.warh.restaurante.viewmodel.ProductViewModel
import com.warh.restaurante.viewmodel.UserViewModel
import kotlinx.coroutines.*

@Composable
fun HomeScreen(
    navController: NavController,
    userViewModel: UserViewModel,
    productViewModel: ProductViewModel
) {
    val context = LocalContext.current

    //region GMAPS-VARIABLES
    val esperanzaLatLng = LatLng(-31.44892023020554, -60.93041943633306)
    val posicionCamaraMaps = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(esperanzaLatLng, 16f)
    }

    val opcionesUiMapa = MapUiSettings(
        myLocationButtonEnabled = false,
        zoomControlsEnabled = false,
        tiltGesturesEnabled = false,
        rotationGesturesEnabled = false,
        scrollGesturesEnabled = false,
        compassEnabled = false,
        indoorLevelPickerEnabled = false,
        mapToolbarEnabled = false,
        scrollGesturesEnabledDuringRotateOrZoom = false,
        zoomGesturesEnabled = false
    )
    val propiedadesMapa = MapProperties(mapType = MapType.NORMAL, mapStyleOptions = MapStyleOptions(MapStyle.json))
    //endregion

    var listaProductos by remember { mutableStateOf(listOf<Producto>()) }
    var numeroProducto by remember { mutableStateOf(0) }

    val scaffoldState = rememberScaffoldState()
    val scaffoldScope = rememberCoroutineScope()

    var usuarioLogueado by remember { mutableStateOf(FirebaseAuth.getInstance().currentUser)}

    var notificacionExtendida by remember { mutableStateOf(false) }
    var mensajeNotificacion by remember { mutableStateOf("") }

    LaunchedEffect(true){
        productViewModel.listarProductos {
            listaProductos = it
        }
    }

    suspend fun mostrarAviso() {
        if (!notificacionExtendida) {
            notificacionExtendida = true
            delay(3000L)
            notificacionExtendida = false
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        backgroundColor = MaterialTheme.colors.primary.copy(0.1f),
        topBar = {
            CustomTopBar(
                titulo = context.getString(R.string.TITULO_INICIO),
                permiteVolver = false,
                usuarioLogueado = usuarioLogueado != null,
                usuarioImagenLink =
                    usuarioLogueado?.let {
                        //TODO cambiar por imagen perfil
                        "https://picsum.photos/100"
                    } ?: context.getString(R.string.TEXTO_INICIAR_SESION),
                accionBotonIzquierda = {
                    usuarioLogueado?.let {
                        scaffoldScope.launch{
                            scaffoldState.drawerState.open()
                        }
                    } ?: run {
                        scaffoldScope.launch{
                            mensajeNotificacion = context.getString(R.string.TEXTO_MENSAJE_SESION_NO_INICIADA)
                            mostrarAviso()
                        }
                    }
                },
                accionBotonDerecha = {
                    usuarioLogueado?.let {
                        FirebaseAuth.getInstance().signOut()
                        usuarioLogueado = null
                        scaffoldScope.launch {
                            mensajeNotificacion = context.getString(R.string.TEXTO_MENSAJE_SESION_CERRADA)
                            mostrarAviso()
                        }
                    } ?: navController.navigate(Screens.LoginScreen.route)
                },
            )
        },
        drawerContent = {
            CustomDrawer(
                usuarioLogueado,
                userViewModel,
                navController
            )
        },
        drawerGesturesEnabled = scaffoldState.drawerState.isOpen
    ) { scaffoldPadding ->
        AnimatedVisibility(
            visible = notificacionExtendida,
            enter = slideInVertically(
                initialOffsetY = { altura -> -altura },
                animationSpec = tween(150, easing = LinearOutSlowInEasing)
            ),
            exit = slideOutVertically(
                targetOffsetY = { altura -> -altura },
                animationSpec = tween(100, easing = FastOutLinearInEasing)
            )
        ){
            Surface(
                modifier = Modifier
                    .fillMaxWidth(),
                color = Color.DarkGray
            ){
                Text(
                    mensajeNotificacion,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
                    color = MaterialTheme.colors.onPrimary,
                    style = MaterialTheme.typography.caption
                )
            }
        }

        LazyColumn(
            Modifier
                .padding(scaffoldPadding)
                .padding(
                    start = 25.dp,
                    end = 25.dp,
                )
                .fillMaxSize()
        ) {

            item {
                Text(
                    context.getString(R.string.TEXTO_PRODUCTOS),
                    modifier = Modifier.padding(bottom = 2.dp, top = 20.dp),
                    style = TextStyle(fontSize = 18.sp)
                )
            }

            if (listaProductos.isNotEmpty()){
                item {
                    ProductCardView(
                        imagenProductoLink = listaProductos[numeroProducto].imagenLink,
                        nombreProducto = listaProductos[numeroProducto].nombre,
                        precioProducto = listaProductos[numeroProducto].precio,
                        accionClickIzquierda = {
                            if (numeroProducto > 0)
                                numeroProducto--
                        },
                        accionClickDerecha = {
                            if (numeroProducto < listaProductos.size-1)
                                numeroProducto++
                        }
                    )
                }
            }

            item { Spacer(Modifier.padding(15.dp)) }

            item {
                Text(
                    context.getString(R.string.TEXTO_UBICACION),
                    modifier = Modifier.padding(bottom = 2.dp),
                    style = TextStyle(fontSize = 18.sp)
                )
            }

            item {
                GoogleMap(
                    cameraPositionState = posicionCamaraMaps,
                    modifier = Modifier
                        .height(250.dp)
                        .fillMaxWidth()
                        .border(width = 1.dp, color = MaterialTheme.colors.primary.copy(0.3f)),
                    properties = propiedadesMapa,
                    uiSettings = opcionesUiMapa

                ) {
                    Marker(
                        state = MarkerState(esperanzaLatLng),
                        title = context.getString(R.string.MARCADOR_GMAPS_TITULO),
                        snippet = context.getString(R.string.MARCADOR_GMAPS_DESCRIPCION)
                    )
                }
            }

            item { Spacer(Modifier.padding(15.dp)) }

            item {
                Text(
                    context.getString(R.string.TEXTO_CONTACTO),
                    modifier = Modifier.padding(bottom = 3.dp),
                    style = TextStyle(fontSize = 18.sp)
                )
            }

            item {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(bottom = 2.dp)){
                    Icon(Icons.Default.Whatsapp, "Whatsapp", tint = Color(47, 140, 15))
                    Text(context.getString(R.string.TEXTO_NUMERO_WHATSAPP))
                }
            }

            item {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp)){
                    Icon(Icons.Default.Phone, "Teléfono")
                    Text(context.getString(R.string.TEXTO_NUMERO_TELEFONO_FIJO))
                }
            }
        }
    }
}