package com.tecsup.rickandmorty

import android.content.Context
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.tecsup.rickandmorty.data.auth.AuthRepository
import com.tecsup.rickandmorty.data.auth.FirebaseAuthRepository
import com.tecsup.rickandmorty.data.local.AppDatabase
import com.tecsup.rickandmorty.data.remote.RetrofitClient
import com.tecsup.rickandmorty.data.repository.ItemRepository
import com.tecsup.rickandmorty.data.repository.ItemRepositoryImpl
import com.tecsup.rickandmorty.notification.Notificador
import com.tecsup.rickandmorty.notification.NotificadorAndroid

class AppContainer(contexto: Context) {
    private val database = Room.databaseBuilder(contexto, AppDatabase::class.java, "personajes.db").build()
    val itemRepository: ItemRepository = ItemRepositoryImpl(RetrofitClient.api, database.itemDao())
    val authRepository: AuthRepository = FirebaseAuthRepository(FirebaseAuth.getInstance(), FirebaseFirestore.getInstance())
    val notificador: Notificador = NotificadorAndroid(contexto.applicationContext)
}
