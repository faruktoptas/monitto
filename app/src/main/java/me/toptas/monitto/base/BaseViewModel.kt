package me.toptas.monitto.base

import android.arch.lifecycle.MutableLiveData

abstract class BaseViewModel {

    val errorLive = MutableLiveData<Throwable>()

    fun postError(msg: String) {
        errorLive.postValue(Throwable(msg))
    }
}