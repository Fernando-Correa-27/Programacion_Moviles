package com.correa.campusgps.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.correa.campusgps.model.LugarConDistancia
import com.correa.campusgps.model.UbicacionUi
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

@Composable
fun MapaUbicacion(
    estado: UbicacionUi,
    lugares: List<LugarConDistancia>,
    seleccionado: Int?,
    modifier: Modifier = Modifier
) {
    val contexto = LocalContext.current
    val ciclo = LocalLifecycleOwner.current.lifecycle
    val mapView = remember {
        MapView(contexto).apply {
            setTileSource(TileSourceFactory.MAPNIK)
            setMultiTouchControls(true)
            controller.setZoom(12.0)
            controller.setCenter(GeoPoint(-12.0464, -77.0428))
        }
    }
    DisposableEffect(ciclo) {
        val observador = LifecycleEventObserver { _, evento ->
            when (evento) {
                Lifecycle.Event.ON_RESUME -> mapView.onResume()
                Lifecycle.Event.ON_PAUSE -> mapView.onPause()
                else -> Unit
            }
        }
        ciclo.addObserver(observador)
        onDispose {
            ciclo.removeObserver(observador)
            mapView.onDetach()
        }
    }
    LaunchedEffect(estado) {
        if (estado is UbicacionUi.Lista) {
            mapView.controller.animateTo(GeoPoint(estado.posicion.latitud, estado.posicion.longitud), 16.0, 1000L)
        }
    }
    AndroidView(factory = { mapView }, modifier = modifier, update = { vista ->
        vista.overlays.clear()
        if (estado is UbicacionUi.Lista) {
            vista.overlays += Marker(vista).apply {
                position = GeoPoint(estado.posicion.latitud, estado.posicion.longitud)
                title = "Estás aquí"
                setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            }
        }
        vista.invalidate()
    })
}
