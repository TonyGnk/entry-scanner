package com.tonygnk.entry_scanner.di

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import com.tonygnk.entry_scanner.data.repositories.AdminDaoRepository
import com.tonygnk.entry_scanner.data.repositories.AdminDaoRepositoryImpl

interface AppModule {
    val fireStore: FirebaseFirestore
    val repository: AdminDaoRepository
}

class AppModuleImp(
    private val appContext: Context,
) : AppModule {
    override val fireStore: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    override val repository: AdminDaoRepository by lazy {
        AdminDaoRepositoryImpl(fireStore)
    }
}
