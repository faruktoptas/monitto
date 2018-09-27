package me.toptas.monitto.data

import android.arch.lifecycle.LiveData
import me.toptas.monitto.data.room.SiteDao
import me.toptas.monitto.model.Site
import me.toptas.monitto.util.DbTask

class SitesRepositoryImpl(private val dao: SiteDao) : SitesRepository {

    override fun getSites(): LiveData<List<Site>> = dao.getAll()

    override fun insert(site: Site) {
        DbTask(Runnable {
            dao.insert(site)
        })
    }

    override fun update(site: Site) {
        DbTask(Runnable {
            dao.update(site)
        })
    }
}

interface SitesRepository {
    fun getSites(): LiveData<List<Site>>
    fun insert(site: Site)
    fun update(site: Site)
}