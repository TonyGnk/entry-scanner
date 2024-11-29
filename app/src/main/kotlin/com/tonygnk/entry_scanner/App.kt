package com.tonygnk.entry_scanner

import android.app.Application
import com.google.firebase.FirebaseApp
import com.tonygnk.entry_scanner.di.AppModule
import com.tonygnk.entry_scanner.di.AppModuleImp


class App : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        appModule = AppModuleImp(this)
    }

    companion object {
        lateinit var appModule: AppModule
    }
}
