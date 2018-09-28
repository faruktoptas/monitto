package me.toptas.monitto.util

import android.content.Context
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.toptas.monitto.data.SitesRepository
import me.toptas.monitto.logv
import me.toptas.monitto.model.Site

class SiteManager(private val repo: SitesRepository) {

    fun checkSite(context: Context, site: Site) {
        checkAll(context, listOf(site))
    }


    fun checkAll(context: Context, list: List<Site> = repo.getSiteListNoLive()) {
        Observable.fromIterable(list)
                .flatMap {
                    RequestManager.instance.makeGetRequestRx(it)
                }
                .map {
                    repo.update(it.first, it.second)
                    Pair(it.first, it.second)
                }
                .filter {
                    it.first.code != -1 && it.first.code != it.second
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError {
                    logv("error: $it")
                }
                .subscribe {
                    it.first.apply {
                        NotificationTask("url: $url status:$code").showNotification(_id!!.toInt(), context)
                    }
                }

    }

}