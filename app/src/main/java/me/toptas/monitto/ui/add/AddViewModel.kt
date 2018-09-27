package me.toptas.monitto.ui.add

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import me.toptas.monitto.data.SitesRepository
import me.toptas.monitto.model.Site
import me.toptas.monitto.isValidUrl

class AddViewModel(private val repo: SitesRepository) : ViewModel() {

    var siteUrl = ""
    val addedLive = MutableLiveData<Boolean>()
    val errorLive = MutableLiveData<Throwable>()


    fun save() {
        if (siteUrl.isValidUrl()) {
            repo.insert(Site(url = siteUrl))
            addedLive.postValue(true)
        } else {
            postError("Url not valid")
        }

    }

    fun postError(msg: String) {
        errorLive.postValue(Throwable(msg))
    }
}