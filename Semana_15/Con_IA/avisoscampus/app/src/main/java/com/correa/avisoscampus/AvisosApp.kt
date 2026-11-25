package com.correa.avisoscampus

import android.app.Application
import androidx.room.Room
import com.correa.avisoscampus.data.AvisosDatabase
import com.correa.avisoscampus.data.AvisosRepository
import com.correa.avisoscampus.data.AvisosRepositoryRoom
import com.correa.avisoscampus.notificacion.Notificador
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class AvisosApp : Application() {
    private val alcance = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    val repositorio: AvisosRepository by lazy {
        val base = Room.databaseBuilder(this, AvisosDatabase::class.java, "avisos.db").build()
        AvisosRepositoryRoom(base.avisosDao(), alcance)
    }

    override fun onCreate() {
        super.onCreate()
        Notificador.crearCanal(this)
    }
}
