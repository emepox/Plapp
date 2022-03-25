package com.switcherette.plantapp

import android.app.Application
import com.switcherette.plantapp.di.KoinGraph
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PlantApp: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(applicationContext)
            modules(KoinGraph.mainModule)
        }
    }
}