package me.toptas.monitto

import android.app.Application
import com.evernote.android.job.JobManager
import me.toptas.monitto.di.appModule
import me.toptas.monitto.util.MyJobCreator
import me.toptas.monitto.util.SiteManager
import org.koin.android.ext.android.inject
import org.koin.android.ext.android.startKoin

class App : Application() {

    private val manager:SiteManager by inject()

    override fun onCreate() {
        super.onCreate()
        instance = this

        startKoin(this, listOf(appModule))
        JobManager.create(this).addJobCreator(MyJobCreator(manager))
    }

    companion object {
        lateinit var instance: App
    }
}