package com.correa.campusgps.ui

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.MotionEvent
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
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.correa.campusgps.R
import com.correa.campusgps.model.LugarConDistancia
import com.correa.campusgps.model.UbicacionUi
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.OverlayWithIW
import org.osmdroid.views.overlay.Polygon

/** Puente mínimo entre los botones de la pantalla y el MapView de osmdroid. */
class ControladorZoomMapa {
    internal var acercar: (() -> Unit)? = null
    internal var alejar: (() -> Unit)? = null

    fun acercarMapa() = acercar?.invoke()
    fun alejarMapa() = alejar?.invoke()
}

@Composable
fun MapaUbicacion(
    estado: UbicacionUi,
    lugares: List<LugarConDistancia>,
    seleccionado: Int?,
    masCercanoId: Int?,
    radioMetros: Int?,
    controladorZoom: ControladorZoomMapa,
    modifier: Modifier = Modifier
) {
    val contexto = LocalContext.current
    val ciclo = LocalLifecycleOwner.current.lifecycle
    val mapView = remember {
        MapView(contexto).apply {
            setTileSource(TileSourceFactory.MAPNIK)
            setMultiTouchControls(true)
            setBuiltInZoomControls(false)
            controller.setZoom(16.0)
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
    DisposableEffect(mapView, controladorZoom) {
        controladorZoom.acercar = {
            mapView.controller.animateTo(
                mapView.mapCenter as GeoPoint,
                (mapView.zoomLevel + 1.0).coerceAtMost(mapView.maxZoomLevel),
                220L
            )
        }
        controladorZoom.alejar = {
            mapView.controller.animateTo(
                mapView.mapCenter as GeoPoint,
                (mapView.zoomLevel - 1.0).coerceAtLeast(mapView.minZoomLevel),
                220L
            )
        }
        onDispose {
            controladorZoom.acercar = null
            controladorZoom.alejar = null
        }
    }
    LaunchedEffect(estado) {
        if (estado is UbicacionUi.Lista) {
            mapView.controller.animateTo(GeoPoint(estado.posicion.latitud, estado.posicion.longitud), 17.0, 1000L)
        }
    }
    LaunchedEffect(seleccionado, lugares) {
        val lugarSeleccionado = lugares.firstOrNull { it.lugar.id == seleccionado }?.lugar ?: return@LaunchedEffect
        mapView.controller.animateTo(GeoPoint(lugarSeleccionado.latitud, lugarSeleccionado.longitud), 18.0, 1000L)
        mapView.overlays.filterIsInstance<Marker>()
            .firstOrNull { it.id == lugarSeleccionado.id.toString() }
            ?.showInfoWindow()
    }
    AndroidView(factory = { mapView }, modifier = modifier, update = { vista ->
        vista.overlays.filterIsInstance<OverlayWithIW>().forEach { it.closeInfoWindow() }
        vista.overlays.clear()
        if (radioMetros != null && estado is UbicacionUi.Lista) {
            val centro = GeoPoint(estado.posicion.latitud, estado.posicion.longitud)
            vista.overlays += CirculoRadio(vista).apply {
                points = Polygon.pointsAsCircle(centro, radioMetros.toDouble())
                fillColor = 0x332563EB
                strokeColor = 0xFF2563EB.toInt()
                strokeWidth = 3f
            }
        }
        lugares.forEach { conDistancia ->
            val lugar = conDistancia.lugar
            vista.overlays += Marker(vista).apply {
                position = GeoPoint(lugar.latitud, lugar.longitud)
                title = lugar.nombre
                snippet = lugar.categoria
                id = lugar.id.toString()
                setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                icon = pinTenido(
                    contexto,
                    if (lugar.id == masCercanoId) COLOR_MAS_CERCANO else COLOR_LUGAR
                )
            }
        }
        if (estado is UbicacionUi.Lista) {
            vista.overlays += Marker(vista).apply {
                position = GeoPoint(estado.posicion.latitud, estado.posicion.longitud)
                title = "Estás aquí"
                setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                icon = pinTenido(contexto, COLOR_UBICACION)
            }
        }
        vista.invalidate()
    })
}

private val COLOR_LUGAR = 0xFF2563EB.toInt()
private val COLOR_MAS_CERCANO = 0xFF059669.toInt()
private val COLOR_UBICACION = 0xFFDC2626.toInt()

/** Círculo solo decorativo: no intercepta toques ni mueve la cámara. */
private class CirculoRadio(mapa: MapView) : Polygon(mapa) {
    override fun onSingleTapConfirmed(event: MotionEvent, mapView: MapView): Boolean = false
}

private fun pinTenido(contexto: Context, color: Int): Drawable {
    val original = requireNotNull(ContextCompat.getDrawable(contexto, R.drawable.ic_pin))
    return DrawableCompat.wrap(original).mutate().also { DrawableCompat.setTint(it, color) }
}
