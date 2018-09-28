package me.toptas.monitto.ui.main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import me.toptas.monitto.data.SitesRepository
import me.toptas.monitto.model.Site

class MainViewModel(private val repo: SitesRepository) : ViewModel() {

    val loadingLive = MutableLiveData<Boolean>()

    val siteListLive = repo.getSites()

    val emptyList: LiveData<Boolean> = Transformations.map(repo.getSites()) {
        it.isEmpty()
    }

    fun getSites() {
        repo.getSites()
    }

    fun updateStatus(site: Site, code: Int) {
        repo.update(site, code)
    }

    fun deleteSite(site: Site) {
        repo.delete(site)
    }

}
