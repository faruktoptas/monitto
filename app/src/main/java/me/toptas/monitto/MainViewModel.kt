package me.toptas.monitto

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

class MainViewModel : ViewModel(), RequestManager.OnResponseCodeListener {


    val urlLive = MutableLiveData<String>()

    val codeLive = MutableLiveData<Int>()

    val loadingLive = MutableLiveData<Boolean>()

    var prefStorage: PrefStorage? = null
        set(value) {
            field = value
            urlLive.postValue(prefStorage?.getUrl())
            codeLive.postValue(prefStorage?.getUrlStatus(prefStorage?.getUrl()))
        }

    fun save(url: String) {
        if (url.isValidUrl()) {
            prefStorage?.setUrl(url)
            loadingLive.postValue(true)
            RequestManager.instance.makeGetRequest(url, this)
            //MyJob.scheduleJob()
        }
    }

    override fun onResponseCode(url: String, code: Int) {
        prefStorage?.setUrlStatus(url, code)
        codeLive.postValue(code)
        loadingLive.postValue(false)
    }

}
