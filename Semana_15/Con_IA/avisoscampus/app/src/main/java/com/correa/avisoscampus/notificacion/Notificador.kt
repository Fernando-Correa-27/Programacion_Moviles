package com.correa.avisoscampus.notificacion

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.correa.avisoscampus.MainActivity
import com.correa.avisoscampus.R
import com.correa.avisoscampus.model.Aviso
import com.correa.avisoscampus.model.CanalAviso

object Notificador {
    const val CANAL = "avisos"
    const val CANAL_URGENTE = "urgentes"

    fun crearCanal(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val avisos = NotificationChannel(CANAL, "Avisos", NotificationManager.IMPORTANCE_DEFAULT)
            val urgentes = NotificationChannel(
                CANAL_URGENTE, "Urgentes", NotificationManager.IMPORTANCE_HIGH
            )
            context.getSystemService(NotificationManager::class.java)
                .createNotificationChannels(listOf(avisos, urgentes))
        }
    }

    fun tienePermiso(context: Context): Boolean =
        Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU ||
            ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) ==
            PackageManager.PERMISSION_GRANTED

    @SuppressLint("MissingPermission")
    fun mostrar(context: Context, aviso: Aviso): Boolean {
        if (!tienePermiso(context)) return false

        val intent = Intent(context, MainActivity::class.java)
        val pendiente = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        val (idCanal, prioridad) = when (aviso.canal) {
            CanalAviso.AVISOS -> CANAL to NotificationCompat.PRIORITY_DEFAULT
            CanalAviso.URGENTES -> CANAL_URGENTE to NotificationCompat.PRIORITY_HIGH
        }
        val notificacion = NotificationCompat.Builder(context, idCanal)
            .setSmallIcon(R.drawable.ic_notificacion)
            .setContentTitle(aviso.titulo)
            .setContentText(aviso.texto)
            .setPriority(prioridad)
            .setContentIntent(pendiente)
            .setAutoCancel(true)
            .build()

        return try {
            NotificationManagerCompat.from(context).notify(aviso.id, notificacion)
            true
        } catch (_: SecurityException) {
            // El permiso pudo cambiar entre la comprobación y la publicación.
            false
        }
    }
}
