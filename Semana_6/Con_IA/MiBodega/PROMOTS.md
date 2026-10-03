# Prompts para mejoras de MiBodega

## Contexto común

MiBodega es una aplicación Android existente, implementada en Kotlin con Jetpack Compose, Material 3 y Navigation Compose. El estado compartido de la experiencia de cliente se coordina desde `app/src/main/java/com/tecsup/mibodega/ui/cliente/ClienteApp.kt`; las pantallas están bajo `ui/cliente/screens`, los modelos bajo `ui/cliente/modelo` y los componentes reutilizables bajo `ui/componentes`. Revisa el código actual antes de editarlo, conserva las rutas y flujos no relacionados, reutiliza el estado y componentes existentes, evita dependencias nuevas, no agregues comentarios al código, ejecuta pruebas y `:app:assembleDebug`, y realiza un commit pequeño en español para la mejora solicitada.

# Mejora 1 - Validación del login

Implementa la validación funcional del inicio de sesión de MiBodega. Revisa `BienvenidaScreen.kt`, `ClienteApp.kt` y el modelo de autenticación antes de cambiar nada. Usa credenciales de demostración fijas en código, valida que usuario y contraseña no estén vacíos, muestra errores claros y visuales para campos vacíos o credenciales incorrectas, y navega a Inicio únicamente si ambas credenciales son correctas. Permite cancelar o cerrar el formulario sin iniciar sesión. Mantén el flujo de registro existente. Añade pruebas unitarias para credenciales correctas, incorrectas y vacías, ejecuta las pruebas y compila. Haz un commit exclusivo con el mensaje `Validar credenciales en el inicio de sesión`.

# Mejora 2 - Validación de formularios

Implementa la validación de campos obligatorios en Crear cuenta y Datos de entrega. Revisa `RegistroScreen.kt`, `DatosEntregaScreen.kt` y `CampoTexto.kt`. No permitas continuar si hay campos obligatorios vacíos o con espacios; marca los campos inválidos con el estilo de error de Material 3 y muestra mensajes junto a ellos. Actualiza el error inmediatamente cuando el usuario corrija el valor y conserva el teclado apropiado para cada campo. Añade pruebas unitarias para la lógica reutilizable de campos requeridos, ejecuta pruebas y compilación. Haz un commit exclusivo con el mensaje `Validar campos obligatorios de formularios`.

# Mejora 3 - Badge del carrito

Verifica y completa el badge del carrito en la barra superior de Inicio. Inspecciona `InicioScreen.kt`, `ClienteApp.kt` e `ItemCarrito.kt`. El número debe representar las unidades totales, no solo las líneas distintas, actualizarse al agregar, incrementar, decrementar o eliminar productos y ocultarse cuando sea cero. Centraliza el cálculo en el modelo o en una función reutilizable sin modificar la lista original. Añade pruebas para carrito vacío y varias cantidades; ejecuta pruebas y compilación. Haz un commit exclusivo con el mensaje `Actualizar badge con unidades del carrito`.

# Mejora 4 - Estado de carrito vacío

Implementa un estado vacío explícito en `CarritoScreen.kt`. Cuando no haya productos, muestra el texto `Carrito vacío`, un indicador visual sobrio y una acción que permita regresar al catálogo. Oculta el listado y el resumen financiero mientras no haya artículos, y conserva la navegación de retorno. Asegúrate de que el estado aparezca tanto al abrir el carrito vacío como al quitar el último producto. Ejecuta pruebas y compilación. Haz un commit exclusivo con el mensaje `Mostrar estado de carrito vacío`.

# Mejora 5 - Confirmación antes de eliminar productos

Agrega un `AlertDialog` al flujo de eliminación de `CarritoScreen.kt`. Al tocar eliminar, solicita confirmación indicando qué producto se quitará. La acción de cancelar o cerrar el diálogo debe conservar el carrito; ejecuta el callback de eliminación únicamente al confirmar. Mantén intactas las acciones de incrementar, decrementar y continuar pedido. Ejecuta pruebas y compilación. Haz un commit exclusivo con el mensaje `Confirmar eliminación de productos del carrito`.

# Mejora 6 - Pantalla Mis pedidos

Implementa el historial de pedidos confirmados. Inspecciona `ClienteApp.kt`, `Pedido.kt`, `PedidosScreen.kt` y `ConfirmacionScreen.kt`. Guarda cada pedido como una nueva entrada inmutable con identificador, productos, cantidades, total, fecha y dirección disponible; nunca reemplaces los pedidos previos. Actualiza la pantalla `Mis pedidos` para listar el historial más reciente primero y mostrar esa información con un estado vacío cuando no existan pedidos. Asegura que confirmar un pedido lo agregue antes de navegar y no pierda el carrito hasta que termine el flujo vigente. Ejecuta pruebas y compilación. Haz un commit exclusivo con el mensaje `Agregar historial de pedidos confirmados`.

# Mejora 7 - Favoritos

Implementa favoritos sincronizados en MiBodega. Revisa `ClienteApp.kt`, `InicioScreen.kt`, `ProductoCard.kt` y `DetalleProductoScreen.kt`. Mantén el conjunto de productos favoritos en estado compartido, ofrece un corazón que permita marcar y desmarcar en catálogo y detalle, y crea una pantalla Favoritos que liste solo los seleccionados. Desde Favoritos se debe poder quitar un producto y ver el cambio inmediatamente en las demás pantallas; conserva la navegación y las acciones de carrito. Incluye un estado vacío apropiado y ejecuta pruebas y compilación. Haz un commit exclusivo con el mensaje `Implementar productos favoritos`.

# Mejora 8 - Ordenamiento por precio

Agrega al catálogo un selector de orden de precio con `Menor a mayor` y `Mayor a menor`. Revisa `InicioScreen.kt` y el modelo `Producto.kt`. Ordena solo la lista visible después de aplicar búsqueda y categoría, sin alterar los datos originales; conserva la opción elegida mientras la pantalla o la sesión siga activa. Mantén una opción inicial razonable y un control coherente con Material 3. Añade pruebas unitarias para ambos órdenes y para confirmar que la lista original no cambia; ejecuta pruebas y compilación. Haz un commit exclusivo con el mensaje `Ordenar productos por precio`.

# Mejora 9 - Recojo en tienda vs. delivery

Incorpora la selección entre `Recojo en tienda` y `Delivery` mediante controles `RadioButton`. Revisa `CarritoScreen.kt`, `DatosEntregaScreen.kt`, `Pedido.kt`, `PedidosScreen.kt` y `ClienteApp.kt`. Centraliza el costo de delivery en una constante configurable, actualiza el total del carrito inmediatamente al cambiar la modalidad, omite los datos de dirección cuando se recoge en tienda y guarda la modalidad en el pedido confirmado. El historial y la confirmación deben identificar correctamente la modalidad elegida. Añade pruebas de cálculo de total para ambas opciones, ejecuta pruebas y compilación. Haz un commit exclusivo con el mensaje `Agregar opciones de delivery y recojo`.

# Mejora 10 - Modo oscuro

Implementa el modo oscuro global. Inspecciona `MainActivity.kt`, `Theme.kt` y `PerfilScreen.kt`. Agrega un `Switch` en Perfil, eleva el estado al nivel que envuelve toda la aplicación y actualiza `MaterialTheme` inmediatamente al cambiarlo. Mantén la preferencia durante la sesión y cambios de configuración usando el mecanismo de estado guardable disponible. Define esquemas claro y oscuro con contraste suficiente; sustituye colores fijos en superficies y textos que impidan legibilidad en modo oscuro. No alteres el diseño claro ni agregues almacenamiento nuevo sin necesidad. Ejecuta pruebas y compilación. Haz un commit exclusivo con el mensaje `Implementar modo oscuro en la aplicación`.

# Mejora 11 - Animación de navegación

Agrega transiciones breves y consistentes entre rutas del `NavHost` en `ClienteApp.kt`, usando las APIs de navegación y `AnimatedContent` compatibles con la versión instalada de Navigation Compose. Aplica entradas y salidas discretas, incluyendo transiciones inversas al volver atrás. No cambies nombres de rutas, callbacks, comportamiento del back stack ni estado compartido. Evita añadir dependencias si la versión actual ya ofrece transiciones. Ejecuta pruebas y compilación. Haz un commit exclusivo con el mensaje `Agregar animaciones de navegación`.
