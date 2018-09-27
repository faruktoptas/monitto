package me.toptas.monitto

import android.app.Application
import com.evernote.android.job.JobManager
import me.toptas.monitto.di.appModule
import me.toptas.monitto.util.MyJobCreator
import org.koin.android.ext.android.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        JobManager.create(this).addJobCreator(MyJobCreator())

        startKoin(this, listOf(appModule))
    }

    companion object {
        lateinit var instance: App
    }
}