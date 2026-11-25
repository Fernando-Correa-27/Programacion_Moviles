# Laboratorio 12: Rick y Morty

Aplicación Android para consultar personajes vivos de Rick and Morty. El catálogo se consume desde la API pública, Room conserva la lista y los favoritos en el dispositivo, y Firebase Authentication con Firestore controla el acceso según el rol. La implementación separa interfaz, lógica y datos con MVVM.

## Tema y variante

- Tema: Rick and Morty.
- Variante: `status=alive`.
- API: `https://rickandmortyapi.com/api/`.
- Lista: `GET character?status=alive` (primera página, 20 personajes).
- Detalle: `GET character/{id}`.

## Requisitos locales

1. Abre este directorio en Android Studio y usa JDK 17 o el JDK configurado por Android Studio.
2. Descarga el archivo `google-services.json` de la aplicación Android de Firebase y guárdalo en `app/google-services.json`. El archivo está ignorado por Git y no se distribuye con el repositorio.
3. Sincroniza Gradle y ejecuta `gradlew.bat clean assembleDebug` en Windows (o `./gradlew clean assembleDebug` en macOS/Linux).
4. Instala `app/build/outputs/apk/debug/app-debug.apk` en un emulador con Google Play o en un dispositivo compatible.

El `applicationId` y el `namespace` son `com.tecsup.rickandmorty`; el paquete Android registrado en Firebase debe coincidir.

## Preparar Firebase

En la consola del proyecto Firebase:

1. En **Authentication → Sign-in method**, habilita **Correo electrónico/contraseña**.
2. En **Authentication → Users**, crea una cuenta de prueba para usuario y otra para administrador. Guarda el UID que Firebase asigna a cada cuenta.
3. En **Firestore Database → Data**, crea una colección `usuarios`. Dentro crea un documento cuyo ID sea el UID de la cuenta de usuario. Agrega los campos `rol` (string: `usuario`) y `nombre` (string con el nombre de prueba).
4. Crea otro documento con el UID de la cuenta administradora y los campos `rol` (string: `admin`) y `nombre` (string con el nombre de prueba).
5. En **Firestore Database → Rules**, publica estas reglas:

```text
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    match /usuarios/{uid} {
      allow read: if request.auth != null && request.auth.uid == uid;
      allow write: if false;
    }
  }
}
```

La aplicación consulta el rol después de autenticar. Solo el valor `admin` concede acceso al panel; cualquier otro valor se trata como `usuario`. La aplicación no permite modificar estos documentos.

## Funciones

- Inicio de sesión y cierre de sesión con Firebase Authentication.
- Lista de personajes desde Room, actualizada con Retrofit.
- Detalle del personaje desde el segundo endpoint.
- Favoritos locales persistentes que se conservan durante la sincronización.
- Aviso cuando se muestran datos guardados sin conexión y botón para reintentar.
- Panel Admin protegido por rol, con totales, sincronización y limpieza de caché.
- Notificación local al sincronizar. En Android 13 o superior, el sistema solicita el permiso; si se deniega, el resto de la aplicación sigue disponible.

## Capturas

Las capturas deben tomarse de la aplicación ejecutándose en un emulador o dispositivo real. Se esperan `docs/capturas/login.png`, `lista.png`, `detalle.png`, `favoritos.png`, `admin.png` y `notificacion.png`. Aún no se han agregado capturas porque esta sesión no tiene emulador ni dispositivo ADB disponible.

## Trabajo y uso de IA

Trabajo individual. Se usó asistencia de IA en las dos fases del laboratorio, conforme a la autorización docente indicada en la guía. La fase 1 se desarrolló en `Sin_IA` por ser la rama indicada para esa etapa; la fase 2 y sus consultas se registran en `PROMPTS.md`.

## Privacidad del repositorio

No agregues `google-services.json`, contraseñas, claves de acceso ni identificadores de cuentas reales. El JSON de Firebase debe mantenerse solo en tu copia local.
