# CentroTareas

## Observación del Paso 1

Al pulsar **Tarea larga (MAL)** la pantalla se queda congelada durante 4 segundos. El botón **Tócame** deja de sumar y no responde a los toques, la barra superior tampoco reacciona y el texto **Trabajando...** no llega a verse. Esto ocurre porque `tareaLargaMala()` se ejecuta en el hilo principal: primero cambia el mensaje, pero ese cambio solo queda en memoria; después ese mismo hilo pasa 4 segundos ocupado y no puede dibujar la pantalla ni atender los eventos táctiles. Cuando por fin termina, el hilo vuelve a estar libre y dibuja el estado final, que ya dice **Terminó**. Por eso el usuario nunca alcanza a ver el estado intermedio.

Si la espera fuera de 10 segundos, Android mostraría el diálogo **«La aplicación no responde» (ANR)**, porque el hilo principal habría estado bloqueado más tiempo del que el sistema tolera para atender la entrada del usuario (unos 5 segundos). La app no se cerraría sola en ese instante, pero el sistema ofrecería cerrarla o esperar.
