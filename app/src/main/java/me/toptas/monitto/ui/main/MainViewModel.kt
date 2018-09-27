package me.toptas.monitto.ui.main

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import me.toptas.monitto.data.SitesRepository
import me.toptas.monitto.util.PrefStorage
import me.toptas.monitto.util.RequestManager
import me.toptas.monitto.isValidUrl
import me.toptas.monitto.model.Site
import me.toptas.monitto.toStatus
import java.util.*

class MainViewModel(val repo: SitesRepository) : ViewModel() {


    val urlLive = MutableLiveData<String>()

    val codeLive = MutableLiveData<Int>()

    val loadingLive = MutableLiveData<Boolean>()

    val siteListLive = repo.getSites()

    val emptyList = Transformations.map(repo.getSites()) {
        it.isEmpty()
    }


    var prefStorage: PrefStorage? = null
        set(value) {
            field = value
            urlLive.postValue(prefStorage?.getUrl())
            codeLive.postValue(prefStorage?.getUrlStatus(prefStorage?.getUrl()))
        }

    fun getSites() {
        repo.getSites()
    }

    fun updateStatus(site: Site, code: Int) {
        site.state = code.toStatus()
        site.updated = Date().time
        repo.update(site)
    }

    /*fun save(url: String) {
        if (url.isValidUrl()) {
            prefStorage?.setUrl(url)
            loadingLive.postValue(true)
            RequestManager.instance.makeGetRequest(url, this)
        }
    }*/

    /*override fun onResponseCode(url: String, code: Int) {
        prefStorage?.setUrlStatus(url, code)
        codeLive.postValue(code)
        loadingLive.postValue(false)
    }*/

}
