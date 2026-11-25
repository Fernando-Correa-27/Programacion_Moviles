# CampusGPS

Aplicación Android en Kotlin y Jetpack Compose que solicita la ubicación actual mediante Google Play Services Location, la presenta sobre un mapa de OpenStreetMap con osmdroid y ordena cinco puntos ficticios del campus por cercanía. No utiliza claves de Google Maps.

## Arquitectura

```text
ui/ (Compose + osmdroid) -> MapaViewModel -> interfaces Repository
                                      |-> UbicacionRepositoryGoogle (Fused)
                                      |-> LugaresRepositoryLocal
util/ (distancia y formato) <- ViewModel
```

La vista observa `StateFlow` del ViewModel. El ViewModel no recibe `Context` ni clases de osmdroid; trabaja con modelos de dominio e interfaces. Los marcadores y `MapView` pertenecen únicamente a `ui/`.

## Árbol de paquetes

```text
com.correa.campusgps/
├── MainActivity.kt
├── data/                 Repositories de ubicación y lugares
├── model/                Posicion, Lugar y estados de UI
├── ui/                   Pantalla, mapa, lista y mensaje de error
├── util/                 Distancia y formato puros
└── viewmodel/            MapaViewModel y Factory
```

## Capturas

Capturas incluidas en `docs/capturas/`, tomadas con el emulador Medium Phone (API 37) y la ubicación fijada en `-12.0464, -77.0428`:

| Escenario | Ruta |
|---|---|
| Solicitud de permiso | `docs/capturas/permiso.png` |
| Mapa y ubicación actual | `docs/capturas/mapa_ubicacion.png` |
| Cinco marcadores | `docs/capturas/marcadores.png` |
| Lista ordenada | `docs/capturas/lista_ordenada.png` |
| Estado de error | `docs/capturas/error.png` |

## Pruebas de aceptación

| Prueba | Estado |
|---|---|
| Abrir y pulsar por primera vez | Verificada en emulador: aparece el cuadro de permiso |
| Aceptar permiso | Verificada: se ven latitud, longitud y el marcador Estás aquí |
| Negar permiso y abrir ajustes | Verificada: mensaje con Abrir ajustes; el botón abre la ficha de la app |
| Mapa y cinco marcadores | Verificada: carga OpenStreetMap con 5 marcadores azules y el más cercano en verde |
| Tocar un marcador | Verificada: la ventana muestra nombre y categoría |
| Lista ordenada por distancia | Verificada: 88 m, 109 m, 172 m, 220 m y 266 m; con ubicación lejana muestra km |
| Seleccionar tarjeta | Verificada: la cámara viaja al lugar, abre su ventana y la tarjeta queda con fondo y borde |
| Ir al más cercano | Verificada: el botón está deshabilitado antes de buscar y luego lleva al marcador verde |
| Cambiar ubicación y volver a buscar | Verificada: se actualizan distancias, orden, marcador verde y el primero de la lista |
| GPS apagado | Verificada: mensaje Activa el GPS con botón Reintentar |
| Rotar pantalla | Verificada: se conservan ubicación, distancias, selección y lista; en horizontal el contenido es desplazable y el mapa mantiene altura fija |
| Revisar archivos locales y secretos | Verificada: `.gitignore` excluye locales; no hay claves de Maps ni `local.properties` en Git |

## Para pensar

### ¿Por qué FusedLocationProviderClient está dentro del Repository y no en el ViewModel?

Fused es un detalle de infraestructura de Google Play Services. El Repository lo encapsula y devuelve un modelo `Posicion`, por lo que el ViewModel no depende de la API concreta. Así se mantiene más fácil de probar y se puede cambiar la fuente de ubicación sin reescribir la pantalla ni las reglas de negocio.

### ¿Qué diferencia hay entre el permiso de ubicación y una clave de API? ¿Qué pasa si falta INTERNET o el user-agent?

El permiso autoriza que la app lea una capacidad privada del dispositivo; si se niega, Fused no debe usarse. Una clave de API identifica y autoriza el consumo de un servicio remoto; esta versión con osmdroid no necesita una. Sin Internet no se descargan tiles y sin un user-agent propio OpenStreetMap puede bloquear las solicitudes.

### ¿Por qué una clave de API va en local.properties y no en el código? ¿Qué harías si se subiera por error?

Una clave no debe quedar en el historial público de Git, por eso se carga desde un archivo ignorado y se restringe por paquete, huella y APIs permitidas. Si se filtrara, la revocaría o regeneraría de inmediato y revisaría los registros de uso. Después eliminaría el secreto del historial con el procedimiento aprobado por el repositorio.

### ¿Dónde calculas la distancia y por qué no en el Composable?

La distancia se calcula en `MapaViewModel` con la función pura `distanciaMetros` de `util/`. El resultado se publica como estado ya ordenado para que la interfaz solo lo represente. Calcularlo en un Composable mezclaría presentación con lógica y podría repetir trabajo en cada recomposición.

### ¿Qué pasa si el usuario niega el permiso dos veces?

La app permanece abierta y muestra un estado `SIN_PERMISO` con una explicación clara. El botón Abrir ajustes lleva a la ficha de la aplicación para que el usuario conceda el permiso manualmente, incluido el caso de rechazo permanente. Nunca se intenta obtener ubicación sin autorización.
