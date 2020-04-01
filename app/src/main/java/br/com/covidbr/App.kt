package br.com.covidbr

import android.app.Application
import br.com.covidbr.di.serviceModule
import br.com.covidbr.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(listOf(viewModelModule, serviceModule))
        }
    }
}