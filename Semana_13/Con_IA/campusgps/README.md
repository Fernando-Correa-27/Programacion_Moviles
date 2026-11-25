# CampusGPS

Aplicación Android en Kotlin y Jetpack Compose que solicita la ubicación actual mediante Google Play Services Location, la presenta sobre un mapa de OpenStreetMap con osmdroid y ordena cinco puntos ficticios del campus por cercanía. No utiliza claves de Google Maps.

Esta es la versión de la Fase 2: agrega filtro por categoría, radio de cercanía, persistencia del último lugar con DataStore, modo oscuro del mapa y un rediseño de la interfaz con controles de zoom.

## Arquitectura

```text
ui/ (Compose + osmdroid) -> MapaViewModel -> interfaces Repository
                                      |-> UbicacionRepositoryGoogle (Fused)
                                      |-> LugaresRepositoryLocal
                                      |-> PreferenciasRepositoryDataStore
util/ (distancia y formato) <- ViewModel
```

La vista observa `StateFlow` del ViewModel. El ViewModel no recibe `Context` ni clases de osmdroid; trabaja con modelos de dominio e interfaces. Los marcadores, el `MapView`, el círculo del radio y el filtro de color de los tiles pertenecen únicamente a `ui/`.

## Árbol de paquetes

```text
com.correa.campusgps/
├── MainActivity.kt
├── data/                 Repositories de ubicación, lugares y preferencias
│   ├── LugaresRepository.kt
│   ├── LugaresRepositoryLocal.kt
│   ├── PreferenciasRepository.kt
│   ├── PreferenciasRepositoryDataStore.kt
│   ├── UbicacionRepository.kt
│   └── UbicacionRepositoryGoogle.kt
├── model/                Posicion, Lugar y estados de UI
├── ui/                   Pantalla, mapa, lista, filtros, radio, zoom y tema
│   ├── ControlesZoom.kt
│   ├── FiltroCategorias.kt
│   ├── ListaLugares.kt
│   ├── MapaUbicacion.kt
│   ├── MensajeError.kt
│   ├── PantallaMapa.kt
│   ├── SelectorRadio.kt
│   └── theme/
├── util/                 Distancia y formato puros
└── viewmodel/            MapaViewModel y Factory
```

## Mejoras de la Fase 2

| Mejora | Qué hace | Capas modificadas |
|---|---|---|
| Filtro por categoría | Chips `Todas`, `Comida`, `Estudio`, `Eventos`, `Servicios`. Filtra mapa, lista y cálculo del más cercano, y muestra el conteo `2 de 5`. | `viewmodel/` (estado y regla), `ui/` (chips y contador) |
| Radio de cercanía | Selector `Sin radio`, `100 m`, `200 m`, `500 m`. Dibuja un círculo y marca `Dentro del radio` en la lista. No altera el filtro por categoría. | `viewmodel/` (ids dentro del radio), `ui/` (selector, polígono, etiquetas) |
| Último lugar con DataStore | Guarda el id al tocar una tarjeta y lo restaura al reabrir la app, centrando el mapa y abriendo la ventana del marcador. | `data/` (interfaz e implementación), `viewmodel/` (restauración y guardado), `viewmodel/CampusViewModelFactory.kt` |
| Modo oscuro del mapa | Invierte los colores de los tiles cuando el sistema usa tema oscuro. | `ui/MapaUbicacion.kt` |
| Rediseño y zoom | Tema azul/verde/rojo con tarjetas y bordes suaves, cabecera con identidad, controles flotantes `+` y `−` de 48 dp y adaptación a horizontal. | `ui/theme/`, `ui/PantallaMapa.kt`, `ui/ListaLugares.kt`, `ui/ControlesZoom.kt` |

El radio es informativo: dibuja el círculo y etiqueta los lugares que caen dentro, pero la lista sigue mostrando todos los lugares de la categoría activa. El filtro de categoría sí cambia el contenido del mapa, de la lista y del lugar más cercano.

## Capturas

Capturas incluidas en `docs/capturas/`, tomadas con el emulador Medium Phone (API 37) y la ubicación fijada en `-12.0464, -77.0428`:

| Escenario | Ruta |
|---|---|
| Solicitud de permiso | `docs/capturas/permiso.png` |
| Mapa y ubicación actual | `docs/capturas/mapa_ubicacion.png` |
| Cinco marcadores | `docs/capturas/marcadores.png` |
| Lista ordenada | `docs/capturas/lista_ordenada.png` |
| Estado de error | `docs/capturas/error.png` |
| Fase 2: rediseño con zoom, filtro y radio | `docs/capturas/diseno_fase2.png` |
| Fase 2: filtro por categoría | `docs/capturas/filtro.png` |
| Fase 2: radio de 200 m | `docs/capturas/radio.png` |
| Fase 2: último lugar restaurado | `docs/capturas/ultimo_lugar.png` |

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
| Fase 2: filtro por categoría | Verificada: `Estudio` deja 2 de 5; el mapa, la lista y el más cercano se reducen; `Todas` restaura los 5 |
| Fase 2: limpiar selección filtrada | Verificada: al seleccionar Cafetería y filtrar `Estudio`, la tarjeta seleccionada se desmarca y el mapa no queda apuntando a un lugar oculto |
| Fase 2: radio de cercanía | Verificada: 100 m marca 1 lugar, 200 m marca 3, 500 m marca 3 (los otros dos están fuera); `Sin radio` apaga el círculo y las marcas |
| Fase 2: círculo decorativo | Verificada: tocar dentro del círculo no mueve la cámara ni abre una burbuja vacía |
| Fase 2: persistencia con DataStore | Verificada: se crea `files/datastore/campusgps_preferencias.preferences_pb`; al cerrar y reabrir, Cafetería aparece seleccionada y el mapa centrado en su marcador |
| Fase 2: rotación con filtro y radio | Verificada: `Estudio` y `200 m` sobreviven a rotar a horizontal y volver a vertical, junto con distancias y etiquetas |
| Fase 2: controles de zoom | Verificada: los botones `+` y `−` cambian el nivel de zoom del mapa, tienen 48 dp y permanecen visibles en horizontal |
| Fase 2: modo oscuro del mapa | Verificada: con el tema oscuro del sistema los tiles se invierten y la interfaz usa la paleta oscura |

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
