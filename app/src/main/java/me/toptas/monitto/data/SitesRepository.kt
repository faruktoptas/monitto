package me.toptas.monitto.data

import android.arch.lifecycle.LiveData
import me.toptas.monitto.data.room.SiteDao
import me.toptas.monitto.model.Site
import me.toptas.monitto.util.DbTask
import java.util.*

class SitesRepositoryImpl(private val dao: SiteDao) : SitesRepository {

    override fun getSites(): LiveData<List<Site>> = dao.getAll()

    override fun getSiteListNoLive() = dao.getAllNoLive()

    override fun insert(site: Site) {
        DbTask(Runnable {
            dao.insert(site)
        })
    }

    override fun update(site: Site, code: Int) {
        DbTask(Runnable {
            site.code = code
            site.updated = Date().time
            dao.update(site)
        })
    }

    override fun delete(site: Site) {
        DbTask(Runnable {
            dao.delete(site)
        })
    }
}

interface SitesRepository {
    fun getSites(): LiveData<List<Site>>
    fun insert(site: Site)
    fun update(site: Site, code: Int)
    fun getSiteListNoLive(): List<Site>
    fun delete(site: Site)
}