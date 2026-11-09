# Registro de uso de IA

Este registro resume las tareas reales realizadas con asistencia de IA. Los prompts se reproducen como extractos literales del encargo de trabajo para no atribuir consultas que no ocurrieron. La reflexión final debe revisarse y redactarse con las palabras del estudiante.

## Consulta 1 — Implementación de la fase 1

**Fecha:** 2026-11-08  
**Herramienta:** OpenAI Codex Desktop, agente de código GPT-6.  
**Prompt:** «Desarrolla de principio a fin el Laboratorio 12 [...] primero la FASE 1 completa en la rama Sin_IA [...] Después, la FASE 2 en la rama Con_IA».  
**Qué respondió (resumen):** Inspeccionó el proyecto Android existente, conservó el paquete `com.tecsup.rickandmorty`, implementó la arquitectura MVVM con Retrofit, Room, Firebase, navegación por roles y notificaciones, y documentó la configuración.  
**Qué usamos / qué cambiamos y por qué:** La fase 1 se desarrolló con este mismo agente. Se conservaron el `applicationId` y el `namespace` existentes. Kotlin y KSP se alinearon con las dependencias del proyecto para que Gradle compilara; no se incluyeron credenciales ni el JSON de Firebase.  
**Qué aprendimos:** Room permite que la interfaz lea un flujo persistente mientras Retrofit actualiza la base. Para no perder favoritos, la sincronización lee sus IDs antes del `upsert` y los reaplica a las entidades recibidas.

## Consulta 2 — Pruebas unitarias de la lista

**Fecha:** 2026-11-08  
**Herramienta:** OpenAI Codex Desktop, agente de código GPT-6.  
**Prompt:** «Pruebas unitarias de ListaViewModel con un FakeItemRepository y un FakeNotificador (JUnit + kotlinx-coroutines-test, con una regla que reemplace Dispatchers.Main). Mínimo 3 pruebas: refresco exitoso, fallo sin datos guardados y fallo con datos guardados».  
**Qué respondió (resumen):** Agregó repositorio y notificador falsos, una regla para sustituir `Dispatchers.Main` y pruebas para los tres resultados del refresco. También añadió una prueba de validación de campos vacíos en el login.  
**Qué usamos / qué cambiamos y por qué:** Se conservaron las tres pruebas requeridas y se agregó la comprobación de login vacío para verificar que no llama al repositorio de autenticación. `testDebugUnitTest` terminó correctamente.  
**Qué aprendimos:** Al controlar `Dispatchers.Main` con `StandardTestDispatcher`, el test puede avanzar las corrutinas del ViewModel de forma predecible y observar el `StateFlow` sin Firebase ni una base de datos real.

## Consulta 3 — Presentación y accesibilidad

**Fecha:** 2026-11-08  
**Herramienta:** OpenAI Codex Desktop, agente de código GPT-6.  
**Prompt:** «Mejoras de interfaz (Material 3 coherente, color por estado del personaje, accesibilidad con contentDescription, mejor presentación de Cargando/Error/Vacío, aviso de sin conexión visible)».  
**Qué respondió (resumen):** Mejoró las tarjetas de personajes, tradujo los estados de la API, dio más contexto a carga y error, añadió una acción en el estado vacío y configuró anuncios accesibles para errores y avisos de conexión.  
**Qué usamos / qué cambiamos y por qué:** El estado `Alive` se muestra como «Vivo», `Dead` como «Muerto» y cada uno recibe un color distinto. Se mantuvieron las pantallas como observadoras y se dejaron las decisiones en ViewModels.  
**Qué aprendimos:** Los colores pueden reforzar el texto del estado, pero el estado también debe expresarse con palabras para no depender solo del color. `contentDescription` identifica las imágenes y acciones para lectores de pantalla.

## Consulta 4 — Revisión de aceptación

**Fecha:** 2026-11-08  
**Herramienta:** OpenAI Codex Desktop, agente de código GPT-6.  
**Prompt:** «Revisión de errores: recorre las pruebas de aceptación PA-01 a PA-14 y corrige lo que falle (login, offline, favoritos tras sincronizar, rotación, permiso de notificaciones negado)».  
**Qué respondió (resumen):** Revisó las rutas de login, sesión, roles, Room, sincronización, navegación, permiso de notificaciones y rotación; corrigió el mapeo de errores de red de Firebase y cubrió la validación local con una prueba.  
**Qué usamos / qué cambiamos y por qué:** Se clasifican como verificadas por código las rutas que dependen de la implementación y por pruebas las rutas cubiertas por tests. No se marcó ninguna prueba como verificada en emulador porque este entorno no tiene ADB ni un dispositivo disponible. La existencia de documentos y reglas de Firestore debe confirmarse manualmente en la consola.  
**Qué aprendimos:** Un fallo al leer el rol de Firestore no debe cerrar la sesión autenticada; se puede mostrar un error y reintentar la lectura. La protección del panel tiene que revisar el rol en la ruta además de ocultar su botón.
