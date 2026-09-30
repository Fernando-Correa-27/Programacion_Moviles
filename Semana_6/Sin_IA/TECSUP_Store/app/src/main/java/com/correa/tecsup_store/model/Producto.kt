package com.correa.tecsup_store.model

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.Devices
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Watch

/**
 * Modelo de producto heredado del Laboratorio 4 (carrito TECSUP) y extendido
 * con los datos que necesita el catalogo de la tienda.
 */
data class Producto(
    val id: Int,
    val nombre: String,
    val precio: Double,
    val cantidad: Int,
    val categoria: String,
    val descripcion: String,
    val emoji: String
)

/** Secciones mostradas en el LazyColumn de la pantalla de inicio. */
data class SeccionCatalogo(
    val titulo: String,
    val productos: List<Producto>
)

/** Categoria del filtro rapido, con su icono asociado. */
data class Categoria(
    val nombre: String,
    val icono: ImageVector
)

object CatalogoTienda {

    val categorias = listOf(
        Categoria("Todos", Icons.Default.MenuBook),
        Categoria("Tecnología", Icons.Default.Computer),
        Categoria("Dispositivos", Icons.Default.Devices),
        Categoria("Audio", Icons.Default.Headphones),
        Categoria("Accesorios", Icons.Default.Watch),
        Categoria("Universidad", Icons.Default.School)
    )

    val productos: List<Producto> = listOf(
        Producto(1, "Laptop Tecsup Pro 14", 2499.00, 12, "Tecnología", "Procesador de 14 pulgadas, 16 GB RAM y SSD de 512 GB.", "💻"),
        Producto(2, "Tablet Tecsup 11\"", 1299.00, 8, "Dispositivos", "Pantalla IPS de 11 pulgadas con 128 GB de almacenamiento.", "📱"),
        Producto(3, "Audífonos Studio TECSUP", 349.00, 25, "Audio", "Cancelación de ruido activa y 30 horas de batería.", "🎧"),
        Producto(4, "Mouse inalámbrico Pro", 89.90, 60, "Accesorios", "Sensor de 12000 DPI y conexión Bluetooth 5.0.", "🖱️"),
        Producto(5, "Libro Algoritmos y Programación", 120.00, 40, "Universidad", "Texto universitario de algoritmos en Kotlin y Java.", "📚"),
        Producto(6, "Monitor Tecsup 24\"", 899.00, 10, "Tecnología", "Panel IPS Full HD con cámara y micrófono integrados.", "🖥️"),
        Producto(7, "Smartwatch Tecsup Fit", 549.00, 15, "Accesorios", "Rastreo de actividad y frecuencia cardiaca continua.", "⌚"),
        Producto(8, "Speaker Bluetooth Bass", 199.00, 22, "Audio", "Potencia de 20 W con protección contra salpicaduras.", "🔊"),
        Producto(9, "Teclado Mecánico TECSUP", 429.00, 14, "Accesorios", "Switches mecánicos y iluminação RGB.", "⌨️"),
        Producto(10, "Mochila Urban Tecsup", 159.00, 35, "Universidad", "Compartimento acolchado para laptop de 15 pulgadas.", "🎒"),
        Producto(11, "Tablet stylus edition", 1599.00, 6, "Dispositivos", "Stylus de 4096 niveles de presión incluido.", "🖊️"),
        Producto(12, "Base refrigerante Laptop", 129.00, 18, "Accesorios", "Disipación pasiva con dos ventiladores silenciosos.", "❄️")
    )

    /** Secciones fijas del catalogo mostradas en la pantalla de inicio. */
    val secciones: List<SeccionCatalogo> = listOf(
        SeccionCatalogo("Destacados de la semana", productos.filter { it.id in listOf(1, 3, 6, 9) }),
        SeccionCatalogo("Tecnología y dispositivos", productos.filter { it.categoria in listOf("Tecnología", "Dispositivos") }),
        SeccionCatalogo("Accesorios y audio", productos.filter { it.categoria in listOf("Accesorios", "Audio") }),
        SeccionCatalogo("Únicos TECSUP", productos.filter { it.categoria == "Universidad" })
    )
}
