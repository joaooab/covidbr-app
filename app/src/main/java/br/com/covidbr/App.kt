package br.com.covidbr

import android.app.Application
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import br.com.covidbr.di.repositoryModule
import br.com.covidbr.di.serviceModule
import br.com.covidbr.di.viewModelModule
import br.com.covidbr.worker.ContryWorker
import br.com.covidbr.worker.RegionWorker
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        val workManager = WorkManager.getInstance(this.applicationContext)
        workManager.enqueue(OneTimeWorkRequest.from(RegionWorker::class.java))
        workManager.enqueue(OneTimeWorkRequest.from(ContryWorker::class.java))
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(listOf(viewModelModule, serviceModule, repositoryModule))
        }
    }
}